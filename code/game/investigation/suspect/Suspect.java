/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.suspect;

import project.game.investigation.LiveCharacter;


/**
 *
 * @author Thibaut
 */
public abstract class Suspect extends LiveCharacter {
    int m_stress;
    String m_look;
    String m_physicalAspect;
    boolean m_findedInnocent;
    int[] m_testimonyRef;

    
    public Suspect(String name, boolean sex, int age, int stressLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef) {
        super(name, sex, age);
        this.m_stress = stressLevel;
        this.m_look = look;
        this.m_physicalAspect = physicalAspect;
        this.m_findedInnocent = findedInnocent;
        this.m_testimonyRef = new int[testimonyRef.length];
        System.arraycopy(testimonyRef, 0, this.m_testimonyRef, 0, testimonyRef.length);
    }

    
    public int getM_stress() {
        return m_stress;
    }
    
    
    public void BeInterrogated() {
        //Présentation du personnage presenterPerso() = description littéraire de qui il est
        //AfficherInfos() = stats du perso + description physique
        
        //Menu => 2 fonctions
            //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
            //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    abstract void giveAlibi();
    
    
    abstract void giveTestimony();
    
    
    @Override
    public int rollDice() {
        int roll = (int) (Math.random() * M_SIDES) + 1;
        System.out.println(roll);
        return(roll); 
    }
    
    
    @Override
    public void presentCharacter(){
        //Affiche la description littéraire de qui il est (nom, sexe, age) + description physique (look, physicalAspect)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void BeDisculpated(){
        //on modifie findedInnocent + vous avez décidé de disculpté ... 
    }
    
    
    public void BeArrested(){
        //si c'est coupable = bonne fin => enquête réussi
        //sinon => il y a eu de nouveaux meurtres => vous êtes virés !
    }
    
    
    @Override
    public void displayInfos() {
        //Affiche les niveaux de stress et de coopération du suspect
    }
}