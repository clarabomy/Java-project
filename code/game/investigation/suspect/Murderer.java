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
public class Murderer extends Suspect implements Lie  {//majoritairement codé - pas testé
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
    @Override
    public void giveAlibi() {
        //Lance dé pour crédibilité et cohérence
        //Si ok : créer fausse piste (donner faux alibi) | sinon : seContredit()
        switch (throwDoubleDices(M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame])) {
            case 1: //réussite critique
                //affiche comme pour innocent
                this.alibi_FalseLead();
                break;
            case 2: //réussite
                this.alibi_FalseLead();
                break;
            case 3: //échec
                this.contradiction();
                break;
            case 4: //échec critique
                this.confess();
                break;
        }
    }//end void giveAlibi

    
    @Override
    public void giveTestimony() {
        //Lance dé pour crédibilité et cohérence
           //Si ok, inventeTémoignage()
           //sinon, seContredit()
        switch (throwDoubleDices(M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame])) {
            case 1:
                //affiche comme pour innocent
                this.testimony_addTestimony();
                break;
            case 2:
                this.testimony_addTestimony();
                break;
            case 3:
                this.contradiction();
                break;
            case 4:
                //dit de la merde
                break;
        }
    }//end void giveTestimony

    
    @Override
    public void contradiction() { //Se contredire
        //Afficher le suspect a dit des choses contradictoires => c'est étrange...
        m_console.display("Enquêteur", "Le suspect semble mal à l'aise. Ses propos sont contradictoires. Il cache quelque chose...", false).execContinue();
    }//end void contradiction()

    
    @Override
    public void alibi_FalseLead() {
        //donne un alibi bidon au hasard
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void alibi_FalseLead

    
    @Override
    public void testimony_addTestimony() {
        //Dans le tableau d'indice, ajoute un témoignage avec islie = true
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void testimony_addTestimony
    
    
    public void confess(){
        //tout avouer + dit ce qui s'est passé
        m_console.display(this.m_name, "Je n'y arrive plus, je vais tout vous avouer...", false).execContinue();
        throw new UnsupportedOperationException("Not fully supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void confess
}
