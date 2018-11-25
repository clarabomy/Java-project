
package javaspector.game.character.suspect;

import javaspector.game.investigation.DepositionType;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public interface Lie {
    final int[] M_CREDIBILITY_VALID   = {60, 75, 90};//simple, medium, difficult
    final int[] M_COHERENCE_VALID     = {60, 75, 90};//same
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param category  explications
     */ 
    abstract void createFalse(DepositionType category);
}
