/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;

/**
 *
 * @author ISEN
 */
public class Victim extends Character implements NoticeClues {
    String deathDate;
    String deathCause;
    int[] refProof;


    public Victim(String deathDate, String deathCause, int[] refProof, String name, boolean sex, int age) {
        super(name, sex, age);
        this.deathDate = deathDate;
        this.deathCause = deathCause;
        this.refProof = new int[refProof.length];
        System.arraycopy(refProof, 0, this.refProof, 0, refProof.length);
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getDeathCause() {
        return deathCause;
    }
    
    @Override
    public void displayInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void presentCharacter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void analyse(Investigator player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
