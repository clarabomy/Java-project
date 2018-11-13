
package project.game;

import java.io.File;
import project.game.investigation.Investigation;

/**
 *
 * @author ISEN
 */
public class Game {
    protected Investigation m_currentGame;//memberOfClass_attributeName
    protected Difficulties m_levelChoice;
    protected UserInterface m_console;

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
        this.m_console = new UserInterface();
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public Difficulties getLevelChoice() {
        return m_levelChoice;
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
            switch (m_console.display("Menu principal", choicesList, false).execSingleChoice()) {
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
        switch (m_console.display("Votre supérieur", intro[0], choices[0], false).execSingleChoice()) {
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
        if(file.delete()) System.out.printf("%s is deleted. Vous pouvez commencer une nouvelle enquête\n", file.getName());
        else System.out.println("Delete operation is failed.");   

    }//end void dropInvestigation
}
