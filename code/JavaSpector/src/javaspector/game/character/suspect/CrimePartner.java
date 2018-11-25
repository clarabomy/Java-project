
package javaspector.game.character.suspect;

import java.util.ArrayList;
import javaspector.game.character.Sex;
import javaspector.game.investigation.Clue;
import javaspector.game.investigation.Deposition;
import javaspector.game.investigation.DepositionType;
import static javaspector.game.investigation.Investigation.getSuspectsNameList;

/**
 *
 * Contains the methods and attributes of the crime partner
 * @author Clara BOMY
 */ 
public class CrimePartner extends Suspect implements Lie {
    protected Deposition m_falseAlibi;
    protected String m_murdererName;

    /** 
     * Constructor of the class CrimePartner
     * @param fullName          full name of the crime partner
     * @param sex               sex of the crime partner
     * @param age               age of the crime partner
     * @param stressLevel       stress level of the crime partner
     * @param cooperationLevel  cooperation level of the crime partner
     * @param look              look of the crime partner
     * @param physicalAspect    physical aspect of the crime partner
     * @param alibi             alibi of the crime partner
     * @param murdererName      full name of the murderer
     */ 
    public CrimePartner(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, String alibi, String murdererName) {
        super(fullName, sex, age, stressLevel, cooperationLevel, look, physicalAspect, false);
        m_murdererName = murdererName;
        m_alibi = new Deposition(m_fullName, alibi, DepositionType.ALIBI, false);
        
        m_falseAlibi = null;
        m_heardTestimony = null;
        m_seenTestimony = null;
    }
    
    /** 
     * Constructor of the class CrimePartner - for loaded investigation
     * @param fullName          full name of the crime partner
     * @param sex               sex of the crime partner
     * @param age               age of the crime partner
     * @param stressLevel       stress level of the crime partner
     * @param cooperationLevel  cooperation level of the crime partner
     * @param look              look of the crime partner
     * @param physicalAspect    physical aspect of the crime partner
     * @param consideredInnocent    if the crime partner is considered innocent by the investigator
     * @param alibi                 alibi of the crime partner
     * @param falseHeard            false heard testimony
     * @param falseSeen             false seen testimony
     */ 
    public CrimePartner(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean consideredInnocent, String alibi, String falseHeard, String falseSeen) {
        this(fullName, sex, age, stressLevel, cooperationLevel, look, physicalAspect, alibi, null);
        m_consideredInnocent = consideredInnocent;
        
        m_heardTestimony = new Deposition(m_fullName, falseHeard, DepositionType.HEARD, true);
        m_seenTestimony = new Deposition(m_fullName, falseSeen, DepositionType.SEEN, true);
    }

    /** 
     * Defines the name of the murderer
     * @param fullName  full name of the murderer
     */ 
    public void setMurdererName(String fullName) {
        m_murdererName = fullName;
    }
    
    /** 
     * Determines the alibi to display based on the results of rolls of dice
     */ 
    @Override
    public void giveAlibi() {
        int[] validStage = {M_COHERENCE_VALID[m_difficulty], M_CREDIBILITY_VALID[m_difficulty]};
        //Depending on the throw of the die, the crime partner acts differently
        switch (rollMultiDice(validStage, null, false)) {//result of the investigator : opponent of the crimePartner
            case CRITIC_FAILURE:
                textLawyer();
                break;
            case FAILURE:
                if (m_falseAlibi == null) {
                    createFalse(DepositionType.ALIBI);
                }
                m_falseAlibi.display();
                
                //if the false alibi is not already in the found clue list, we add it
                if (!m_clueList.contains(m_falseAlibi)) {
                    m_clueList.add(m_falseAlibi);
                }
                break;
            case SUCCESS:
                m_alibi.display();
                //if the alibi is not already in the found clue list, we add it
                if (!m_clueList.contains(m_alibi)) {
                    m_clueList.add(m_alibi);
                }
                break;
            case CRITIC_SUCCESS:
                Deposition declaration = new Deposition(m_fullName, " complice de ce crime", DepositionType.ROLE, false);
                declaration.display();
                
                if (!m_clueList.contains(declaration)) {
                    m_clueList.add(declaration);
                }
                break;
        }
    }
    
    /** 
     * Determines the testimony to display based on the results of rolls of dice
     */ 
    @Override
    public void giveTestimony() {
        // create a testimony if there is not
        if (m_heardTestimony == null) {
            createFalse(DepositionType.HEARD);
        }
        if (m_seenTestimony == null) {
            createFalse(DepositionType.SEEN);
        }
        
        int[] validStage = {M_COHERENCE_VALID[m_difficulty], M_CREDIBILITY_VALID[m_difficulty]};
        
        //Depending on the throw of the die, the crime partner acts differently
        switch (rollMultiDice(validStage, null, false)) {//result of the investigator : opponent of the crimePartner
            case CRITIC_FAILURE:
                m_seenTestimony.display();
                m_heardTestimony.display();
                
                // the inspector records what he hears again
                if (!m_clueList.contains(m_heardTestimony)) {
                    m_clueList.add(m_heardTestimony);
                }
                
                //if the testimony is not already in the found clue list, we add it
                if (!m_clueList.contains(m_seenTestimony)) {
                    m_clueList.add(m_seenTestimony);
                }
                break;
            case FAILURE:
                //a fifty-fifty chance: either what he saw or what he heard
                boolean tellSeen = Math.random() < 0.5;
                (tellSeen? m_seenTestimony : m_heardTestimony).display();
                
                //if the testimony is not already in the found clue list, we add it
                if (!m_clueList.contains((Clue)(tellSeen? m_seenTestimony : m_heardTestimony))) {
                    m_clueList.add((tellSeen? m_seenTestimony : m_heardTestimony));
                }
                break;
            case SUCCESS:
                textNoSpeak();
                break;
            case CRITIC_SUCCESS:
                m_heardTestimony = null;
                m_seenTestimony = null;
                
                textForget();
                break;
        }
    }
    
    /** 
     * Generates a false deposition
     * @param category  type of the deposition
     */ 
    @Override
    public void createFalse(DepositionType category) {
        String text;
        switch (category) {
            case SEEN:
                String[] object = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"};
                
                //Remove the name of the crime partner and of the murderer of the suspect list to make him coherent
                ArrayList <String> suspect = getSuspectsNameList();
                suspect.remove(m_fullName);
                suspect.remove(m_murdererName);
                
                text = suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)] + " près du lieu du crime.";
                
                //Create the false seen testimony
                m_seenTestimony = new Deposition(m_fullName, text, DepositionType.SEEN, true);
                break;
                
            case HEARD:
                String[] sound = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
                
                text = sound[(int) (Math.random() * sound.length)] + " près du lieu du crime.";
                //Create the false heard testimony
                m_heardTestimony = new Deposition(m_fullName, text, DepositionType.HEARD, true);
                break;
                
            case ALIBI:
                text = m_alibi.getContent() + " Il y avait aussi " + m_murdererName + " avec moi.";
                
                //Create the false alibi
                m_falseAlibi = new Deposition(m_fullName, text, DepositionType.ALIBI, true);
                break;
        }
    }
}

    

