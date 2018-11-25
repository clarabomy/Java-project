
package javaspector.game.investigation;

import static javaspector.game.Game.getConsole;

/**
 *
 * Contains the methods and attributes of a deposition
 * @author Clara BOMY
 */ 
public class Deposition extends Clue {
    protected boolean m_isLie;
    protected DepositionType m_category;
    protected String m_depositor; //keep track of who talked

    /** 
     * Constructor of the class Deposition
     * @param depositor suspect who talked
     * @param content   content of the deposition
     * @param type      type of the deposition
     * @param isLie     if the deposition is a lie
     */ 
    public Deposition(String depositor, String content, DepositionType type, boolean isLie) {
        super((type == DepositionType.SEEN? "Je me souviens avoir vu " : type == DepositionType.HEARD? "Je me souviens avoir entendu " : type == DepositionType.ROLE? "Je suis " : "" + "") + content);
        m_isLie = isLie;
        m_category = type;
        m_depositor = depositor;
    }

    /** 
     * Returns the veracity of the deposition
     * @return isLie    true if the deposition is a lie
     */ 
    public boolean isLie() {
        return m_isLie;
    }

    /** 
     * Getter of the category of the deposition
     * @return category   type of the deposition
     */ 
    public DepositionType getCategory() {
        return m_category;
    }

    /** 
     * Getter of the depoitor
     * @return depositor    name of the depositor of the deposition
     */ 
    public String getDepositor() {
        return m_depositor;
    }
    
    /** 
     * Displays the name of the depositor and the content of the deposition
     */ 
    @Override
    public void display() {
        getConsole().display(m_depositor, getContent());
    }
}

