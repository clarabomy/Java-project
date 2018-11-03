/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.suspect;

/**
 *
 * @author Thibaut
 */
public class Murderer extends Suspect implements Lie  {
    protected String m_motive;

    
    /*$$ CONSTRUCTOR $$*/
    public Murderer(String name, boolean sex, int age, int stressLevel, int cooperationLevel, String personality, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String motive) {
        super(name, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.m_motive = motive;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getMotive() {
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        //Lance dé pour crédibilité et cohérence puis affiche pour roleplay
        int coherenceThrow = rollDice(), credibThrow = rollDice();    
        
        
        //determine action
        int action = 0;
        if (coherenceThrow < M_COHERENCE_MIN[m_diff]) {
            //si réussite critique au premier : bonus pour le second
            if (credibThrow < M_CREDIBILITY_MIN[m_diff] + ((coherenceThrow < M_CRITICAL_SUCCESS)? M_BONUS[m_diff] : 0)) {
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
                if (credibThrow < M_CREDIBILITY_MIN[m_diff] - M_MALUS[m_diff]) {
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
        
        
        //affiche pour debug
        String[] category = {"la cohérence", "la crédibilité"};
        int[] result = {coherenceThrow, credibThrow};
        m_console.displayThrow(category, result, action).execContinue();
        
        
        //Si ok : créer fausse piste (donner faux alibi) | sinon : seContredit()
        switch (action) {
            case 1:
                //semble dire la vérité / envie de le croire..
                break;
            case 2:
                //crée fausse piste
                break;
            case 3:
                //se contredit
                m_console.display("Enquêteur", "Le suspect semble mal à l'aise. Il cache quelque chose...",  "continuer").execContinue();
                break;
            case 4:
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
            
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void giveTestimony

    
    @Override
    public void all_Lie() {
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
    }//end void confess
}
