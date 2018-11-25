
package javaspector.game.character.suspect;

import javaspector.game.character.Sex;
import javaspector.game.investigation.Clue;
import javaspector.game.investigation.Deposition;
import javaspector.game.investigation.DepositionType;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public class Innocent extends Suspect {
    /** 
     * Constructor of the class
     * @param fullName              explications
     * @param sex                   explications
     * @param age                   explications
     * @param stressLevel           explications
     * @param cooperationLevel      explications
     * @param look                  explications
     * @param physicalAspect        explications
     * @param consideredInnocent    explications
     * @param alibi                 explications
     * @param heard                 explications
     * @param seen                  explications
     */ 
    public Innocent(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean consideredInnocent, String alibi, String heard, String seen) {
        super(fullName, sex, age, stressLevel, cooperationLevel, look, physicalAspect, consideredInnocent);
        
        m_heardTestimony = new Deposition(m_fullName, heard, DepositionType.HEARD, false);
        m_seenTestimony = new Deposition(m_fullName, seen, DepositionType.SEEN, false);
        m_alibi = new Deposition(m_fullName, alibi, DepositionType.ALIBI, false);
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    @Override
    public void giveAlibi() {
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                //String text = "Je suis innocent. Concentrez-vous sur les autres suspects, plutôt que de perdre votre temps avec moi.";
                Deposition declaration = new Deposition(m_fullName, "innocent dans cette affaire", DepositionType.ROLE, false);
                declaration.display();
                
                if (!m_clueList.contains(declaration)) {
                    m_clueList.add(declaration);
                }
                break;
            case SUCCESS:
                m_alibi.display();
                
                if (!m_clueList.contains(m_alibi)) {
                    m_clueList.add(m_alibi);
                }
                break;
            case FAILURE:
                textNoSpeak();
                break;
            case CRITIC_FAILURE:
                textLawyer();
                break;
        }
    }

    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    @Override
    public void giveTestimony() {
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_seenTestimony.display();
                m_heardTestimony.display();
                
                //l'inspecteur enregistre ce qu'il entend de nouveau
                if (!m_clueList.contains(m_heardTestimony)) {
                    m_clueList.add(m_heardTestimony);
                }
                if (!m_clueList.contains(m_seenTestimony)) {
                    m_clueList.add(m_seenTestimony);
                }
                break;
            case SUCCESS:
                boolean tellSeen = Math.random() < 0.5;//1 chance sur 2 : soit ce qu'il a vu, soit ce qu'il a entendu
                (tellSeen? m_seenTestimony : m_heardTestimony).display();
                
                if (!m_clueList.contains((Clue)(tellSeen? m_seenTestimony : m_heardTestimony))) {
                    m_clueList.add((tellSeen? m_seenTestimony : m_heardTestimony));
                }
                break;
            case FAILURE:
                textNoSpeak();
                break;
            case CRITIC_FAILURE:
                textForget();//mais garde temoignage en mémoire
                break;
        }
    }
}
