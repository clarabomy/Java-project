/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.investElement;

/**
 *
 * @author Thibaut
 */
public class CrimeScene extends InvestElement {
    int m_penality;

    
    public CrimeScene(int penalty, int[] refProof) {
        super(refProof);
        this.m_penality = penalty;
    }
    
}
