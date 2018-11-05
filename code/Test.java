
package project;

import project.game.UserInterface;
import project.game.investigation.Investigator;
import project.game.investigation.clue.Clue;
import project.game.investigation.clue.Proof;
import project.game.investigation.InvestElement;
import static project.game.investigation.LiveCharacter.throwDoubleDices;
import project.game.investigation.Sex;
import static project.game.investigation.suspect.Lie.M_COHERENCE_VALID;
import static project.game.investigation.suspect.Lie.M_CREDIBILITY_VALID;
import project.game.investigation.suspect.Murderer;


/**
 *
 * @author ISEN
 */
public class Test {
    private UserInterface console = new UserInterface();
        
    public void test1() {//à répartir dans les catégories testées
        int[] tab = {1,2,3};
        InvestElement sang = new InvestElement(tab);
        Proof clue = new Proof(sang, "sang", false);
        Proof clue2 = new Proof(sang, "sang", false);
        Clue[] clue_tab = {clue,clue2};
        Investigator player = new Investigator("Bourgain", "Manon", Sex.WOMAN, 34, 60, 70, 32, clue_tab, "100");
        Investigator.rollDice();
        player.presentCharacter();
        player.displayStats();
        System.out.println(Sex.WOMAN.toString());
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
        this.testMurderer();//débug en cours
        
        
        //project.game.investigation
        //this.testInvestElement();
        //this.testInvestigator();
        //this.testVictim();
        
        
        //project.game
        //this.testInvestigation();
        //this.testUserInterface();//débug ok
        
        
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
    
    public void testMurderer() {//debug en cours
        int[] testimonyRef = {};
        Murderer criminel = new Murderer("name", "surname", Sex.MAN, 30, 70, "look", "physicalAspect", false, testimonyRef, "motive");
        
        criminel.addTestimony();//coder
        
        //criminel.confess();//débug ok
        
        //criminel.contradiction();//débug ok
        
        //criminel.createFalseLead();//débug ok
        
        //console.display(criminel.getMotive(), false).execContinue();//débug ok
        
        //criminel.giveAlibi();//débug ok
        
        //criminel.giveTestimony();//débug ok
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
    
    public void testUserInterface() {//débug ok
        String choices[] = {"option 1", "option 2", "option 3"};
        int result;
        
        console.display("test 1", true);
        
        console.display("Test 2", choices, true);
        
        console.display("debuggeur", "Test 3", true);
        
        console.display("debuggeur", "Test 4", choices, true);
        
        console.display("test 5", false).execContinue().clean();
        
        result = console.display("test 6", choices, false).execSingleChoice();
        System.out.printf("Choix enregistré : %d\n", result);
    }
    
    
    //project
    public void testGame() {
        
    }
}
