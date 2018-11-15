
package project.game.investigation;

import project.game.character.Victim;


/**
 *
 * @author ISEN
 */
public class Proof extends Clue {
    protected InvestElement m_element;

    
    /*$$ CONSTRUCTOR $$*/
    public Proof(InvestElement element, String content, boolean isFounded) {
        super(content, isFounded);
        this.m_element = element;
    }
    
    
    /*$$ METHODS $$*/
    
    public void assignVictim(Victim corpse) {
        //assigne la preuve à la victime
    }
    
    
    public void assignElement(InvestElement elt){
        //assigne la preuve à l'élément d'enquête (arme du crime, scène de crime)
    }

    
    @Override
    public void display() {
        //affiche ce que contient la preuve et d'où elle vient (sang trouvé sur arme, cheveux trouvés sur scène de crime, etc.)
        String proofDisplay = "Ohh, je viens de trouver une preuve (" + this.getContent() + ").";
        m_console.display("Enquêteur", proofDisplay, false).execContinue();
    }
}
