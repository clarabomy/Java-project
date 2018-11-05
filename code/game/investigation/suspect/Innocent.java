
package project.game.investigation.suspect;

import project.game.investigation.Sex;

/**
 *
 * @author ISEN
 */

public class Innocent extends Suspect {
    protected String m_alibi;
    protected int m_cooperation;

    
    /*$$ CONSTRUCTOR $$*/
    public Innocent(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String personality, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi) {
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
    public void BeInterrogated() {
        //Présentation du personnage presenterPerso() = description littéraire de qui il est + description physique 
        //AfficherInfos() = stats du perso 
        
        //Menu => 2 fonctions
            //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void BeInterrogated
    
    
    @Override
    public void giveAlibi() { //options à déterminer 
        //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
        switch(throwDoubleDices(m_stress, m_cooperation)) {
            case 1: //succès critique
            case 2:
                System.out.printf("%s", this.getAlibi()); //trouver meilleure phrase
                break;
            case 3:
            case 4:
                System.out.println("Euh... je ne m'en souviens pas...");
                break;
        }
    }//end void giveAlibi

    
    @Override
    public void giveTestimony() {
        switch(throwDoubleDices(m_stress, m_cooperation)) {
            case 1: //succès critique
            case 2:
                //Donner un témoignage
                break;
            case 3:
            case 4:
                System.out.println("Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !");
                break;
        }
        //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void giveTestimony
}
