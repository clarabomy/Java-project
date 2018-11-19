
package project.game.character.suspect;

import project.game.character.Sex;
import project.game.investigation.Deposition;
import project.game.investigation.DepositionType;

/**
 *
 * @author ISEN
 */

public class Innocent extends Suspect {
    protected int m_cooperation;

    
    /*$$ CONSTRUCTOR $$*/
    public Innocent(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, String heard, String seen, String alibi) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect);
        this.m_cooperation = cooperationLevel;
        
        //String depositor, String content, DepositionType category, boolean isLie
        m_heardTestimony = new Deposition(this.m_fullName, heard, DepositionType.HEARD, false);
        m_seenTestimony = new Deposition(this.m_fullName, seen, DepositionType.SEEN, false);
        m_alibi = new Deposition(this.m_fullName, alibi, DepositionType.ALIBI, false);
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                String text = "Je suis innocent. Concentrez-vous sur les autres suspects, plutôt que de perdre votre temps.";
                m_console.display(this.m_fullName, text, false);
                
                Deposition declaration = new Deposition(this.m_fullName, text, DepositionType.ALIBI, false);
                if (!this.m_clueList.contains(declaration)) {
                    this.m_clueList.add(declaration);
                }
                break;
            case SUCCESS:
                m_console.display(this.m_fullName, this.m_alibi.getContent(), false);
                
                if (!this.m_clueList.contains(m_alibi)) {
                    this.m_clueList.add(m_alibi);
                }
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
        String seen = "J'ai vu " + this.m_seenTestimony.getContent() + ".";
        String heard = "J'ai entendu " + this.m_heardTestimony.getContent() + ".";
        
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display(this.m_fullName, seen + heard, false);
                
                //l'inspecteur enregistre ce qu'il entend de nouveau
                if (!m_clueList.contains(m_heardTestimony)) {
                    m_clueList.add(m_heardTestimony);
                }
                if (!m_clueList.contains(m_seenTestimony)) {
                    m_clueList.add(m_seenTestimony);
                }
                break;
            case SUCCESS:
                if (Math.random() < 0.5) {//1 chance sur 2 : soit ce qu'il a vu, soit ce qu'il a entendu
                    m_console.display(this.m_fullName, heard, false);
                    if (!m_clueList.contains(m_heardTestimony)) {
                        m_clueList.add(m_heardTestimony);
                    }
                }
                else {
                    m_console.display(this.m_fullName, seen + heard, false);
                    if (!m_clueList.contains(m_seenTestimony)) {
                        m_clueList.add(m_seenTestimony);
                    }
                }
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
