
package javaspector.game.character.suspect;

import javaspector.game.investigation.DepositionType;

/**
 *
 * Allows a character to lie during the interrogation 
 * @author Clara BOMY
 */ 
public interface Lie {
    final int[] M_CREDIBILITY_VALID   = {60, 75, 90};//simple, medium, difficult
    final int[] M_COHERENCE_VALID     = {60, 75, 90};//same
    
    /** 
     * Generates a false deposition
     * @param category  type of the deposition
     */ 
    abstract void createFalse(DepositionType category);
}
