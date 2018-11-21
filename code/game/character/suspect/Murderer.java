
package project.game.character.suspect;

import java.util.ArrayList;
import static project.game.Game.getConsole;
import static project.game.character.LiveCharacter.rollMultiDice;
import project.game.character.Sex;
import static project.game.character.suspect.Lie.M_COHERENCE_VALID;
import static project.game.character.suspect.Lie.M_CREDIBILITY_VALID;
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
    public Murderer(String name, String surname, Sex sex, int age, int stressLevel, String look, String physicalAspect, String motive) {
        super(name, surname, sex, age, stressLevel, 0, look, physicalAspect);
        this.m_motive = motive;
        
        m_heardTestimony = null;
        m_seenTestimony = null;
        m_alibi = null;
    }
    //chargement partie
    public Murderer(String name, String surname, Sex sex, int age, int stressLevel, String look, String physicalAspect, String motive, String falseAlibi, String falseHeard, String falseSeen) {
        super(name, surname, sex, age, stressLevel, 0, look, physicalAspect);
        this.m_motive = motive;
        
        m_alibi = new Deposition(this.m_fullName, falseAlibi, DepositionType.ALIBI, true);
        m_heardTestimony = new Deposition(this.m_fullName, falseHeard, DepositionType.HEARD, true);
        m_seenTestimony = new Deposition(this.m_fullName, falseSeen, DepositionType.SEEN, true);
    }

    public String getMotive() {
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                this.textLawyer();
                break;
            case SUCCESS:
                //crée alibi s'il n'en a pas
                if (this.m_alibi == null) {
                    this.createFalse(DepositionType.ALIBI);
                }
                getConsole().display(this.m_fullName, this.m_alibi.getContent(), false);
                
                if (!this.m_clueList.contains(m_alibi)) {
                    this.m_clueList.add(m_alibi);
                }
                break;
            case FAILURE:
                this.m_alibi = null;
                this.textForget();
                break;
            case CRITIC_FAILURE:
                String part1 = "Vous voulez savoir ce que je faisais, ce " + "soir" + "-là? Vraiment? Bien, je vais vous le dire : ",
                       part2 = "j'étais occupé à assassiner " + "nameVictim" + " !";
                getConsole().display(this.m_fullName, part1 + part2, false);
                
                Deposition declaration = new Deposition(this.m_fullName, part1 + part2, DepositionType.ALIBI, false);
                if (!this.m_clueList.contains(declaration)) {
                    this.m_clueList.add(declaration);
                }
                break;
        }
        getConsole().execContinue();
    }

    @Override
    public void giveTestimony() {
        //crée temoignage si n'en a pas
        if (this.m_heardTestimony == null) {
            this.createFalse(DepositionType.HEARD);
        }
        if (this.m_seenTestimony == null) {
            this.createFalse(DepositionType.SEEN);
        }
        
        //affiche ce qu'il reussit a faire
        String seen = "J'ai vu " + this.m_seenTestimony.getContent() + ".";
        String heard = "J'ai entendu " + this.m_heardTestimony.getContent() + ".";
        
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                getConsole().display(this.m_fullName, seen + heard, false);
                
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
                    getConsole().display(this.m_fullName, heard, false);
                    if (!m_clueList.contains(m_heardTestimony)) {
                        m_clueList.add(m_heardTestimony);
                    }
                }
                else {
                    getConsole().display(this.m_fullName, seen + heard, false);
                    if (!m_clueList.contains(m_seenTestimony)) {
                        m_clueList.add(m_seenTestimony);
                    }
                }
                break;
            case FAILURE:
                this.textNoSpeak();
                break;
            case CRITIC_FAILURE:
                m_heardTestimony = null;
                m_seenTestimony = null;
                
                this.textForget();
                break;
        }
        getConsole().execContinue();
    }
    
    @Override
    public void createFalse(DepositionType category) {//crée témoigage bidon avec aléatoire
        ArrayList <String> suspect = suspectsNameList();
        suspect.remove(this.m_fullName);
                
        String text;
        switch (category) {
            case SEEN:
                String[] object = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"};
                text = "J'ai vu " + suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)] + " près du lieu du crime.";
                
                m_seenTestimony = new Deposition(this.m_fullName, text, DepositionType.SEEN, true);
                break;
            case HEARD:
                String[] sound = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
                text = "J'ai entendu " + sound[(int) (Math.random() * sound.length)] + " près du lieu du crime.";
                
                m_heardTestimony = new Deposition(this.m_fullName, text, DepositionType.HEARD, true);
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
                
                m_alibi = new Deposition(this.m_fullName, text, DepositionType.ALIBI, true);
                break;
        }
    }
    
    public void confess(){
        getConsole().display(m_fullName, "C'est bon, je vais tout vous avouer...", false).execContinue();
        getConsole().display(m_fullName, "C'est moi le coupable ! AH AH AH AH AH !", false).execContinue();
        
        String text = "J'ai fait tout ça pour " + m_motive + ". Et vous, qu'auriez-vous fait à ma place?";
        getConsole().display(m_fullName, text, false).execContinue();
    }
}
