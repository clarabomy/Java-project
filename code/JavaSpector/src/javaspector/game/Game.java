
package javaspector.game;

import java.util.ArrayList;
import java.util.Arrays;
import javaspector.game.character.Sex;
import javaspector.game.investigation.Investigation;

/**
 *
 * Contain the game
 * @author Clara BOMY
 */ 
public class Game {
    protected static UserInterface m_console = new UserInterface();//memberOfClass_attributeName
    protected static FileManager m_files = new FileManager();
    protected static DataManager m_dataSave = new DataManager();
    protected static Difficulty m_levelChoice;
    protected static boolean m_endedGame;
    protected Investigation m_currentInvestigation = null;
    
    /** 
     * Getter of the class
     * @return console  reference to the console - a single instance for the entire game
     */ 
    public static UserInterface getConsole() {
        return m_console;
    }
    
    /** 
     * Getter of the class
     * @return gameDifficulty   the difficulty of the actual investigation
     */ 
    public static Difficulty getLevelChoice() {
        return m_levelChoice;
    }
    
    /** 
     * Getter of the class
     * @return isEndedGame  true if the investigation is finish, false then
     */ 
    public static boolean isEndedGame() {
        return m_endedGame;
    }
    
    /** 
     * Setter of the class
     */ 
    public static void setEndedGame() {
        m_endedGame = true;
    }
    
    /** 
     * Convert an arrayList to a table
     * @param list          arrayList to convert
     * @return StringTab    array resulting
     */ 
    public static String[] convertArrayList(ArrayList <String> list) {
        String[] tab = new String[list.size()];
        
        int i = 0;
        for (String current : list) {
            tab[i++] = current;
        }
        
        return tab;
    }
    
    /** 
     * Main menu of the game
     */ 
    public void gameMenu() {
        boolean exitGame = false;
        do {
            boolean gotSaves = m_files.getSavesName().length != 0;//true if there is saves files in save folder
            boolean gotInvest = m_currentInvestigation != null;//true if the game run an investigation
            
            ArrayList choicesList = new ArrayList();
            {//menu constitution
                choicesList.add("Commencer une nouvelle enquête" + (gotSaves || gotInvest? "" : "\n\n"));
                if (m_currentInvestigation != null) {
                    choicesList.add("Continuer l'enquête en cours");
                    choicesList.add("Déposer un rapport d'enquête" + (gotSaves? "" : "\n\n"));
                }
                if (gotSaves) {
                    choicesList.add("Reprendre une enquête");
                    choicesList.add("Abandonner une enquête\n\n");
                }      
                choicesList.add("Règles du jeu");
                choicesList.add("Quitter le bureau d'enquête");
            }
        
            
            int choix = m_console.clean().display("Standardiste", "Bienvenue à JavaSpector. Que puis-je pour vous?", convertArrayList(choicesList)).execChoice();
            if (!gotSaves && !gotInvest && choix >= 2) {//choices 2, 3, 4, 5 not displayed : must increment for matching unser choice
                choix += 4;
            }
            else if ((!gotInvest && choix >=2) || (!gotSaves && gotInvest && choix >= 4)) {//choices 2, 3 or 4, 5 not displayed : must increment for matching unser choice
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
                    m_files.setCurrentFileName(m_currentInvestigation.getInvestigator().getFullName());//memorize filename in case of ending game
                    m_files.writeSaveFile(m_currentInvestigation.getInvestigator().getFullName(), convertArrayList(gameData));
                    break;
                case 4:
                    //loadInvestigation
                    ArrayList<String> contentFile = m_files.readSaveFile(m_files.selectFile("charger"));//constitute data tab of selected file
                    if (contentFile != null) {//if selected file is "cancel" option, contentFile == null and we go on
                        m_currentInvestigation = m_dataSave.importGame(contentFile);
                        m_files.setCurrentFileName(m_currentInvestigation.getInvestigator().getFullName());//memorize filename in case of ending game
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
            
            if (m_endedGame && m_files.getCurrentFileName() != null) {//at the ending game, if we got a file, we delete it
                m_files.deleteSaveFile(m_files.getCurrentFileName());
                m_currentInvestigation = null;
            }
        } while (!exitGame);
    }
   
    /** 
     * Initialization of a new investigation
     */ 
    public void newInvestigation() {
        //step 1 : player name (determines savefile name)
        String fullName = null;
        m_console.clean();
        do {
            String name = m_console.display("Standardiste", "Veuillez entrer votre nom :").execInput();
            String surname = m_console.display("Standardiste", "Et maintenant votre prénom :").execInput();
            fullName = surname + ' ' + name;
            if (Arrays.asList(m_files.getSavesName()).contains(fullName) || //if already used
                    //if contains an incorrect file symbol
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
        
        
        //step 2 : player sex
        Sex gender = null;
        String[] sexList = {"Un talentueux inspecteur !", "Une talentueuse inspectrice !"};
        gender = m_console.display("Vous", "Croyez moi, je suis", sexList).execChoice() == 1? Sex.HOMME : Sex.FEMME;
        
        
        //step 3 : investigation difficulty
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
        
        
        //step 4 : initialization
        m_currentInvestigation = m_dataSave.createGame(fullName, gender);
        m_currentInvestigation.getInvestigator().presentCharacter();
        
        //step 5 : launch game
        m_console.display("Votre supérieur", "Voici des enquêtes à votre niveau, je vous laisse en choisir une.").execContinue("Vous choisissez une affaire");
        m_currentInvestigation.investigationMenu();
    }
}
