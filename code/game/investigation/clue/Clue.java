
package project.game.investigation.clue;

/**
 *
 * @author ISEN
 */
public abstract class Clue {
    protected String m_content;
    protected boolean m_isFounded;
    
    
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
    public abstract String display();
}
