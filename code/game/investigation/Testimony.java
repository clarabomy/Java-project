
package project.game.investigation;

import project.game.character.suspect.Suspect;



/**
 *
 * @author ISEN
 */
public class Testimony extends Clue {
    protected Suspect m_suspect;
    protected boolean m_isLie;

    
    /*$$ CONSTRUCTOR $$*/
    public Testimony(Suspect suspect, boolean isLie, String content, boolean isFounded) {
        super(content, isFounded);
        this.m_suspect = suspect;
        this.m_isLie = isLie;
    }

    
    /*$ GETTERS & SETTERS $*/
    public Suspect getSuspect() {    
        return m_suspect;
    }

    
    /*$$ METHODS $$*/
    public void assignTestimony(Suspect suspect, TestimonyType type) {
       //assigne le témoignage à un suspect
        suspect.setTestimonyRef(this.m_indexInClues,type);
    } //end void assign

    
    @Override
    public void display() {
        //Témoignage de nom_personnage : ce qu'il a dit
        m_console.display(this.getSuspect().getFullName(), this.getContent(), false).execContinue();
    }
}
