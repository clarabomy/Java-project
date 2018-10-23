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
    
    public void newInvestigation(){
    }
    
    public void submitReport(){
    }
  
    public void continueInvestigation(){
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
