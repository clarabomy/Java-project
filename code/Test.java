
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
import project.game.investigation.DepositionType;
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
    ArrayList <Suspect> m_suspectsList = new ArrayList();
    ArrayList <Clue> m_clueList = new ArrayList();
    int[] m_testimonyRef;
    ArrayList <Integer> m_refProof = new ArrayList();
    InvestElement[] m_elements;
    Game m_jeu;
    
    Proof proof1 = new Proof("victim", "sang");
    Proof proof2 = new Proof("victim", "coups de couteaux à l'épaule");
    Proof proof3 = new Proof("crimeWeapon", "sang");
    Proof proof4 = new Proof("crimeWeapon", "verre brisé");
    Proof proof5 = new Proof("crimeScene", "bouteille d'eau");
    Proof proof6 = new Proof("crimeScene", "revolver");
    
    
    public Test() {
        m_console = new UserInterface();
        
        //m_testimonyRef = new int[2];
        //m_elements = new InvestElement[2];
        //m_player = new Investigator("Joueur", "Prénom", Sex.HOMME, 30, 60, 70, m_clueList, "progress");    
        
        //Innocent m_innocent1 = new Innocent("Innocent1", "Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", false, m_testimonyRef, "alibi", m_clueList);
        //Innocent m_innocent2 = new Innocent("Innocent2", "Huguette", Sex.FEMME, 55, 30, 50, "look2", "physicalAspect2", false, m_testimonyRef, "alibi2", m_clueList);
        //m_suspectsList.add(m_innocent1);
        //m_suspectsList.add(m_innocent2);
               
 
       
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
        
        //m_enquete = new Investigation(m_player, m_suspectsList, m_corpse, m_elements);
        
        //m_jeu = new Game(Difficulties.DIFFICULT, "Jean Coupable", "Jeanne Victime", "arme", "folie");
    }
    
    public void debug() {
        ArrayList <Proof> m_proofList = new ArrayList();
        m_proofList.add(proof1);
        m_proofList.add(proof2);

        ArrayList <Proof> m_proofListWeapon = new ArrayList();
        m_proofListWeapon.add(proof3);
        m_proofListWeapon.add(proof4);

        ArrayList <Proof> m_proofListScene = new ArrayList();
        m_proofListScene.add(proof5);
        m_proofListScene.add(proof6);    
        m_player = new Investigator("nom prenom", Sex.FEMME, 60, 70, "trueMurderer", "trueVictim", "TrueWeapon", "TrueMobile");    
        m_corpse = new Victim("NameVictim SurnameVictim", Sex.FEMME, 25, "deathdate", "deathcaise", m_proofList);
        
        InvestElement crimeWeapon = new InvestElement("crimeWeapon", m_proofListWeapon);
        InvestElement crimeScene = new InvestElement("crimeScene", m_proofListScene);        
        ArrayList <Suspect> suspectsList = new ArrayList();
        Murderer m_murderer = new Murderer("Criminel Jean Michel", Sex.HOMME, 30, 70, "lookMurderer", "physicalAspectMurderer", "motive");
        CrimePartner m_crimePartner = new CrimePartner("CrimePartName CrimePartSurname", Sex.FEMME, 32, 66, 70, "lookCrimePart", "physicalCrimePart", "alibiCrimePart", m_murderer.getFullName());
        Innocent m_innocent1 = new Innocent("Innocent1 Jean François", Sex.HOMME, 70, 80, 80, "look", "physicalAspect", "alibi", "heard", "seen");
        Innocent m_innocent2 = new Innocent("Innocent2 Caroline", Sex.FEMME, 20, 30, 20, "look", "physicalAspect", "alibi", "heard", "seen");
        Innocent m_innocent3 = new Innocent("Innocent3 Clement", Sex.HOMME, 32, 45, 65, "look", "physicalAspect", "alibi", "heard", "seen");
        suspectsList.add(m_murderer);
        suspectsList.add(m_crimePartner);
        suspectsList.add(m_innocent1);
        suspectsList.add(m_innocent2);
        suspectsList.add(m_innocent2);
        
        Investigation enquete = new Investigation(m_player, suspectsList, m_corpse, crimeWeapon, crimeScene);
        
        //Test innocent
        //m_innocent1.displayStats(); //débug ok
        
        //m_innocent1.beInterrogated(m_player); //spécifier les lancers
        
        //m_innocent1.beArrested(); //débug ok
        
        //m_innocent1.beDisculpated(); //débug ok
        
        //m_innocent1.giveTestimony(); //débug ok

        //Test murderer
        //m_murderer.giveAlibi();//débug ok
        
        //m_murderer.giveTestimony(); //débug ok
        
        //m_murderer.createFalse(DepositionType.HEARD); //débug ok
        
        //m_murderer.confess();//débug ok
        
        //m_murderer.beArrested(); //débug ok

        // Test crimePartner
        //m_crimePartner.giveAlibi(); //débug ok
        
        //m_crimePartner.giveTestimony(); //débug ok
        
        //m_crimePartner.createFalse(DepositionType.HEARD); //débug ok
        
        //Test victim
       // m_corpse.presentCharacter(); //debug ok
        //m_corpse.analyse(m_player); //débug ok
        
        //Test élément d'enquête
        //crimeWeapon.analyse(m_player); //debug ok
        //crimeScene.analyse(m_player); //debug ok
        
        //Test investigateur
        //m_player.presentCharacter(); //debug ok
        
        
        
        
        
        
        
        
        
        
        
        //$$$$ débug du plus précis au plus général $$$$
        
        //project.game.investigation.clue
        //this.testProof();
        //this.testTestimony();
        
        
        //project.game.investigation.suspect
        //this.testInnocent();//débug ok
       //this.testCrimePartner(); //débug en cours
        //this.testMurderer();//débug en cours
          //this.testVictim(); //débug en cours

        
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
        
        //m_suspectsList.add(m_crimePartner);
        //m_crimePartner.giveAlibi(); //erreur fonctions dés
        
        //m_crimePartner.giveTestimony(); //erreur fonctions dés
        
        //m_crimePartner.createFalseAlibi(); //débug ok mais null
        
        //m_crimePartner.createFalseTestimony(TestimonyType.SEEN); //débug ok 
    }
    
    public void testInnocent() {
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
        
        m_console.display("test 5", false).execContinue(null).clean();
        
        result = m_console.display("test 6", choices, false).execChoice();
        System.out.printf("Choix enregistré : %d\n", result);
    }
    
    
    //project
    public void testGame() {
        
    }
    
    
}
