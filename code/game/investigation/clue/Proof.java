/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.clue;

import project.game.investigation.Victim;
import project.game.investigation.investElement.InvestElement;


/**
 *
 * @author ISEN
 */
public class Proof extends Clue {
    protected InvestElement m_element;

    
    /*$$ CONSTRUCTOR $$*/
    public Proof(InvestElement element, String content, boolean isFounded) {
        super(content, isFounded);
        this.m_element = element;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void assign() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void assign
    
    
    public void assignClue(InvestElement elt){
    }//end void assignClue
    
    
    public void assignClue(Victim corpse){
    }//end void assignClue
}
