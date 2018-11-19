
package project.game;

import java.io.File;
import java.util.ArrayList;
import project.game.investigation.Investigation;

/**
 *
 * @author ISEN
 */
public class Game {
    protected Investigation m_currentGame;//memberOfClass_attributeName
    protected static Difficulties m_levelChoice;
    protected UserInterface m_console = new UserInterface();
    protected static ArrayList<String> m_victimList = new ArrayList();//victimes à l'initialisation
    protected static ArrayList<String> m_weaponList = new ArrayList();//armes à l'initialisation
    protected static ArrayList<String> m_mobileList = new ArrayList();//mobiles à l'initialisation
    protected static String m_murderer;//à l'initialisation
    protected static String m_victim;//à l'initialisation
    protected static String m_weapon;//à l'initialisation
    protected static String m_mobile;//à l'initialisation
    

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
    }
    
    //pour Test
    public Game(Difficulties levelchoice, String murderer, String victim, String weapon, String mobile) {
        m_levelChoice = levelchoice;
        m_murderer = murderer;
        m_victim = victim;
        m_weapon = weapon;
        m_mobile = mobile;
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public static Difficulties getLevelChoice() {
        return m_levelChoice;
    }
    
    public static int getDifficulty() {
        return m_levelChoice == Difficulties.SIMPLE? 0 : (m_levelChoice == Difficulties.MEDIUM? 1 : 2);
    }
    
    public static ArrayList<String> getWeaponList() {
        return m_weaponList;
    }
    
    public static ArrayList<String> getMobileList() {
        return m_mobileList;
    }
    
    public static ArrayList<String> getVictimList() {
        return m_victimList;
    }
    
    public static String getMurderer() {
        return m_murderer;
    }
    
    public static String getVictim() {
        return m_victim;
    }
    
    public static String getWeapon() {
        return m_weapon;
    }
    
    public static String getMobile() {
        return m_mobile;
    }
    
    /*$$ METHODS $$*/   
    public void gameMenu() {//menu général du jeu
        boolean exitGame = false;
        do {
            String[] choicesList = {"Règles du jeu\n",
                                    "Commencer une nouvelle enquête",   //nouvelle partie
                                    "Reprendre une enquête",            //charger partie
                                    "Déposer un rapport d'enquête",     //sauvegarder partie
                                    "Abandonner une enquête\n",         //abandonner partie
                                    "Quitter le bureau d'enquête"};     //quitter jeu
            switch (m_console.display("Menu principal", choicesList, false).execChoice()) {
                case 0:
                    gameRules();
                    break;
                case 1:
                    newInvestigation();
                    break;
                case 2:
                    continueInvestigation();
                    break;
                case 3:
                    submitReport();
                    break;
                case 4:
                    dropInvestigation();
                    break;
                case 5:
                    exitGame = true;
                    break;
            }
        } while (!exitGame);//sort en choisissant l'option dédiée
    }//end void gameMenu
    
    
    public void gameRules(){
        //affiche lecture en fichier
    }//end void gameRules
    
    
    public void newInvestigation(){
        //lecture en fichier...
        String[] intro = {"Bonjour, inspecteur. Bon, écoute, j'ai trois enquêtes pour toi. Je te laisse choisir celle que tu préfères.",
                            "Tu as fais ton choix? Bien. Voyons voir ce qui t'attend."};
        String[][] choices = {{"Une enquête facile", "Une enquête classique", "Une enquête difficile"},
                                    {"Ouvrir le dossier"}};
        
        //intro
        switch (m_console.display("Votre supérieur", intro[0], choices[0], false).execChoice()) {
            case 0:
                m_levelChoice = Difficulties.SIMPLE;
                break;
            case 1:
                m_levelChoice = Difficulties.MEDIUM;
                break;
            case 2:
                m_levelChoice = Difficulties.DIFFICULT;
                break;
        }
        
        m_console.display("Votre supérieur", intro[1], choices[1], false).execContinue();//appel chaîné : renvoie this en fin de fonction
        
        //initialise classes avec aléatoire
        
        m_currentGame.mainMenu();
    }//end void newInvestigation
  
    
    public void continueInvestigation(){
        //initialise classes avec lecture fichier
        
        m_currentGame.mainMenu();
    }//end void continueInvestigation
    
    
    public void submitReport(){
        //ecrit en fichier contenu de classes
    }//end void submitReport
    
    
    public void dropInvestigation(){
        //supprime le fichier 
        File file = new File("invest.txt");
        if(file.delete()) {
            System.out.printf("%s is deleted. Vous pouvez commencer une nouvelle enquête\n", file.getName());
        }
        else {
            System.out.println("Delete operation is failed.");
        }   

    }//end void dropInvestigation

}
