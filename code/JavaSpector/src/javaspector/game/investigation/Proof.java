
package javaspector.game.investigation;

import static javaspector.game.Game.getConsole;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public class Proof extends Clue {
    protected String m_origin;//garde une trace de là ou est trouvé l'indice

    /** 
     * Constructor of the class
     * @param origin    explications
     * @param content   explications
     */ 
    public Proof(String origin, String content) {
        super(content);
        m_origin = origin;
    }
        
    /** 
     * Getter of the class
     * @return origin   explications
     */ 
    public String getOrigin() {
        return m_origin;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    @Override
    public void display() {
        String proofDisplay = "   - Preuve découverte : " + getContent();
        getConsole().display(proofDisplay);
    }
}
