/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation;

import java.util.ArrayList;
/**
 *
 * @author Thibaut
 */
public class Victim extends Character implements NoticeClues {
    protected String m_deathDate;
    protected String m_deathCause;
    protected int[] m_refProof;


    /*$$ CONSTRUCTOR $$*/
    public Victim(String deathDate, String deathCause, int[] refProof, String name, boolean sex, int age) {
        super(name, sex, age);
        this.m_deathDate = deathDate;
        this.m_deathCause = deathCause;
        this.m_refProof = new int[refProof.length];
        System.arraycopy(refProof, 0, this.m_refProof, 0, refProof.length);
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getDeathDate() {
        return m_deathDate;
    }

    
    public String getDeathCause() {
        return m_deathCause;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void displayInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void displayInfos

    
    @Override
    public void presentCharacter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void presentCharacter

    
    @Override
    public void analyse(Investigator player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void analyse
}