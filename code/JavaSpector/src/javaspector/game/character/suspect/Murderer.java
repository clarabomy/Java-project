
package javaspector.game.character.suspect;

import java.util.ArrayList;
import static javaspector.game.character.LiveCharacter.rollMultiDice;
import javaspector.game.character.Sex;
import static javaspector.game.character.suspect.Lie.M_COHERENCE_VALID;
import static javaspector.game.character.suspect.Lie.M_CREDIBILITY_VALID;
import javaspector.game.investigation.Clue;
import javaspector.game.investigation.Deposition;
import javaspector.game.investigation.DepositionType;
import static javaspector.game.investigation.Investigation.getSuspectsNameList;

/**
 *
 * Contains the methods and attributes of the murderer
 * @author Clara BOMY
 */ 
public class Murderer extends Suspect implements Lie {
    protected String m_motive;
    
    /** 
     * Constructor of the class Murderer - for new investigation
     * @param fullName              full name of the murderer
     * @param sex                   sex of the murderer
     * @param age                   age of the murderer
     * @param stressLevel           stress level of the murderer
     * @param look                  look of the murderer
     * @param physicalAspect        physical aspect of the murderer
     * @param motive                motive of the murderer
     */ 
    public Murderer(String fullName, Sex sex, int age, int stressLevel, String look, String physicalAspect, String motive) {
        super(fullName, sex, age, stressLevel, 0, look, physicalAspect, false);
        m_motive = motive;
        m_heardTestimony = null;
        m_seenTestimony = null;
        m_alibi = null;
    }
    
    /** 
     * Constructor of the class Murderer - for loaded investigation
     * @param fullName              full name of the murderer
     * @param sex                   sex of the murderer
     * @param age                   age of the murderer
     * @param stressLevel           stress level of the murderer
     * @param look                  look of the murderer
     * @param physicalAspect        physical aspect of the murderer
     * @param consideredInnocent    explications
     * @param motive                motive of the murderer
     * @param falseAlibi            false alibi of the murderer
     * @param falseHeard            false heard testimony of the murderer
     * @param falseSeen             false seen testimony of the murderer
     */ 
    public Murderer(String fullName, Sex sex, int age, int stressLevel, String look, String physicalAspect, boolean consideredInnocent, String motive, String falseAlibi, String falseHeard, String falseSeen) {
        this(fullName, sex, age, stressLevel, look, physicalAspect, motive);
        
        m_consideredInnocent = consideredInnocent;
        m_alibi = new Deposition(m_fullName, falseAlibi, DepositionType.ALIBI, true);
        m_heardTestimony = new Deposition(m_fullName, falseHeard, DepositionType.HEARD, true);
        m_seenTestimony = new Deposition(m_fullName, falseSeen, DepositionType.SEEN, true);
    }

    /** 
     * Getter of the motive
     * @return motive   motive of the murderer
     */ 
    public String getMotive() {
        return m_motive;
    }
    
    /** 
     * Determines the alibi to display based on the results of rolls of dice
     */ 
    @Override
    public void giveAlibi() {
        int[] validStage = {M_COHERENCE_VALID[m_difficulty], M_CREDIBILITY_VALID[m_difficulty]};
        //Depending on the throw of the die, the murderer acts differently
        switch (rollMultiDice(validStage, null, false)) {//result of the investigator : opponent of the murderer
            case CRITIC_FAILURE:
                textLawyer();
                break;
                
            case FAILURE:
                // create alibi if there is not
                if (m_alibi == null) {
                    createFalse(DepositionType.ALIBI);
                }
                m_alibi.display();
                
                //if the alibi is not already in the found clue list, we add it
                if (!m_clueList.contains(m_alibi)) {
                    m_clueList.add(m_alibi);
                }
                break;
                
            case SUCCESS:
                m_alibi = null;
                textForget();
                break;
            case CRITIC_SUCCESS:
                Deposition declaration = new Deposition(m_fullName, " le criminel que vous recherchez", DepositionType.ROLE, false);
                declaration.display();
                
                //if the deposition is not already in the found clue list, we add it
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
        //create testimony if there is not
        if (m_heardTestimony == null) {
            createFalse(DepositionType.HEARD);
        }
        if (m_seenTestimony == null) {
            createFalse(DepositionType.SEEN);
        }
        
        int[] validStage = {M_COHERENCE_VALID[m_difficulty], M_CREDIBILITY_VALID[m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {//result of the investigator : opponent of the murderer
            case CRITIC_FAILURE:
                m_seenTestimony.display();
                m_heardTestimony.display();
                
                // create a testimony if there is not
                if (!m_clueList.contains(m_heardTestimony)) {
                    m_clueList.add(m_heardTestimony);
                }
                if (!m_clueList.contains(m_seenTestimony)) {
                    m_clueList.add(m_seenTestimony);
                }
                break;
            case FAILURE:
                //a fifty-fifty chance: either what he saw or what he heard
                boolean tellSeen = Math.random() < 0.5;
                (tellSeen? m_seenTestimony : m_heardTestimony).display();
                
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
        //Remove the name of the murderer of the suspect list to make him coherent
        ArrayList <String> suspect = getSuspectsNameList();
        suspect.remove(m_fullName);
                
        String text;
        switch (category) {
            case SEEN:
                String[] object = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"};
                text = suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)] + " près du lieu du crime.";
                
                 //Create the false seen testimony
                m_seenTestimony = new Deposition(m_fullName, text, category, true);
                break;
            case HEARD:
                String[] sound = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
                text = sound[(int) (Math.random() * sound.length)] + " près du lieu du crime.";
                
                //Create the false heard testimony
                m_heardTestimony = new Deposition(m_fullName, text, category, true);
                break;
            case ALIBI:
                String[] activity   = {"J'ai travaillé", 
                                        "Je me suis reposé(e)", 
                                        "J'ai mangé", 
                                        "Je suis sorti(e)", 
                                        "J'étais"},
                            place   = {"au restaurant", 
                                        "à l'hotel", 
                                        "chez moi", 
                                        "chez un ami", 
                                        "dans un parc"};
                
                text = activity[(int) (Math.random() * activity.length)] + " " + place[(int) (Math.random() * place.length)];
               
                int nbSuspectsIncluded = (int) (Math.random() * 3); //between 0 and 3
                if (nbSuspectsIncluded == 0) {
                    text += ", seul";
                }
                else {
                    //Constitue a coherent sentence
                    for (int i = 0; i < nbSuspectsIncluded; i++) {
                        if (i == 0) {
                            text += " avec ";
                        }
                        else if (i != nbSuspectsIncluded - 2) {
                            text += ", ";
                        }
                        else if (i != nbSuspectsIncluded - 1) {
                            text += " et ";
                        }
                        String person = suspect.get((int) (Math.random() * suspect.size()));
                        suspect.remove(person);
                        text += person;
                    }
                }
                text += ".";
                
                m_alibi = new Deposition(m_fullName, text, category, true);
                break;
        }
    }
    
    /** 
     * Displays the confessions of the murderer
     */ 
    public void confess(){
        String part1 = "le coupable. Eh oui, c'est MOI ! AH AH AH AH AH ! ",
                part2 = "J'ai fait tout ça " + m_motive + " ! ";
        Deposition truth = new Deposition(m_fullName, part1 + part2, DepositionType.ROLE, true);
        truth.display();
    }
}
