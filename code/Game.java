/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ISEN
 */
public class Game {
    Investigation currentGame;
    Difficulties levelChoice;
    /*protected?*/ Console window;

    public Game(Investigation currentGame, Difficulties levelChoice, Console window) {
        this.currentGame = currentGame;
        this.levelChoice = levelChoice;
        this.window = window;
    }//end constructor
    
    public static void main(String[] args) {
        // TODO code application logic here
        int[] tab = {1,2,3};
        System.out.println(tab.length);
        Victim victim = new Victim("21 janvier", "meurtre", tab, "Olivier", false, 32);
    }//end main
    
    public void gameMenu() {//menu général du jeu
        boolean exitGame = false;
        do {
            String[] choicesList = {"Règles du jeu\n",
                                    "Commencer une nouvelle enquête",   //nouvelle partie
                                    "Reprendre une enquête",            //charger partie
                                    "Déposer un rapport d'enquête",     //sauvegarder partie
                                    "Abandonner une enquête\n",         //abandonner partie
                                    "Quitter le bureau d'enquête"};     //quitter jeu
            switch (window.display("Menu principal", choicesList).playerSingleChoice()) {
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
    }//end void gameMenu()
    
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
        switch (window.display(intro[0], choices[0]).playerSingleChoice()) {
            case 0:
                //levelChoice.SIMPLE;
                break;
            case 1:
                //levelChoice.MEDIUM;
                break;
            case 2:
                //levelChoice.DIFFICULT;
                break;
        }
        
        window.display(intro[1], choices[1]).playerSingleChoice();//appel chaîné : renvoie this en fin de fonction
        
        //initialise classes avec aléatoire
        
        currentGame.mainMenu();
    }//end void newInvestigation()
  
    public void continueInvestigation(){
        //initialise classes avec lecture fichier
        
        currentGame.mainMenu();
    }//end void continueInvestigation()
    
    public void submitReport(){
        //ecrit en fichier contenu de classes
    }//end void submitReport()
    
    public void dropInvestigation(){
        //supprime le fichier ou vide son contenu
    }//end void dropInvestigation()
}
