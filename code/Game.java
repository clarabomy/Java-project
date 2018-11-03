/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import project.game.Difficulties;
import project.game.Investigation;
import project.game.UserInterface;

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
            switch (m_console.display("Menu principal", choicesList).execSingleChoice("action")) {
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
        switch (m_console.display(intro[0], choices[0]).execSingleChoice("enquête")) {
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
        
        m_console.display(intro[1], choices[1]).execContinue();//appel chaîné : renvoie this en fin de fonction
        
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
        //supprime le fichier ou vide son contenu
    }//end void dropInvestigation
}
