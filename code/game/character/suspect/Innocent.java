
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.character.Sex;
import project.game.investigation.Clue;

/**
 *
 * @author ISEN
 */

public class Innocent extends Suspect {
    protected String m_alibi;
    protected int m_cooperation;

    
    /*$$ CONSTRUCTOR $$*/
    public Innocent(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef, clueList);
        this.m_alibi = alibi;
        this.m_cooperation = cooperationLevel;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {   
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display(this.getFullName(), "Je suis innocent. Concentrez-vous sur les autres suspects, plutôt que de perdre votre temps.", false);
                break;
            case SUCCESS:
                m_console.display(this.getFullName(), this.m_alibi, false);
                break;
            case FAILURE:
                this.textNoSpeak();
                break;
            case CRITIC_FAILURE:
                this.textLawyer();
                break;
        }
        m_console.execContinue();
    }

    @Override
    public void giveTestimony() {
        String seen = "J'ai vu " + this.m_clueList.get(this.m_testimonyRef[0]).getContent() + ".";
        String heard = "J'ai entendu" + this.m_clueList.get(this.m_testimonyRef[1]).getContent() + ".";
        
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display(this.getFullName(), seen + heard, false);
                break;
            case SUCCESS:
                m_console.display(this.getFullName(), (Math.random() < 0.5)? seen : heard, false);//soit ce qu'il a vu, soit ce qu'il a entendu
                break;
            case FAILURE:
                this.textNoSpeak();
                break;
            case CRITIC_FAILURE:
                this.textForget();//mais garde temoignage en mémoire
                break;
        }
        m_console.execContinue();
    }
}
