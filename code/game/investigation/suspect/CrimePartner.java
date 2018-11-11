/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.suspect;

import project.game.investigation.DiceResult;
import project.game.investigation.Sex;

/**
 *
 * @author Thibaut
 */
public class CrimePartner extends Suspect implements Lie {
    protected String m_alibi;
    protected int m_cooperation;

    
    /*$$ CONSTRUCTOR $$*/
    public CrimePartner(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.m_alibi = alibi;
        this.m_cooperation = cooperationLevel;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getAlibi() {
        return m_alibi;
    }

    
    /*$$ METHODS $$*/
    @Override
    public void contradiction() {
        //Afficher le suspect a dit des choses contradictoires mais n'a pas l'air inquiet..
        // + isLie = false
        System.out.println("Le suspect a dit des choses contradictoires mais n'a pas l'air très inquiet");
    }//end void all_Lie

    
    @Override
    public void createFalseLead() {
        //modifie son alibi + isLie = true
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void alibi_FalseLead

    
    @Override
    public void addTestimony() {
        //Ajouter un témoignage
        //Phrase : le suspect n'avait pas l'air très inquiet
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void testimony_addTestimony

    
    @Override
    public void giveAlibi(DiceResult actionInvestigator) { //trouver les options
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
        int[] validStage = {m_stress, M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame]};
        
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS: 
                //affiche comme pour innocent
                this.createFalseLead();
                break;
            case SUCCESS: 
                this.createFalseLead();
                break;
            case FAILURE:
                this.contradiction();
                break;
            case CRITIC_FAILURE: 
                break;
        }
    }//end void giveAlibi
    
    
    @Override
    public void giveTestimony(DiceResult actionInvestigator) {
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + complice utilise cohérence et crédibilité pour mentir et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si complice stresse pas trop : va avoir plus de mal à faire passer un mensonge
            //sinon : ne va pas réussit à mentir
        //s'il réussit mal : 
            //si complice stresse pas trop : va réussir plus facilement
            //sinon : va avoir plus de mal à faire passer un mensonge
        
        
        //Lance dé pour stress, crédibilité et cohérence
           //Si ok, inventeTémoignage() en n'ayant pas l'air inquiet
           //sinon, seContredit() et finit par donner son vrai témoignage
        
        int[] validStage = {m_stress, M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                //affiche comme pour innocent
                this.addTestimony();
                break;
            case SUCCESS:
                this.addTestimony();
                break;
            case FAILURE:
                this.contradiction();
                break;
            case CRITIC_FAILURE:
                //dit de la merde
                break;
        }
    }//end void giveTestimony
}

