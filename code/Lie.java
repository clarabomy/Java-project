/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ISEN
 */
public interface Lie {
    final int CREDIBILITY_MIN_SIMPLE = 40;
    final int COHERENCE_MIN_SIMPLE = 40;
    
    final int CREDIBILITY_MIN_MEDIUM = 60;
    final int COHERENCE_MIN_MEDIUM = 60;
    
    final int CREDIBILITY_MIN_DIFFICULT = 80;
    final int COHERENCE_MIN_DIFFICULT = 80;
    
    abstract boolean correctLie();
    abstract void createFalseLead();
    abstract void addTestimony();
    
}
