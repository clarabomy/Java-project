
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
    protected static Initialize m_construct = new Initialize();
    
    protected static Difficulties m_levelChoice;
    protected Investigation m_currentInvestigation = null;
    

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public static UserInterface getConsole() {
        return m_console;
    }
    
    public static Difficulties getLevelChoice() {
        return m_levelChoice;
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
            choicesList.add("Commencer une nouvelle enquête");  //nouvelle partie
            if (gotSaves) {
                choicesList.add("Reprendre une enquête");       //charger partie
            }
            if (this.m_currentInvestigation != null) {          //sauvegarder partie
                choicesList.add("Déposer un rapport d'enquête (sauvegarde)");
            }
            if (gotSaves) {
                choicesList.add("Abandonner une enquête\n\n");  //abandonner partie
            }      
            choicesList.add("Règles du jeu");
            choicesList.add("Quitter le bureau d'enquête");     //quitter jeu
        
            
            int choix = m_console.clean().display("Standardiste", "Bienvenue au Bureau d'enquête. Que puis-je pour vous?", convertArrayList(choicesList), true).execChoice();
            {//gère différents cas, incrémentations successives
                if (!gotSaves && choix >= 2) {
                    choix++;
                }
                if (!gotSaves && !gotInvest && choix >= 2) {
                    choix++;
                }
                if (!gotSaves && !gotInvest && choix >= 3) {
                    choix++;
                }
                if (!gotSaves && gotInvest && choix >= 4) {
                    choix++;
                }
                if (gotSaves && !gotInvest && choix >= 3) {
                    choix++;
                }
            }
            switch (choix) {
                case 1:
                    newInvestigation();
                    gameRules();
                    break;
                case 2:
                    //continueInvestigation
                    m_currentInvestigation = m_construct.importGame(m_files.readSaveFile());
                    m_currentInvestigation.investigationMenu();
                    break;
                case 3:
                    //saveInvestigation
                    ArrayList <String> gameData = m_construct.exportGame(m_currentInvestigation);
                    m_files.writeSaveFile(m_currentInvestigation.getInvestigator().getFullName(), convertArrayList(gameData));
                    break;
                case 4:
                    //dropInvestigation
                    m_console.display((m_files.deleteSaveFile()? "Partie supprimée." : "Echec de suppression.") + "\nRetours au menu principal.", false).execContinue(null);
                    break;
                case 5:
                    gameRules();
                    break;
                case 6:
                    exitGame = true;
                    break;
            }
        } while (!exitGame);//quitte le jeu en choisissant l'option dédiée
    }
    
    
    public void gameRules(){
        //affiche lecture en fichier
        /*
        deux grands axes : 
        personnages
            Enqueteur (capacités)
            Suspects (spécificités des catégories et capacités)
        éléments d'enquête
            Victime (spécificités)
            Arme et Scène du crime (spécificités)
            Indices (spécificités des catégories)
        */
    }
    
    public void newInvestigation() {
        //etape 1 : nom perso (nom fichier)
        String fullName = null;
        m_console.clean();
        do {
            String name = m_console.display("Standardiste", "Veuillez entrer votre nom :", false).execInput();
            String surname = m_console.display("Standardiste", "Et maintenant votre prénom :", false).execInput();
            fullName = surname + ' ' + name;
            if (Arrays.asList(m_files.getSavesName()).contains(fullName)) {//si nom déjà utilisé (nom enquêteur = nom fichier save)
                fullName = null;
                m_console.display("Vous", "Mais non, bien sûr! Je ne m'appelle pas comme ça.", true);
            }
        } while (fullName == null);
        
        
        //etape 2 : sexe perso
        Sex gender = null;
        String[] sexList = {"Un talentueux inspecteur.", "Une talentueuse inspectrice."};
        switch (m_console.display("Vous", "Je suis", sexList, true).execChoice()) {
            case 1:
                gender = Sex.HOMME;
                break;
            case 2:
                gender = Sex.FEMME;
                break;
        }
        
        
        //etape 3 : difficulté enquête
        String[] diffList = {"Basiques.", "Classiques.", "Avancées."};
        switch (m_console.display("Vous", "Et je suis tout à fait capable de résoudre des enquêtes", diffList, true).execChoice()) {
            case 1:
                m_levelChoice = Difficulties.SIMPLE;
                break;
            case 2:
                m_levelChoice = Difficulties.MEDIUM;
                break;
            case 3:
                m_levelChoice = Difficulties.DIFFICULT;
                break;
        }
        
        
        //etape 4 : initialisation
        m_console.display("Standardiste", "Alors voici des enquêtes à votre niveau !", true).execContinue("Vous choisissez une affaire");
        m_currentInvestigation = m_construct.createGame(fullName, gender);
        
        
        //etape 5 : lancer jeu
        m_console.display("Standardiste", "Allez, M" + (gender == Sex.HOMME? ". " : "me. ") + fullName.split(" ")[1] + ". Il faut se lancer, maintenant.", true).execContinue("Vous ouvrez le dossier"); 
        m_currentInvestigation.investigationMenu();
    }
}
