/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.suspect;

import project.game.investigation.Sex;

/**
 *
 * @author Thibaut
 */
public class Murderer extends Suspect implements Lie  {
    protected String m_motive;

    
    /*$$ CONSTRUCTOR $$*/
    public Murderer(String name, Sex sex, int age, int stressLevel, int cooperationLevel, String personality, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String motive) {
        super(name, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.m_motive = motive;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getMotive() {
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    private int throwForCoherenceAndCredib() {
        int coherenceThrow = rollDice(), credibThrow = rollDice();    
        
        
        //determine action
        int action = 0;
        
        if (coherenceThrow >= M_CRITICAL_FAILURE) action = 4;
        else if ((coherenceThrow <= M_COHERENCE_VALID[m_diffGame] && 
            credibThrow <= M_CREDIBILITY_VALID[m_diffGame] + ((coherenceThrow < M_CRITICAL_SUCCESS)? m_bonus : 0))
            ||
            (coherenceThrow > M_COHERENCE_VALID[m_diffGame] && //coherenceThrow < M_CRITICAL_FAILURE && déjà vérifié
            credibThrow <= M_CREDIBILITY_VALID[m_diffGame] - m_malus)) {
            action = (credibThrow <= M_CRITICAL_SUCCESS)? 1 : 2 ;
        }
        else if ((coherenceThrow <= M_COHERENCE_VALID[m_diffGame] &&
            credibThrow > M_CREDIBILITY_VALID[m_diffGame] + ((coherenceThrow < M_CRITICAL_SUCCESS)? m_bonus : 0))
            ||
            (coherenceThrow > M_COHERENCE_VALID[m_diffGame] && //coherenceThrow < M_CRITICAL_FAILURE && déjà vérifié
            credibThrow > M_CREDIBILITY_VALID[m_diffGame] - m_malus)) {
            action = (credibThrow < M_CRITICAL_FAILURE)? 3 : 4 ;
        }
        
        
        /*
        //version longue
        if (coherenceThrow < M_COHERENCE_VALID[m_diffGame]) {
            //si réussite critique au premier : bonus pour le second
            if (credibThrow < M_CREDIBILITY_VALID[m_diffGame] + ((coherenceThrow < M_CRITICAL_SUCCESS)? M_BONUS[m_diffGame] : 0)) {
                if (credibThrow < M_CRITICAL_SUCCESS) action = 1;//réussite critique
                else action = 2;//réussite simple
            }
            else {
                if (credibThrow < M_CRITICAL_FAILURE) action = 3;//échec simple
                else action = 4;//échec critique
            }
        }
        else {
            if (coherenceThrow < M_CRITICAL_FAILURE) {
                //si échec simple pour le premier, malus pour le second
                if (credibThrow < M_CREDIBILITY_VALID[m_diffGame] - M_MALUS[m_diffGame]) {
                    if (credibThrow < M_CRITICAL_SUCCESS) action = 1;//réussite critique
                    else action = 2;//réussite simple
                }
                else {
                    if (credibThrow < M_CRITICAL_FAILURE) action = 3;//échec simple
                    else action = 4;//échec critique
                }
            }
            else action = 4;//échec critique
        }
        */
        
        
        //affiche pour debug
        String[] category = {"la cohérence", "la crédibilité"};
        int[] result = {coherenceThrow, credibThrow};
        m_console.displayThrow(category, result, action).execContinue();
        
        return action;
    }
    
    @Override
    public void giveAlibi() {
        //Lance dé pour crédibilité et cohérence
        //Si ok : créer fausse piste (donner faux alibi) | sinon : seContredit()
        switch (throwForCoherenceAndCredib()) {
            case 1: //réussite critique
                //semble dire la vérité / envie de le croire..
                break;
            case 2: //réussite
                //crée fausse piste
                break;
            case 3: //échec
                //se contredit
                m_console.display("Enquêteur", "Le suspect semble mal à l'aise. Il cache quelque chose...",  "continuer").execContinue();
                this.contradiction();
                break;
            case 4: //échec critique
                //passe aux aveux
                m_console.display("Enquêteur", "Le suspect semble mal à l'aise. Il cache quelque chose...",  "continuer").execContinue();
                m_console.display(this.m_name, "Je n'y arrive plus, je vais tout vous avouer...",  "continuer").execContinue();
                this.confess();
                break;
            default:
                //throw new ...
                break;
        }
    }//end void giveAlibi

    
    @Override
    public void giveTestimony() {
        //Lance dé pour crédibilité et cohérence
           //Si ok, inventeTémoignage()
           //sinon, seContredit()
        switch (throwForCoherenceAndCredib()) {
            case 1:
                //invente super témoignage
                break;
            case 2:
                //invente témoignage
                break;
            case 3:
                //se contredit
                break;
            case 4:
                //dit de la merde
                break;
            default:
                //throw new ...
                break;
        }
    }//end void giveTestimony

    
    @Override
    public void contradiction() { //Se contredire
        //Afficher le suspect a dit des choses contradictoires => c'est étrange...
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void all_Lie

    
    @Override
    public void alibi_FalseLead() {
        //donne un alibi au hasard
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void alibi_FalseLead

    
    @Override
    public void testimony_addTestimony() {
        //Dans le tableau d'indice, ajoute un témoignage avec islie = true
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void testimony_addTestimony
    
    
    public void confess(){
        //tout avouer + dit ce qui s'est passé
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void confess
}
