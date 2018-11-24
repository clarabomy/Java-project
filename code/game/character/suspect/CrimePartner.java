/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.character.Sex;
import project.game.investigation.Clue;
import static project.game.investigation.Investigation.suspectsNameList;
import project.game.investigation.Deposition;
import project.game.investigation.DepositionType;

/**
 *
 * @author ISEN
 */
public class CrimePartner extends Suspect implements Lie {
    protected Deposition m_falseAlibi;
    protected String m_murdererName;

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie
    public CrimePartner(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, String alibi, String murdererName) {
        super(fullName, sex, age, stressLevel, cooperationLevel, look, physicalAspect, false);
        this.m_murdererName = murdererName;
        m_alibi = new Deposition(this.m_fullName, alibi, DepositionType.ALIBI, false);
        
        m_falseAlibi = null;
        m_heardTestimony = null;
        m_seenTestimony = null;
    }
    //chargement partie
    public CrimePartner(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean consideredInnocent, String alibi, String murdererName, String falseHeard, String falseSeen) {
        super(fullName, sex, age, stressLevel, cooperationLevel, look, physicalAspect, consideredInnocent);
        this.m_murdererName = murdererName;
        m_alibi = new Deposition(this.m_fullName, alibi, DepositionType.ALIBI, false);
        m_heardTestimony = new Deposition(this.m_fullName, falseHeard, DepositionType.HEARD, true);
        m_seenTestimony = new Deposition(this.m_fullName, falseSeen, DepositionType.SEEN, true);
        
        this.m_falseAlibi = null;
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
                if (m_falseAlibi == null) {
                    createFalse(DepositionType.ALIBI);
                }
                m_falseAlibi.display();
                
                if (!this.m_clueList.contains(m_falseAlibi)) {
                    this.m_clueList.add(m_falseAlibi);
                }
                break;
            case FAILURE:
                m_alibi.display();
                
                if (!m_clueList.contains(m_alibi)) {
                    m_clueList.add(m_alibi);
                }
                break;
            case CRITIC_FAILURE:
                //String part1 = "Ce soir là, j'ai participé à ce meurtre. Voilà. Vous êtes content ? ",
                //        part2 = "Maintenant, vous pouvez arrêter avec vos questions : je n'en dirai pas plus.";
                Deposition declaration = new Deposition(this.m_fullName, " complice de ce crime", DepositionType.ROLE, false);
                declaration.display();
                
                if (!this.m_clueList.contains(declaration)) {
                    this.m_clueList.add(declaration);
                }
                break;
        }
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
        
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
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
                this.textNoSpeak();
                break;
            case CRITIC_FAILURE:
                m_heardTestimony = null;
                m_seenTestimony = null;
                
                this.textForget();
                break;
        }
    }
    
    @Override
    public void createFalse(DepositionType category) {//crée témoigage bidon avec aléatoire
        String text;
        switch (category) {
            case SEEN:
                String[] object = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"};
                
                ArrayList <String> suspect = suspectsNameList();
                suspect.remove(this.m_fullName);
                suspect.remove(this.m_murdererName);
                
                text = suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)] + " près du lieu du crime.";
                m_seenTestimony = new Deposition(this.m_fullName, text, DepositionType.SEEN, true);
                break;
            case HEARD:
                String[] sound = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
                
                text = sound[(int) (Math.random() * sound.length)] + " près du lieu du crime.";
                m_heardTestimony = new Deposition(this.m_fullName, text, DepositionType.HEARD, true);
                break;
            case ALIBI:
                text = m_alibi.getContent() + ". Il y avait aussi " + this.m_murdererName + " avec moi.";
                
                m_falseAlibi = new Deposition(this.m_fullName, text, DepositionType.ALIBI, true);
                break;
        }
    }
}

    

