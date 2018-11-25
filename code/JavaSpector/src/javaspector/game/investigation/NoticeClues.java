
package javaspector.game.investigation;

import javaspector.game.character.Investigator;

/**
 *
 * Allows an element to be analysed
 * @author Clara BOMY
 */ 
public interface NoticeClues {
    /** 
     * Get the proofs present on the investigation element
     * @param player    investigator
     */ 
    abstract void analyse(Investigator player);
}
