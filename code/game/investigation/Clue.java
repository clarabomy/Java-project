
package project.game.investigation;


/**
 *
 * @author ISEN
 */
public abstract class Clue {
    protected String m_content;
    
    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Clue(String content) {
        this.m_content = content;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getContent() {
        return m_content;
    }

    
    /*$$ METHODS $$*/
    public abstract void display();//formatedText?
}
