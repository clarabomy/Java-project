/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.character.suspect;

import java.util.ArrayList;
import static project.game.Game.getConsole;
import project.game.character.Sex;
import static project.game.investigation.Investigation.suspectsNameList;
import project.game.investigation.Deposition;
import project.game.investigation.DepositionType;

/**
 *
 * @author ISEN
 */
public class CrimePartner extends Suspect implements Lie {
    protected String m_falseAlibi;
    protected String m_murdererName;

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie
    public CrimePartner(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, String alibi, String murdererName) {
        super(name, surname, sex, age, stressLevel, cooperationLevel, look, physicalAspect);
        this.m_murdererName = murdererName;
        m_alibi = new Deposition(this.m_fullName, alibi, DepositionType.ALIBI, false);
        
        this.m_falseAlibi = null;
        m_heardTestimony = null;
        m_seenTestimony = null;
    }
    //chargement partie
    public CrimePartner(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, String alibi, String murdererName, String falseHeard, String falseSeen) {
        super(name, surname, sex, age, stressLevel, cooperationLevel, look, physicalAspect);
        this.m_murdererName = murdererName;
        m_alibi = new Deposition(this.m_fullName, alibi, DepositionType.ALIBI, false);
        m_heardTestimony = new Deposition(this.m_fullName, falseHeard, DepositionType.HEARD, true);
        m_seenTestimony = new Deposition(this.m_fullName, falseSeen, DepositionType.SEEN, true);
        
        this.m_falseAlibi = null;
    }

    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        Deposition declaration;
        
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                this.textLawyer();
                break;
            case SUCCESS:
                if (this.m_falseAlibi == null) {
                    this.createFalse(DepositionType.ALIBI);
                }
                getConsole().display(this.m_fullName, this.m_falseAlibi, false);
                
                declaration = new Deposition(this.m_fullName, m_falseAlibi, DepositionType.ALIBI, true);
                if (!this.m_clueList.contains(declaration)) {
                    this.m_clueList.add(declaration);
                }
                break;
            case FAILURE:
                getConsole().display(this.m_fullName, this.m_alibi.getContent(), false);
                
                if (!this.m_clueList.contains(m_alibi)) {
                    this.m_clueList.add(m_alibi);
                }
                break;
            case CRITIC_FAILURE:
                String part1 = "Ce soir là, j'ai participé à ce meurtre. Voilà. Vous êtes content ? ",
                        part2 = "Maintenant, vous pouvez arrêter avec vos questions : je n'en dirai pas plus.";
                getConsole().display(this.m_fullName, part1 + part2, false);
                
                declaration = new Deposition(this.m_fullName, part1 + part2, DepositionType.ALIBI, false);
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
        String text;
        switch (category) {
            case SEEN:
                String[] object = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"};
                
                ArrayList <String> suspect = suspectsNameList();
                suspect.remove(this.m_fullName);
                suspect.remove(this.m_murdererName);
                
                text = "J'ai vu " + suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)] + " près du lieu du crime.";
                
                m_seenTestimony = new Deposition(this.m_fullName, text, DepositionType.SEEN, true);
                break;
            case HEARD:
                String[] sound = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
                
                text = "J'ai entendu " + sound[(int) (Math.random() * sound.length)] + " près du lieu du crime.";
                
                m_heardTestimony = new Deposition(this.m_fullName, text, DepositionType.HEARD, true);
                break;
            case ALIBI:
                this.m_falseAlibi = this.m_alibi + ". Il y avait aussi " + this.m_murdererName + " avec moi.";
                break;
        }
    }
}

    

