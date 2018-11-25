
package javaspector.game.investigation;

import static javaspector.game.Game.getConsole;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public class Deposition extends Clue {
    protected boolean m_isLie;
    protected DepositionType m_category;
    protected String m_depositor;//garde une trace de qui a parlé

    /** 
     * Constructor of the class
     * @param depositor explications
     * @param content   explications
     * @param type      explications
     * @param isLie     explications
     */ 
    public Deposition(String depositor, String content, DepositionType type, boolean isLie) {
        super((type == DepositionType.SEEN? "Je me souviens avoir vu " : type == DepositionType.HEARD? "Je me souviens avoir entendu " : type == DepositionType.ROLE? "Je suis " : "" + "") + content);
        m_isLie = isLie;
        m_category = type;
        m_depositor = depositor;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     * @return isLie    explications
     */ 
    public boolean isLie() {
        return m_isLie;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     * @return category explications
     */ 
    public DepositionType getCategory() {
        return m_category;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     * @return depositor    explications
     */ 
    public String getDepositor() {
        return m_depositor;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    @Override
    public void display() {
        getConsole().display(m_depositor, getContent());
    }
}

