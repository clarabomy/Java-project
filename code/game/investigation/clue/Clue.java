/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.clue;

/**
 *
 * @author ISEN
 */
public abstract class Clue {
    String m_content;
    public boolean m_isFounded;

    public Clue(String content, boolean isFounded) {
        this.m_content = content;
        this.m_isFounded = isFounded;
    }
    
    
    public void BeFounded(){
    }
    
    abstract void assign();
    
    
    public String display() {
        return "tmp";
    }
}
