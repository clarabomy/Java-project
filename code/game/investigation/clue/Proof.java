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
    InvestElement m_element;

    
    public Proof(InvestElement element, String content, boolean isFounded) {
        super(content, isFounded);
        this.m_element = element;
    }
    
    
    @Override
    void assign() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    void assignClue(InvestElement elt){
    }
    
    
    void assignClue(Victim corpse){
    }
}
