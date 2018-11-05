
package project.game.investigation.suspect;

/**
 *
 * @author ISEN
 */
public interface Lie {
    final int[] M_CREDIBILITY_VALID   = {60, 75, 90};//simple, medium, difficult
    final int[] M_COHERENCE_VALID     = {60, 75, 90};//same
    
    
    /*$$ METHODS $$*/
    abstract void contradiction();
    abstract void createFalseLead();
    abstract void addTestimony();
}
