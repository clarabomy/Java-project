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
import project.game.investigation.InvestElement;
import project.game.investigation.Sex;


/**
 *
 * @author ISEN
 */
public class Test {
    public void test1() {//à répartir dans les catégories testées
        int[] tab = {1,2,3};
        InvestElement sang = new InvestElement(tab);
        Proof clue = new Proof(sang, "sang", false);
        Proof clue2 = new Proof(sang, "sang", false);
        Clue[] clue_tab = {clue,clue2};
        Investigator player = new Investigator("Michel", Sex.MAN, 34, 60, 70, 32, clue_tab, "100");
        Investigator.rollDice();
    }
    
    public void debug() {
        //débug du plus précis au plus général
        //this.test1();//à répartir dans les catégories testées
        
        
        //project.game.investigation.clue
        //this.testProof();
        //this.testTestimony();
        
        
        //project.game.investigation.suspect
        //this.testCrimePartner();
        //this.testInnocent();
        //this.testMurderer();
        
        
        //project.game.investigation
        //this.testInvestElement();
        //this.testInvestigator();
        //this.testVictim();
        
        
        //project.game
        //this.testInvestigation();
        this.testUserInterface();
        
        
        //project
        //this.testGame();
    }
    
    
    //project.game.investigation.clue
    public void testProof() {
        
    }
    
    public void testTestimony() {
        
    }
    
    
    //project.game.investigation.suspect
    public void testCrimePartner() {
        
    }
    
    public void testInnocent() {
        
    }
    
    public void testMurderer() {
        
    }
    
    
    //project.game.investigation
    public void testInvestElement() {
        
    }
    
    
    public void testInvestigator() {
        
    }
    
    public void testVictim() {
        
    }
    
    
    //project.game
    public void testInvestigation() {
        
    }
    
    public void testUserInterface() {//1 pb left
        UserInterface console = new UserInterface();
        String choices[] = {"option 1", "option 2", "option 3"};
        int result = 0;
        
        console.display("test 1", true);
        
        console.display("Test 2", choices, true);
        
        console.display("debuggeur", "Test 3", true);
        
        console.display("debuggeur", "Test 4", choices, true);
        
        console.display("test 5", false).execContinue().clean();
        
        result = console.display("test 6", choices, false).execSingleChoice();//pb
        System.out.println(result);
    }
    
    
    //project
    public void testGame() {
        
    }
}
