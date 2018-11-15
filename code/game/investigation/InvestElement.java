
package project.game.investigation;

import project.game.character.Investigator;

/**
 *
 * @author ISEN
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
        for (int i = 0; i < this.m_refProof.length; i++) {
            player.getClue(this.m_refProof[i]).setFounded(true);
        }
    }//end void analyse

    
    public String display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end String display
}
