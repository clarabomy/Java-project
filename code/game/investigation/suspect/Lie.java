/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.suspect;

/**
 *
 * @author Thibaut
 */
public interface Lie {
    final int[] M_CREDIBILITY_VALID   = {40, 60, 80};//simple, medium, difficult
    final int[] M_COHERENCE_VALID     = {40, 60, 80};//same
    
    
    /*$$ METHODS $$*/
    abstract void all_Lie();
    abstract void alibi_FalseLead();
    abstract void testimony_addTestimony();
}
