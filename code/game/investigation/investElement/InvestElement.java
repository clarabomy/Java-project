/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.investElement;

import project.game.investigation.Investigator;
import project.game.investigation.NoticeClues;

/**
 *
 * @author Thibaut
 */
public class InvestElement implements NoticeClues {
    protected int[] m_refProof;

    
    /*$$ CONSTRUCTOR $$*/
    public InvestElement(int[] refProof) {
        this.m_refProof = new int[refProof.length];
        System.arraycopy(refProof, 0, this.m_refProof, 0, refProof.length);
    }

    
    /*$$ METHODS $$*/
    @Override
    public void analyse(Investigator player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void analyse

    
    public String display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end String display
}
