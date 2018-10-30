/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;


/**
 *
 * @author Thibaut
 */
public class Source {
    public static void main(String[] args) {
        //débug du plus précis au plus général
        Test debug = new Test();
        debug.test1();//à répartir dans les catégories testées
        
        
        //project.game.investigation.investElement
        debug.testCrimeScene();
        debug.testCrimeWeapon();
        debug.testInvestElement();
        //project.game.investigation.clue
        debug.testProof();
        debug.testTestimony();
        
        
        //project.game.investigation.investElement
        debug.testCrimeScene();
        debug.testCrimeWeapon();
        debug.testInvestElement();
        
        
        //project.game.investigation.suspect
        debug.testCrimePartner();
        debug.testInnocent();
        debug.testMurderer();
        
        
        //project.game.investigation
        debug.testInvestigator();
        debug.testVictim();
        
        
        //project.game
        debug.testInvestigation();
        debug.testUserInterface();
        
        
        //project
        debug.testGame();
    }
}
