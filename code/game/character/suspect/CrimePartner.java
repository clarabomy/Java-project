/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.character.Sex;
import project.game.investigation.Clue;
import project.game.investigation.TestimonyType;

/**
 *
 * @author ISEN
 */
public class CrimePartner extends Suspect implements Lie {
    protected String m_alibi;
    protected int m_cooperation;

    
    /*$$ CONSTRUCTOR $$*/
    public CrimePartner(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef, clueList);
        this.m_alibi = alibi;
        this.m_cooperation = cooperationLevel;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getAlibi() {
        return m_alibi;
    }

    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() { //trouver les options
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + complice utilise cohérence et crédibilité pour mentir et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si complice stresse pas trop : va avoir plus de mal à faire passer un mensonge
            //sinon : ne va pas réussit à mentir
        //s'il réussit mal : 
            //si complice stresse pas trop : va réussir plus facilement
            //sinon : va avoir plus de mal à faire passer un mensonge
        
            
        //Lance dé pour stress, cohérence 
            //Si ok, créer fausse piste (donner faux alibi)
            //sinon, seContredit() + donne son vrai alibi
        int[] validStage = {M_COHERENCE_VALID, M_CREDIBILITY_VALID};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                this.createFalseAlibi();
                break;
            case SUCCESS:
                //donne son alibi
                break;
            case FAILURE:
                this.textAvocat();
                break;
            case CRITIC_FAILURE:
                this.contradiction();
                break;
        }
    }
    
    
    @Override
    public void createFalseAlibi() {
        //modifie son alibi + isLie = true
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public void giveTestimony() {
        //crée temoignage si n'en a pas
        for (int i = 0; i < this.m_testimonyRef.length; i++) {
            if (this.m_testimonyRef[i] == -1) {
                this.m_testimonyRef[i] = this.createFalseTestimony(i == 0? TestimonyType.SEEN : TestimonyType.HEARD);//index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
            }
        }
        
        //affiche ce qu'il reussit a faire
        int[] validStage = {M_COHERENCE_VALID, M_CREDIBILITY_VALID};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display("ce que j'ai vu" + "\n" + "ce que j'ai entendu", false); //afficher comme pour innocent
                break;
            case SUCCESS:
                //Donner un témoignage : soit ce qu'il a vu, soit ce qu'il a entendu
                m_console.display((Math.random() < 0.5)? "ce que j'ai vu" : "ce que j'ai entendu", false); //afficher comme pour innocent
                break;
            case FAILURE:
                this.textAvocat();
                //ou this.contradiction();
                break;
            case CRITIC_FAILURE:
                this.contradiction();
                //ou effacer temoignage
                //this.textForget();
                break;
        }
    }
    
    
    @Override
    public int createFalseTestimony(TestimonyType category) {
        //Ajouter un témoignage
        //Phrase : le suspect n'avait pas l'air très inquiet
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public void contradiction() {
        //Afficher le suspect a dit des choses contradictoires mais n'a pas l'air inquiet..
        // + isLie = false
        System.out.println("Le suspect a dit des choses contradictoires mais n'a pas l'air très inquiet");
    }
}

    

