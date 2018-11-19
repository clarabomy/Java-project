
package project;

import java.util.ArrayList;
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
import project.game.investigation.InvestElementType;
import project.game.investigation.Investigation;
import project.game.investigation.Proof;
import project.game.investigation.Testimony;


/**
 *
 * @author ISEN
 */
public class Test {
    private final UserInterface m_console;
    Investigation m_enquete;
    Victim m_corpse;
    Investigator m_player;
    ArrayList <Suspect> m_suspectsList = new ArrayList();
    ArrayList <Clue> m_clueList = new ArrayList();
    int[] m_testimonyRef;
    ArrayList <Integer> m_refProof = new ArrayList();
    InvestElement[] m_elements;
    
    
    public Test() {
        m_console = new UserInterface();
        
        m_testimonyRef = new int[2];
        m_elements = new InvestElement[2];
        m_player = new Investigator("Joueur", "Prénom", Sex.HOMME, 30, 60, 70, m_clueList, "progress");    
        
        CrimePartner m_crimePartner = new CrimePartner("CrimePartName", "CrimePartSurname", Sex.HOMME, 32, 66, 70, "lookCrimePart", "physicalCrimePart", false, m_testimonyRef, "alibiCrimePart", m_clueList);
        Murderer m_murderer = new Murderer("Criminel", "Jean Michel", Sex.HOMME, 30, 70, m_testimonyRef, "lookMurderer", "physicalAspectMurderer", "motive", m_clueList);
        Innocent m_innocent1 = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        Innocent m_innocent2 = new Innocent("Innocent2", "Huguette", Sex.FEMME, 55, 30, 50, "look2", "physicalAspect2", false, m_testimonyRef, "alibi2", m_clueList);
        
        int[] m_testimonyRef = {1,2};
        
        m_refProof.add(1);
        m_refProof.add(2);
        InvestElement investelem1 = new InvestElement(InvestElementType.CRIME_SCENE, m_refProof);
        Testimony clue1 = new Testimony (m_innocent1, false, "content_clue1");
        Testimony clue2 = new Testimony (m_innocent2, false, "content_clue2");
        Proof clue3 = new Proof (investelem1, "contentClue3", false);
        
        m_clueList.add(clue1);
        m_clueList.add(clue2);
        m_clueList.add(clue3);
        //(InvestElement element, String content, boolean isFounded)
        //Innocent m_innocent1 = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        //Innocent m_innocent2 = new Innocent("Innocent2", "Huguette", Sex.FEMME, 55, 30, 50, "look2", "physicalAspect2", false, m_testimonyRef, "alibi2", m_clueList);
        //m_suspectsList.add(m_innocent1);
        //m_suspectsList.add(m_innocent2);
        //m_suspectsList.add(m_murderer);
        //m_suspectsList.add(m_crimePartner);
        
        //m_corpse = new Victim("VictimName", "VictimSurname", Sex.FEMME, 25, "deathDate", "deathCause", m_refProof);
        
        //m_refProof.add(int1);
        //m_refProof.add(int2);
        //m_suspectsList = new Suspect[5];
        //m_suspectsList[1] = new CrimePartner("Complice", "Jean Lucette", Sex.FEMME, 32, 70, 30, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        //m_suspectsList[3] = new Innocent("Innocent", "Jean Jacques", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        //m_suspectsList[4] = new Innocent("Innocent", "Jean Philippe", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        
        m_enquete = new Investigation(m_player, m_suspectsList, m_corpse, m_elements);
    }
        
    public void test1() {//à répartir dans les catégories testées
        int[] tab = {1,2,3};
        Investigator player = new Investigator("Bourgain", "Manon", Sex.FEMME, 34, 60, 32, m_clueList, "100");
        //Investigator.dice();
        //player.presentCharacter();
        player.displayStats();
        //System.out.println(Sex.FEMME.toString());
    }
    
    public void debug() {
        //débug du plus précis au plus général
        //this.test1();//à répartir dans les catégories testées
        
        
        //project.game.investigation.clue
        //this.testProof();
        //this.testTestimony();
        
        
        //project.game.investigation.suspect
        //this.testCrimePartner();
        this.testInnocent();
        //this.testMurderer();//débug en cours
        
        
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
        Innocent m_innocent = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        //m_innocent.displayStats(); //débug ok
        
        //m_innocent.beInterrogated(player); //erreur fonctions dés
        
        //m_innocent.beArrested(); //débug ok
        
        //m_innocent.beDisculpated(); //débug ok
        
        //m_innocent.giveTestimony(); //débug ok
        
    }
    
    public void testMurderer() {//debug en cours
        //Murderer criminel = (Murderer) m_suspectsList[0];
        
        //criminel.createTestimony();//code en cours
        
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
