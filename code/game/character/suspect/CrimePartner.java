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
import project.game.investigation.Testimony;
import project.game.investigation.TestimonyType;

/**
 *
 * @author ISEN
 */
public class CrimePartner extends Suspect implements Lie {
    protected String m_alibi;
    protected String m_falseAlibi;
    protected int m_cooperation;
    protected String m_murdererName;

    
    /*$$ CONSTRUCTOR $$*/
    public CrimePartner(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef, clueList);
        this.m_alibi = alibi;
        this.m_falseAlibi = null;
        this.m_cooperation = cooperationLevel;
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
                if (this.m_falseAlibi == null) {
                    this.createFalseAlibi();
                }
                this.m_console.display(this.getFullName(), this.m_falseAlibi, false);
                break;
            case FAILURE:
                this.m_console.display(this.getFullName(), this.m_alibi, false);
                break;
            case CRITIC_FAILURE:
                String part1 = "Ce soir là, j'ai participé à ce meurtre. Voilà. Vous êtes content ? ",
                        part2 = "Maintenant, vous pouvez arrêter avec vos questions : je n'en dirai pas plus.";
                
                this.m_console.display(this.getFullName(), part1 + part2, false);
                break;
        }
        this.m_console.execContinue();
    }
    
    
    @Override
    public void createFalseAlibi() {
        this.m_falseAlibi = this.m_alibi + " Il y avait " + this.m_murdererName + "avec nous, aussi.";
    }
    
    @Override
    public void giveTestimony() {
        //crée temoignage si n'en a pas
        for (int i = 0; i < this.m_testimonyRef.length; i++) {
            if (this.m_testimonyRef[i] == -1) {
                this.createFalseTestimony(i == 0? TestimonyType.SEEN : TestimonyType.HEARD);//index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
            }
        }
        
        //affiche ce qu'il reussit a faire
        String seen = "J'ai vu " + this.m_clueList.get(this.m_testimonyRef[0]).getContent() + ".";
        String heard = "J'ai entendu" + this.m_clueList.get(this.m_testimonyRef[1]).getContent() + ".";
        
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
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
                for (int i = 0; i < this.m_testimonyRef.length; i++) {
                    this.m_clueList.remove(this.m_testimonyRef[i]);
                    this.m_testimonyRef[i] = -1;
                }
                this.textForget();
                break;
        }
        m_console.execContinue();
    }
    
    
    @Override
    public void createFalseTestimony(TestimonyType category) {//crée témoigage bidon avec aléatoire
        //initialise variables
        String[] object  = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"},
                    sound   = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
    
        
        //cree temoignage
        String testimony = "";
        switch (category) {
            case SEEN:
                ArrayList <String> suspect = suspectsNameList();
                suspect.remove(this.getFullName());
                suspect.remove(this.m_murdererName);
                
                testimony = "J'ai vu " + suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)];
                break;
            case HEARD:
                testimony = "J'ai entendu " + sound[(int) (Math.random() * sound.length)];
                break;
        }
        testimony += " près du lieu du crime.";
        
        
        //Dans le tableau d'indice, ajoute le témoignage avec islie = true et enregistre position
        this.m_testimonyRef[category == TestimonyType.SEEN? 0 : 1] = m_clueList.size();//index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
        Testimony lie = new Testimony(this, true, testimony);
        m_clueList.add(lie);
    }
}

    

