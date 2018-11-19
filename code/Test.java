
package project;

import java.util.ArrayList;
import project.game.Difficulties;
import project.game.Game;
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
import project.game.investigation.TestimonyType;


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
    Game m_jeu;
    
    
    public Test() {
        m_console = new UserInterface();
        
        m_testimonyRef = new int[2];
        m_elements = new InvestElement[2];
        m_player = new Investigator("Joueur", "Prénom", Sex.HOMME, 30, 60, 70, m_clueList, "progress");    
        
        Innocent m_innocent1 = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        Innocent m_innocent2 = new Innocent("Innocent2", "Huguette", Sex.FEMME, 55, 30, 50, "look2", "physicalAspect2", false, m_testimonyRef, "alibi2", m_clueList);
        m_suspectsList.add(m_innocent1);
        m_suspectsList.add(m_innocent2);
        
        int[] m_testimonyRef = {1,2};
        
        m_refProof.add(1);
        m_refProof.add(2);
       
        //m_clueList.add(clue3);
        //(InvestElement element, String content, boolean isFounded)
        //Innocent m_innocent1 = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        //Innocent m_innocent2 = new Innocent("Innocent2", "Huguette", Sex.FEMME, 55, 30, 50, "look2", "physicalAspect2", false, m_testimonyRef, "alibi2", m_clueList);
        //m_suspectsList.add(m_innocent1);
        //m_suspectsList.add(m_innocent2);
        //m_suspectsList.add(m_murderer);
        //m_suspectsList.add(m_crimePartner);
        
        
        //m_refProof.add(int1);
        //m_refProof.add(int2);
        //m_suspectsList = new Suspect[5];
        //m_suspectsList[1] = new CrimePartner("Complice", "Jean Lucette", Sex.FEMME, 32, 70, 30, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        //m_suspectsList[3] = new Innocent("Innocent", "Jean Jacques", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        //m_suspectsList[4] = new Innocent("Innocent", "Jean Philippe", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi");
        
        m_enquete = new Investigation(m_player, m_suspectsList, m_corpse, m_elements);
        
        m_jeu = new Game();
        m_jeu.setLevelChoice(Difficulties.DIFFICULT);
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
        //this.testInnocent();//débug ok
       //this.testCrimePartner(); //débug en cours
        //this.testMurderer();//débug en cours
          this.testVictim(); //débug en cours

        
        //project.game.investigation
        //this.testInvestElement();
        //this.testInvestigator();
        
        
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
        Murderer m_murderer = new Murderer("Criminel", "Jean Michel", Sex.HOMME, 30, 70, m_testimonyRef, "lookMurderer", "physicalAspectMurderer", "motive", m_clueList);
        CrimePartner m_crimePartner = new CrimePartner("CrimePartName", "CrimePartSurname", Sex.HOMME, 32, 66, 70, "lookCrimePart", "physicalCrimePart", false, m_testimonyRef, "alibiCrimePart", m_clueList, m_murderer.getFullName());
         m_suspectsList.add(m_crimePartner);
        //m_crimePartner.giveAlibi(); //erreur fonctions dés
        
        //m_crimePartner.giveTestimony(); //erreur fonctions dés
        
        //m_crimePartner.createFalseAlibi(); //débug ok mais null
        
        //m_crimePartner.createFalseTestimony(TestimonyType.SEEN); //débug ok 
    }
    
    public void testInnocent() {
        Innocent m_innocent = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        //m_innocent.displayStats(); //débug ok
        
        //m_innocent.beInterrogated(m_player); //débug ok
        
        //m_innocent.beArrested(); //débug ok
        
        //m_innocent.beDisculpated(); //débug ok
        
        //m_innocent.giveTestimony(); //débug ok
        
    }
    
    public void testMurderer() {//debug en cours
        //Murderer criminel = (Murderer) m_suspectsList[0];
        //Murderer criminel = new Murderer("Criminel", "Jean Michel", Sex.HOMME, 30, 70, m_testimonyRef, "lookMurderer", "physicalAspectMurderer", "motive", m_clueList);

        //criminel.createFalseAlibi();//débug ok
        
        //criminel.giveTestimony(); //pb fonctions dés
        
        //criminel.createFalseTestimony(TestimonyType.HEARD); //débug ok
        
        //criminel.confess();//débug ok
        
        //criminel.beArrested(); //débug ok
    }
    
    
    //project.game.investigation
    public void testInvestElement() {
        
    }
    
    
    public void testInvestigator() {
        
    }
    
    public void testVictim() {
        m_corpse = new Victim("VictimName", "VictimSurname", Sex.FEMME, 25, "deathDate", "deathCause", m_refProof);
        //m_corpse.presentCharacter();
        InvestElement elem = new InvestElement (InvestElementType.CRIME_SCENE, m_refProof);
        Proof clue4 = new Proof (elem, "contentClue4", false);
        m_clueList.add(clue4);
        Proof clue3 = new Proof (elem, "contentClue3", false);
        
        m_clueList.add(clue3);
        clue4.assignVictim(m_corpse, 1);
        //m_corpse.analyse(m_player); //bug indexOutOfBoundsException
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
