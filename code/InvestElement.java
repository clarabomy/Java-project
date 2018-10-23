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
public class InvestElement implements NoticeClues {
    int[] refProof;

    public InvestElement(int[] refProof) {
        this.refProof = new int[refProof.length];
        System.arraycopy(refProof, 0, this.refProof, 0, refProof.length);
    }

    @Override
    public void analyse(Investigator player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
