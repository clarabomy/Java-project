/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import project.game.UserInterface;
import project.game.investigation.Investigator;
import project.game.investigation.clue.Clue;
import project.game.investigation.clue.Proof;
import project.game.investigation.investElement.InvestElement;


/**
 *
 * @author ISEN
 */
public class Test {
    public void test1() {
        int[] tab = {1,2,3};//à répartir dans les catégories testées
        InvestElement sang = new InvestElement(tab);
        Proof clue = new Proof(sang, "sang", false);
        Proof clue2 = new Proof(sang, "sang", false);
        Clue[] clue_tab = {clue,clue2};
        Investigator player = new Investigator("Michel", false, 34, 60, 70, 32, clue_tab, "100");
        player.rollDice();
    }
    
    
    
    //project.game.investigation.clue
    public void testProof() {
        
    }
    
    public void testTestimony() {
        
    }
    
    
    //project.game.investigation.investElement
    public void testCrimeScene() {
        
    }
    
    public void testCrimeWeapon() {
        
    }
    
    public void testInvestElement() {
        
    }
    
    
    //project.game.investigation.suspect
    public void testCrimePartner() {
        
    }
    
    public void testInnocent() {
        
    }
    
    public void testMurderer() {
        
    }
    
    
    //project.game.investigation
    public void testInvestigator() {
        
    }
    
    public void testVictim() {
        
    }
    
    
    //project.game
    public void testInvestigation() {
        
    }
    
    public void testUserInterface() {
        UserInterface console = new UserInterface();
        
        console.display("Test 1", "Continuer").execContinue();
        
        String choices[] = {"option 1", "option 2", "option 3"};
        int value = console.clean().display("Test 2", choices).execSingleChoice("option");
        System.out.println("Vous avez choisi l'option " + value);
        
        int values[] = console.clean().display("Test 3 (implémenter?)", choices).execMultiChoice();
    }
    
    
    //project
    public void testGame() {
        
    }
}
