
package project;

import project.game.UserInterface;
import project.game.character.Investigator;
import project.game.character.Sex;
import project.game.character.Victim;
import project.game.character.suspect.CrimePartner;
import project.game.character.suspect.Innocent;
import project.game.character.suspect.Murderer;
import project.game.character.suspect.Suspect;
import project.game.investigation.Clue;
import project.game.investigation.InvestElement;
import project.game.investigation.Investigation;
import project.game.investigation.Proof;


/**
 *
 * @author ISEN
 */
public class Test {
    private final UserInterface m_console;
    Investigation m_enquete;
    Victim m_corpse;
    Investigator m_player;
    
    int[] m_testimonyRef;
    Clue[] m_cluesList;
    int[] m_refProof;
    InvestElement[] m_elements;
    Suspect[] m_suspectsList;
    
    
    public Test() {
        m_console = new UserInterface();
        
        m_testimonyRef = new int[2];
        m_cluesList = new Clue[2];
        m_refProof = new int[2];
        m_elements = new InvestElement[2];

        m_player = new Investigator("Joueur", "Prénom", Sex.MAN, 30, 60, 75, m_cluesList, "progress");        
        m_corpse = new Victim("name", "surname", Sex.WOMAN, 25, "deathDate", "deathCause", m_refProof);
        
        m_suspectsList = new Suspect[5];
        m_suspectsList[0] = new Murderer("Criminel", "Jean Michel", Sex.MAN, 30, 70, "look", "physicalAspect", false, m_testimonyRef, "motive");
        m_suspectsList[1] = new CrimePartner("Complice", "Jean Lucette", Sex.WOMAN, 32, 70, 30, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        m_suspectsList[2] = new Innocent("Innocent", "Jean François", Sex.MAN, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        m_suspectsList[3] = new Innocent("Innocent", "Jean Jacques", Sex.MAN, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        m_suspectsList[4] = new Innocent("Innocent", "Jean Philippe", Sex.MAN, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        
        m_enquete = new Investigation(m_player, m_suspectsList, m_corpse, m_elements);
    }
        
    public void test1() {//à répartir dans les catégories testées
        int[] tab = {1,2,3};
        InvestElement sang = new InvestElement(tab);
        Proof clue = new Proof(sang, "sang", false);
        Proof clue2 = new Proof(sang, "sang", false);
        Clue[] clue_tab = {clue,clue2};
        Investigator player = new Investigator("Bourgain", "Manon", Sex.WOMAN, 34, 60, 32, clue_tab, "100");
        //Investigator.dice();
        //player.presentCharacter();
        player.displayStats();
        //System.out.println(Sex.WOMAN.toString());
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
        Murderer criminel = (Murderer) m_suspectsList[0];
        
        criminel.createTestimony();//code en cours
        
        //criminel.confess();//débug ok
        
        //criminel.contradiction();//débug ok
        
        //criminel.createFalseAlibi();//débug ok
        
        //m_console.display(criminel.getMotive(), false).execContinue();//débug ok
        
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
        
        m_console.display("test 1", true);
        
        m_console.display("Test 2", choices, true);
        
        m_console.display("debuggeur", "Test 3", true);
        
        m_console.display("debuggeur", "Test 4", choices, true);
        
        m_console.display("test 5", false).execContinue().clean();
        
        result = m_console.display("test 6", choices, false).execSingleChoice();
        System.out.printf("Choix enregistré : %d\n", result);
    }
    
    
    //project
    public void testGame() {
        
    }
}
