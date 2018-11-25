
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
    public Deposition(String depositor, String content, DepositionType type, boolean isLie) {
        super((type == DepositionType.SEEN? "Je me souviens avoir vu " : type == DepositionType.HEARD? "Je me souviens avoir entendu " : type == DepositionType.ROLE? "Je suis " : "" + "") + content);
        m_isLie = isLie;
        m_category = type;
        m_depositor = depositor;
    }

    public boolean isLie() {
        return m_isLie;
    }

    public DepositionType getCategory() {
        return m_category;
    }

    public String getDepositor() {
        return m_depositor;
    }
    
    
    
    /*$$ METHODS $$*/
    @Override
    public void display() {
        getConsole().display(m_depositor, getContent());
    }
}
