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
    protected String m_content;
    protected boolean m_isFounded;//faire un getter et ou un setter

    
    /*$$ CONSTRUCTOR $$*/
    public Clue(String content, boolean isFounded) {
        this.m_content = content;
        this.m_isFounded = isFounded;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public boolean isFounded() {
        return m_isFounded;
    }

    
    public void setFounded(boolean isFounded) {
        this.m_isFounded = isFounded;
    }
    
    
    /*$$ METHODS $$*/
    abstract void assign();

    public String display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end String display
}
