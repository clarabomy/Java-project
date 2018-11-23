
package project.game.investigation;

import static project.game.Game.getConsole;




/**
 *
 * @author ISEN
 */
public class Deposition extends Clue {
    protected boolean m_isLie;
    protected DepositionType m_category;
    protected String m_depositor;//garde une trace de qui a parlé

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Deposition(String depositor, String content, DepositionType category, boolean isLie) {
        super((category == DepositionType.SEEN? "J'ai vu " : category == DepositionType.HEARD? "j'ai entendu " : category == DepositionType.ROLE? "Je suis " : "" + "") + content);
        m_isLie = isLie;
        m_category = category;
        m_depositor = depositor;
    }

    public boolean isLie() {
        return m_isLie;
    }
    
    /*$$ METHODS $$*/
    @Override
    public void display() {
        getConsole().display(m_depositor, getContent(), true);
    }
}
