
package javaspector.game.character.suspect;

import javaspector.game.character.Sex;
import javaspector.game.investigation.Clue;
import javaspector.game.investigation.Deposition;
import javaspector.game.investigation.DepositionType;

/**
 *
 * Contains the methods and attributes of the innocent
 * @author Clara BOMY
 */ 
public class Innocent extends Suspect {
    /** 
     * Constructor of the class Innocent
     * @param fullName              full name of the innocent
     * @param sex                   sex of the innocent
     * @param age                   age of the innocent
     * @param stressLevel           stress level of the innocent
     * @param cooperationLevel      cooperation of the innocent
     * @param look                  look of the innocent
     * @param physicalAspect        physical aspect of the innocent
     * @param consideredInnocent    if the innocent is considered innocent by the investigator
     * @param alibi                 alibi of the innocent
     * @param heard                 heard testimony
     * @param seen                  seen testimony
     */ 
    public Innocent(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean consideredInnocent, String alibi, String heard, String seen) {
        super(fullName, sex, age, stressLevel, cooperationLevel, look, physicalAspect, consideredInnocent);
        
        m_heardTestimony = new Deposition(m_fullName, heard, DepositionType.HEARD, false);
        m_seenTestimony = new Deposition(m_fullName, seen, DepositionType.SEEN, false);
        m_alibi = new Deposition(m_fullName, alibi, DepositionType.ALIBI, false);
    }
    
    /** 
     * Determines the alibi to display based on the results of rolls of dice
     */ 
    @Override
    public void giveAlibi() {
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                Deposition declaration = new Deposition(m_fullName, "innocent dans cette affaire", DepositionType.ROLE, false);
                declaration.display();
                
                if (!m_clueList.contains(declaration)) {
                    m_clueList.add(declaration);
                }
                break;
            case SUCCESS:
                m_alibi.display();
                
                if (!m_clueList.contains(m_alibi)) {
                    m_clueList.add(m_alibi);
                }
                break;
            case FAILURE:
                textNoSpeak();
                break;
            case CRITIC_FAILURE:
                textLawyer();
                break;
        }
    }

    /** 
     * Determines the testimony to display based on the results of rolls of dice
     */ 
    @Override
    public void giveTestimony() {
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_seenTestimony.display();
                m_heardTestimony.display();
                
                // the inspector records what he hears again
                if (!m_clueList.contains(m_heardTestimony)) {
                    m_clueList.add(m_heardTestimony);
                }
                if (!m_clueList.contains(m_seenTestimony)) {
                    m_clueList.add(m_seenTestimony);
                }
                break;
            case SUCCESS:
                //a fifty-fifty chance: either what he saw or what he heard
                boolean tellSeen = Math.random() < 0.5;
                (tellSeen? m_seenTestimony : m_heardTestimony).display();
                
                if (!m_clueList.contains((Clue)(tellSeen? m_seenTestimony : m_heardTestimony))) {
                    m_clueList.add((tellSeen? m_seenTestimony : m_heardTestimony));
                }
                break;
            case FAILURE:
                textNoSpeak();
                break;
            case CRITIC_FAILURE:
                textForget(); // but keep testimony in memory
                break;
        }
    }
}
