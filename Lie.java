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
    final int CREDIBILITY_MIN = 60;
    final int COHERENCE_MIN = 60;
    
    abstract boolean beIncoherent();
    abstract void createFalseLead();
    abstract void addTestimony();
    
}
