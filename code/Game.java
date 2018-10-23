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
    Console window;

    public Game(Investigation currentGame, Difficulties levelChoice, Console window) {
        this.currentGame = currentGame;
        this.levelChoice = levelChoice;
        this.window = window;
    }
    
    public int mainMenu() {
        window.multiChoice = false;
        window.display("Menu principal");

        String[] choicesList = {"Commencer une nouvelle enquête", 
                                "Reprendre une enquête", 
                                "Abandonner une enquête\n", 
                                "Règles du jeu",
                                "Quitter le jeu"};
        window.addChoices(choicesList);

        switch (window.PlayerChoices()[0]) {//choix unique
            case 0:
                newInvestigation();
                break;
            case 1:
                continueInvestigation();
                break;
            case 2:
                dropInvestigation();
                break;
            case 3:
                gameRules();
                break;
            case 4:
                return 1;
        }
        return 0;
    }
    
    public void startGame() {
        int menuChoice = 0;
        do {
            //donne situation au joueur
            String text = "Ce qui se passe dans le jeu";
            window.display(text);
            //window.multiChoice = (/*selon situation*/)? true : false;
            
            //donne choix au joueur
            String[] choicesList = {"choix 1", "choix 2", "choix 3", "choix 4"};
            if (!window.multiChoice) { //ajoute retours au menu si choix unique
                choicesList[choicesList.length] = "Retours au menu principal";
                menuChoice = choicesList.length + 1;
            }
            window.addChoices(choicesList);
            
            //traite choix du joueur
            //...
        } while (window.multiChoice || window.PlayerChoices()[0] != menuChoice);//si choix unique est retours au menu, on quitte
    }
    
    public void newInvestigation(){
        //initialise
        
        startGame();
    }
    
    public void submitReport(){
    }
  
    public void continueInvestigation(){
        //lecture fichier pour remplir classes
        
        startGame();
    }
    
    public void dropInvestigation(){
    }
    
    public void gameRules(){
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        int[] tab = {1,2,3};
        System.out.println(tab.length);
        Victim victim = new Victim("21 janvier", "meurtre", tab, "Olivier", false, 32);
    }
}
