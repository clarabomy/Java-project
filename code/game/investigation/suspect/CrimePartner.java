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
public class CrimePartner extends Suspect implements Lie {
    String m_alibi;
    int m_cooperation;

    public CrimePartner(String name, boolean sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi) {
        super(name, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.m_alibi = alibi;
        this.m_cooperation = cooperationLevel;
    }

    public String getM_alibi() {
        return m_alibi;
    }

    @Override
    public void all_Lie() {
        //Afficher le suspect a dit des choses contradictoires mais n'a pas l'air inquiet..
       // + isLie = false
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alibi_FalseLead() {
        //modifie son alibi + isLie = true
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void testimony_addTestimony() {
         //Ajouter un témoignage
        //Phrase : le suspect n'avait pas l'air très inquiet
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void giveAlibi() {
        //Lance dé pour stress, cohérence 
            //Si ok, créer fausse piste (donner faux alibi)
            //sinon, seContredit() + donne son vrai alibi
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void giveTestimony() {
         //Lance dé pour stress, crédibilité et cohérence
           //Si ok, inventeTémoignage() en n'ayant pas l'air inquiet
           //sinon, seContredit() et finit par donner son vrai témoignage
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
