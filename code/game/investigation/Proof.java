
package project.game.investigation;

import static project.game.Game.getConsole;


/**
 *
 * @author ISEN
 */
public class Proof extends Clue {
    protected String m_origin;//garde une trace de là ou est trouvé l'indice

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Proof(String origin, String content) {
        super(content);
        m_origin = origin;
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public String getOrigin() {
        return m_origin;
    }

    
    
    /*$$ METHODS $$*/
    @Override
    public void display() {
        //affiche ce que contient la preuve et d'où elle vient (sang trouvé sur arme, cheveux trouvés sur scène de crime, etc.)
        String proofDisplay = "Ohh, je viens de trouver une preuve (" + this.getContent() + ").";
        getConsole().display("Enquêteur", proofDisplay, false).execContinue();
    }
}
