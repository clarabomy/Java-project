
package project.game.character.suspect;

import project.game.investigation.DepositionType;

/**
 *
 * @author ISEN
 */
public interface Lie {
    final int[] M_CREDIBILITY_VALID   = {60, 75, 90};//simple, medium, difficult =>remettre sur 3 lignes
    final int[] M_COHERENCE_VALID     = {60, 75, 90};//same
    
    
    /*$$ METHODS $$*/
    abstract void createFalse(DepositionType category);
}
