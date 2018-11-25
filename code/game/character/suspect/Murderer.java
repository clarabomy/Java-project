
package project.game.character.suspect;

import java.util.ArrayList;
import static project.game.character.LiveCharacter.rollMultiDice;
import project.game.character.Sex;
import static project.game.character.suspect.Lie.M_COHERENCE_VALID;
import static project.game.character.suspect.Lie.M_CREDIBILITY_VALID;
import project.game.investigation.Clue;
import static project.game.investigation.Investigation.suspectsNameList;
import project.game.investigation.Deposition;
import project.game.investigation.DepositionType;

/**
 *
 * @author ISEN
 */
public class Murderer extends Suspect implements Lie {
    protected String m_motive;
    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie
    public Murderer(String fullName, Sex sex, int age, int stressLevel, String look, String physicalAspect, String motive) {
        super(fullName, sex, age, stressLevel, 0, look, physicalAspect, false);
        m_motive = motive;
        
        m_heardTestimony = null;
        m_seenTestimony = null;
        m_alibi = null;
    }
    //chargement partie
    public Murderer(String fullName, Sex sex, int age, int stressLevel, String look, String physicalAspect, boolean consideredInnocent, String motive, String falseAlibi, String falseHeard, String falseSeen) {
        super(fullName, sex, age, stressLevel, 0, look, physicalAspect, consideredInnocent);
        m_motive = motive;
        
        m_alibi = new Deposition(m_fullName, falseAlibi, DepositionType.ALIBI, true);
        m_heardTestimony = new Deposition(m_fullName, falseHeard, DepositionType.HEARD, true);
        m_seenTestimony = new Deposition(m_fullName, falseSeen, DepositionType.SEEN, true);
    }

    public String getMotive() {
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        int[] validStage = {M_COHERENCE_VALID[m_difficulty], M_CREDIBILITY_VALID[m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                textLawyer();
                break;
            case SUCCESS:
                //crée alibi s'il n'en a pas
                if (m_alibi == null) {
                    createFalse(DepositionType.ALIBI);
                }
                m_alibi.display();
                
                if (!m_clueList.contains(m_alibi)) {
                    m_clueList.add(m_alibi);
                }
                break;
            case FAILURE:
                m_alibi = null;
                textForget();
                break;
            case CRITIC_FAILURE:
                //String part1 = "Vous voulez savoir ce que je faisais, ce " + "soir" + "-là? Vraiment? Bien, je vais vous le dire : ",
                //       part2 = "j'étais occupé à assassiner " + "nameVictim" + " !";
                Deposition declaration = new Deposition(m_fullName, " le criminel que vous recherchez", DepositionType.ROLE, false);
                declaration.display();
                
                if (!m_clueList.contains(declaration)) {
                    m_clueList.add(declaration);
                }
                break;
        }
    }

    @Override
    public void giveTestimony() {
        //crée temoignage si n'en a pas
        if (m_heardTestimony == null) {
            createFalse(DepositionType.HEARD);
        }
        if (m_seenTestimony == null) {
            createFalse(DepositionType.SEEN);
        }
        
        int[] validStage = {M_COHERENCE_VALID[m_difficulty], M_CREDIBILITY_VALID[m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
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
                m_heardTestimony = null;
                m_seenTestimony = null;
                
                textForget();
                break;
        }
    }
    
    @Override
    public void createFalse(DepositionType category) {//crée témoigage bidon avec aléatoire
        ArrayList <String> suspect = suspectsNameList();
        suspect.remove(m_fullName);
                
        String text;
        switch (category) {
            case SEEN:
                String[] object = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"};
                text = suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)] + " près du lieu du crime.";
                
                m_seenTestimony = new Deposition(m_fullName, text, category, true);
                break;
            case HEARD:
                String[] sound = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
                text = sound[(int) (Math.random() * sound.length)] + " près du lieu du crime.";
                
                m_heardTestimony = new Deposition(m_fullName, text, category, true);
                break;
            case ALIBI:
                String[] activity   = {"J'ai travaillé", 
                                        "Je me suis reposé(e)", 
                                        "J'ai mangé", 
                                        "Je suis sorti(e)", 
                                        "J'étais"},
                            place   = {"au restaurant", 
                                        "à l'hotel", 
                                        "chez moi", 
                                        "chez un ami", 
                                        "dans un parc"};
                
                text = activity[(int) (Math.random() * activity.length)] + " " + place[(int) (Math.random() * place.length)];
               
                int nbSuspectsIncluded = (int) (Math.random() * 3); //entre 0 et 3
                if (nbSuspectsIncluded == 0) {
                    text += ", seul";
                }
                else {
                    for (int i = 0; i < nbSuspectsIncluded; i++) {
                        if (i == 0) {
                            text += " avec ";
                        }
                        else if (i != nbSuspectsIncluded - 2) {
                            text += ", ";
                        }
                        else if (i != nbSuspectsIncluded - 1) {
                            text += " et ";
                        }
                        String person = suspect.get((int) (Math.random() * suspect.size()));
                        suspect.remove(person);
                        text += person;
                    }
                }
                text += ".";
                
                m_alibi = new Deposition(m_fullName, text, category, true);
                break;
        }
    }
    
    public void confess(){
        String part1 = "le coupable. Eh oui, c'est MOI ! AH AH AH AH AH ! ",
                part2 = "J'ai fait tout ça " + m_motive + " ! ";
        Deposition truth = new Deposition(m_fullName, part1 + part2, DepositionType.ROLE, true);
        truth.display();
    }
}
