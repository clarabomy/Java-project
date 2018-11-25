
package project.game;

import java.util.ArrayList;
import java.util.Arrays;
import project.game.character.Sex;
import project.game.investigation.Investigation;

/**
 *
 * @author ISEN
 */
public class Game {
    protected static UserInterface m_console = new UserInterface();//memberOfClass_attributeName
    protected static FileManager m_files = new FileManager();
    protected static DataManager m_dataSave = new DataManager();
    
    protected static Difficulty m_levelChoice;
    protected Investigation m_currentInvestigation = null;
    protected static boolean m_endedGame;
    

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public static UserInterface getConsole() {
        return m_console;
    }
    
    public static Difficulty getLevelChoice() {
        return m_levelChoice;
    }
    
    public static boolean isEndedGame() {
        return m_endedGame;
    }
    
    public static void setEndedGame() {
        m_endedGame = true;
    }
    
    /*$$ METHODS $$*/
    public static String[] convertArrayList(ArrayList <String> choices) {
        String[] list = new String[choices.size()];
        
        int i = 0;
        for (String current : choices) {
            list[i++] = current;
        }
        
        return list;
    }
    
    
    public void gameMenu() {
        boolean exitGame = false;
        do {
            boolean gotSaves = m_files.getSavesName().length != 0;
            boolean gotInvest = m_currentInvestigation != null;
            
            ArrayList choicesList = new ArrayList();
            {
                choicesList.add("Commencer une nouvelle enquête" + (gotSaves || gotInvest? "" : "\n\n"));
                if (m_currentInvestigation != null) {
                    choicesList.add("Continuer l'enquête en cours");
                    choicesList.add("Déposer un rapport d'enquête (sauvegarde)" + (gotSaves? "" : "\n\n"));
                }
                if (gotSaves) {
                    choicesList.add("Reprendre une enquête");
                    choicesList.add("Abandonner une enquête\n\n");
                }      
                choicesList.add("Règles du jeu");
                choicesList.add("Quitter le bureau d'enquête");
            }
        
            
            int choix = m_console.clean().display("Standardiste", "Bienvenue à JavaSpector. Que puis-je pour vous?", convertArrayList(choicesList)).execChoice();
            if (!gotSaves && !gotInvest && choix >= 2) {
                choix += 4;
            }
            else if ((!gotInvest && choix >=2) || (!gotSaves && gotInvest && choix >= 4)) {
                choix += 2;
            }
            switch (choix) {
                case 1:
                    m_endedGame = false;
                    newInvestigation();
                    break;
                case 2:
                    //continueCurrentInvestigation
                    m_currentInvestigation.investigationMenu();
                    break;
                case 3:
                    //saveCurrentInvestigation
                    ArrayList <String> gameData = m_dataSave.exportGame(m_currentInvestigation);
                    m_files.setCurrentFileName(m_currentInvestigation.getInvestigator().getFullName());
                    m_files.writeSaveFile(m_currentInvestigation.getInvestigator().getFullName(), convertArrayList(gameData));
                    break;
                case 4:
                    //loadInvestigation
                    ArrayList<String> contentFile = m_files.readSaveFile(m_files.selectFile("charger"));
                    if (contentFile != null) {
                        m_currentInvestigation = m_dataSave.importGame(contentFile);
                        m_files.setCurrentFileName(m_currentInvestigation.getInvestigator().getFullName());
                        m_currentInvestigation.investigationMenu();
                    }
                    break;
                case 5:
                    //dropInvestigation
                    m_console.display(m_files.deleteSaveFile(m_files.selectFile("supprimer"))? "Opération réussite." : "Echec de suppression.");
                    break;
                case 6:
                    String text = "Vous avez pour objectif de trouver le coupable parmi une série de suspects.\n"
                            + "Ainsi, vous interrogerez à tour de rôle les suspects afin d’obtenir des dépositions "
                            + "que vous pourrez relier aux preuves que vous trouverez en analysant les différents éléments de l’enquête.\n\n" +
                            "Mais, cela ne sera pas si simple ! Prenez garde, le meurtrier peut compter sur son fidèle partenaire pour vous tromper.";
                    m_console.display("Règles du jeu\n")
                            .display(text)
                            .execContinue("Compris !");
                    break;
                case 7:
                    exitGame = true;
                    break;
            }
            
            if (m_endedGame && m_files.getCurrentFileName() != null) {//s'il y a un fichier chargé, on le supprime
                m_files.deleteSaveFile(m_files.getCurrentFileName());
                m_currentInvestigation = null;
            }
        } while (!exitGame);//quitte le jeu en choisissant l'option dédiée
    }
    
    public void newInvestigation() {
        //etape 1 : nom perso (nom fichier)
        String fullName = null;
        m_console.clean();
        do {
            String name = m_console.display("Standardiste", "Veuillez entrer votre nom :").execInput();
            String surname = m_console.display("Standardiste", "Et maintenant votre prénom :").execInput();
            fullName = surname + ' ' + name;
            if (Arrays.asList(m_files.getSavesName()).contains(fullName) || //si nom déjà utilisé (nom enquêteur = nom fichier save)
                    fullName.contains("\\") ||
                    fullName.contains("/") ||
                    fullName.contains(":") ||
                    fullName.contains("*") ||
                    fullName.contains("?") ||
                    fullName.contains("\"") ||
                    fullName.contains("<") ||
                    fullName.contains(">") ||
                    fullName.contains("|")) {
                fullName = null;
                m_console.display("Vous", "Mais non, bien sûr! Je ne m'appelle pas comme ça.");
            }
        } while (fullName == null);
        
        
        //etape 2 : sexe perso
        Sex gender = null;
        String[] sexList = {"Un talentueux inspecteur !", "Une talentueuse inspectrice !"};
        switch (m_console.display("Vous", "Croyez moi, je suis", sexList).execChoice()) {
            case 1:
                gender = Sex.HOMME;
                break;
            case 2:
                gender = Sex.FEMME;
                break;
        }
        
        
        //etape 3 : difficulté enquête
        String[] diffList = {"Basiques.", "Classiques.", "Avancées."};
        switch (m_console.display("Vous", "Et je suis tout à fait capable de résoudre des enquêtes", diffList).execChoice()) {
            case 1:
                m_levelChoice = Difficulty.SIMPLE;
                break;
            case 2:
                m_levelChoice = Difficulty.MEDIUM;
                break;
            case 3:
                m_levelChoice = Difficulty.DIFFICULT;
                break;
        }
        m_console.display("Standardiste", "Mmmh. Votre futur supérieur vous attend à l'intérieur.").execContinue("Vous quittez la récéption");
        
        
        //etape 4 : initialisation
        m_currentInvestigation = m_dataSave.createGame(fullName, gender);
        m_currentInvestigation.getInvestigator().presentCharacter();
        
        //etape 5 : lancement du jeu
        m_console.display("Votre supérieur", "Voici des enquêtes à votre niveau, je vous laisse en choisir une.").execContinue("Vous choisissez une affaire");
        m_currentInvestigation.investigationMenu();
    }
}
