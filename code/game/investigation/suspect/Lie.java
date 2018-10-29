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
    final int M_CREDIBILITY_MIN_SIMPLE = 40;
    final int M_COHERENCE_MIN_SIMPLE = 40;
    
    final int M_CREDIBILITY_MIN_MEDIUM = 60;
    final int M_COHERENCE_MIN_MEDIUM = 60;
    
    final int M_CREDIBILITY_MIN_DIFFICULT = 80;
    final int M_COHERENCE_MIN_DIFFICULT = 80;
    
    abstract void all_Lie();
    abstract void alibi_FalseLead();
    abstract void testimony_addTestimony();

}
