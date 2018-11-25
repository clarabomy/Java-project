
package javaspector.game.investigation;

import static javaspector.game.Game.getConsole;

/**
 *
 * Contains the methods and attributes of a deposition
 * @author Clara BOMY
 */ 
public class Proof extends Clue {
    protected String m_origin;// keep track of where the proof is

    /** 
     * Constructor of the class Proof
     * @param origin    origin of the proof
     * @param content   content of the proof
     */ 
    public Proof(String origin, String content) {
        super(content);
        m_origin = origin;
    }
        
    /** 
     * Getter of the origin of the proof
     * @return origin   origin of the proof
     */ 
    public String getOrigin() {
        return m_origin;
    }

    /** 
     * Displays the content of the proof
     */ 
    @Override
    public void display() {
        String proofDisplay = "   - Preuve découverte : " + getContent();
        getConsole().display(proofDisplay);
    }
}
