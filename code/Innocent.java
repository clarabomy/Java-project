/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ISEN
 */
public class Innocent extends Suspect {
    String alibi;
    int cooperation;

    public Innocent(String name, boolean sex, int age, int stressLevel, int cooperationLevel, String personality, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi) {
        super(name, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.alibi = alibi;
        this.cooperation = cooperationLevel;
    }

    public String getAlibi() {
        return alibi;
    }
    
    @Override
    void BeInterrogated() {
        //Présentation du personnage presenterPerso() = description littéraire de qui il est
        //AfficherInfos() = stats du perso + description physique
        
        //Menu => 2 fonctions
            //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void giveAlibi() {
        //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void giveTestimony() {
        //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
