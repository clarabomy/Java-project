
package javaspector.game.character;

import java.util.ArrayList;
import javaspector.game.Difficulty;
import static javaspector.game.Game.getConsole;
import static javaspector.game.Game.getLevelChoice;
import javaspector.game.investigation.Clue;

/**
 *
 * Allows live characters to roll dice
 * @author Clara BOMY
 */ 
public abstract class LiveCharacter extends Character {
    private static final int M_SIDES = 100;
    private static final int M_CRITICAL_SUCCESS = 15;
    private static final int M_CRITICAL_FAILURE = M_SIDES - M_CRITICAL_SUCCESS;
    private static final int M_ZONE_VALUE = 5; 
    private static float m_coeffDiff;
    private static int m_lastDiceValue; // value from the previous die
    private static int m_lastDiceValidStage; // validation stage of the previous throw
    
    protected static ArrayList <Clue> m_clueList = new ArrayList();

    /** 
     * Constructor of the class LiveCharacter
     * @param fullName  full name of the live character
     * @param sex       sex of the live character
     * @param age       age of the live character
     */ 
    public LiveCharacter(String fullName, Sex sex, int age) {
        super(fullName, sex, age);
        
        // simple: 0.75 | medium: 1 | difficult: 1.25
        m_coeffDiff = (float) (getLevelChoice() == Difficulty.SIMPLE? 0.75 : (getLevelChoice() == Difficulty.MEDIUM? 1 : 1.25));
    }
    
    /** 
     * Getter of the clue already found
     * @param index : index of the clue to get 
     * @return clue : clue found
     */ 
    public Clue getClue(int index) {
        return m_clueList.get(index);
    }

    /** 
     * Getter of the ArrayList of clues already found
     * @return clueList : list of clues already found
     */ 
    public ArrayList <Clue> getClueList() {
        return m_clueList;
    }
    
    /** 
     * Setter of the new clue found
     * @param newClue  : clue just found
     */ 
    public void setClue(Clue newClue) {
        m_clueList.add(newClue);
    }
    
    /** 
     * Empties the list of clues found
     */ 
    public static void dropClueList() {
        m_clueList = new ArrayList();
    }
    
    /** 
     * Displays statistics of a live character
     * @return liveCharacter    sends the reference to chain the calls
     */ 
    public abstract LiveCharacter displayStats();
    
    /** 
     * Rolls a die
     * @param validStage    validation stage of the throw
     * @param display       text to display - no display if null
     * @param newThrow      determines if is influenced by previous throws
     * @return diceResult   result of the throw
     */ 
    public static DiceResult rollDice(int validStage, String display, boolean newThrow) {
        // roll a die
        int diceValue = (int) (Math.random() * M_SIDES) + 1; // gives value between 1 and 100

        
        // if this throw is dependent on the previous one, adjust the validity interval
        if (!newThrow) {
            // if critical failure at the previous throw
            if (m_lastDiceValue >= M_CRITICAL_FAILURE) {
                diceValue = 100;// miss other throws
            }
            // if critical success at the previous throw
            else if (m_lastDiceValue <= M_CRITICAL_SUCCESS) {
                validStage += 10; // application of the bonus due to the previous success
            }
            // if simple failure at the previous throw
            else if (m_lastDiceValue > m_lastDiceValidStage && m_lastDiceValue < M_CRITICAL_FAILURE) {
                int zone = 0;
                do zone++; while (m_lastDiceValue > m_lastDiceValidStage + zone * ((M_SIDES - m_lastDiceValidStage) / 4));
                validStage -= (int)(M_ZONE_VALUE * zone * m_coeffDiff); // application of the malus due to the previous failure
            }
        }
        
        
        //update attributes
        m_lastDiceValue = diceValue;
        m_lastDiceValidStage = validStage;
    
        
        //determine the score
        DiceResult result;
        
        //more frequent
        if (diceValue <= validStage && diceValue > M_CRITICAL_SUCCESS) result = DiceResult.SUCCESS;
        else if (diceValue > validStage && diceValue < M_CRITICAL_FAILURE) result = DiceResult.FAILURE;
        
        //less frequent
        else if (diceValue <= M_CRITICAL_SUCCESS) result = DiceResult.CRITIC_SUCCESS;
        else /*if (diceValue >= M_CRITICAL_FAILURE)*/ {
            result = DiceResult.CRITIC_FAILURE;
        }
        
        
        //display information if wanted
        if (display != null) {
            getConsole().display("Jet " + display + " : " + diceValue + " / " + validStage + "\t" + result.toString());
        }
        
        
        return result;
    }
   
    /** 
     * Performs a throwing suite
     * @param validStage    tab of validation stages of the throw
     * @param display       tab of texts to display - no display if null
     * @param newThrow      determines if is influenced by previous throws
     * @return diceResult   result of the throwing suite
     */ 
    public static DiceResult rollMultiDice(int[] validStage, String[] display, boolean newThrow) {
        DiceResult action = DiceResult.ERROR;
        for (int i = 0; i < validStage.length; i++) {
            // is there any text to display for this throw ?
            String text = (display != null && i < display.length)? display[i] : null;
            action = rollDice(validStage[i], text, newThrow);
            newThrow = false; // only the first throw is a new suite
        }
        return action;
    }
}
