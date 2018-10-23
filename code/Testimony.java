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
public class Testimony extends Clue {
    Suspect suspect;
    boolean isLie;

    public Testimony(Suspect suspect, boolean isLie, String content, boolean isFounded) {
        super(content, isFounded);
        this.suspect = suspect;
        this.isLie = isLie;
    }

    @Override
    void assign() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
