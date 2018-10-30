/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.clue;

import project.game.investigation.suspect.Suspect;


/**
 *
 * @author ISEN
 */
public class Testimony extends Clue {
    protected Suspect m_suspect;
    protected boolean m_isLie;

    public Testimony(Suspect suspect, boolean isLie, String content, boolean isFounded) {
        super(content, isFounded);
        this.m_suspect = suspect;
        this.m_isLie = isLie;
    }

    @Override
    void assign() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
