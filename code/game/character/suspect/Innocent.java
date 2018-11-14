
package project.game.character.suspect;

import project.game.character.Sex;

/**
 *
 * @author ISEN
 */

public class Innocent extends Suspect {
    protected String m_alibi;
    protected int m_cooperation;

    
    /*$$ CONSTRUCTOR $$*/
    public Innocent(String name, String surname, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String alibi) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.m_alibi = alibi;
        this.m_cooperation = cooperationLevel;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getAlibi() {
        return m_alibi;
    }
    
    
    /*$$ METHODS $$*/
    /*
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
*/
    
    
    @Override
    public void giveAlibi() { //options à déterminer 
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + innocent utilise coopération pour l'aider et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si stresse pas trop : innocent dit ce qu'il sait
            //sinon : a du mal a parler
        //s'il réussit mal : 
            //si stresse pas trop : peut dire une partie de ce qu'il sait
            //sinon : ne dit rien
        
            
        //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display(this.getAlibi(), false); //trouver meilleure phrase
                break;
            case SUCCESS:
                m_console.display(this.getAlibi(), false); //trouver meilleure phrase
                break;
            case FAILURE:
                m_console.display(this.getFullName(), "Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !", false);
                break;
            case CRITIC_FAILURE:
                //effacer alibi
                m_console.display(this.getFullName(),"Euh... je ne m'en souviens pas...", false);
                break;
        }
        m_console.execContinue();
    }//end void giveAlibi

    
    @Override
    public void giveTestimony() {
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + innocent utilise coopération pour l'aider et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si innocent stresse pas trop : innocent dit ce qu'il sait
            //sinon : a du mal a parler
        //s'il réussit mal : 
            //si innocent stresse pas trop : peut dire une partie de ce qu'il sait
            //sinon : ne dit rien
            
            
        //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)
        int[] validStage = {m_stress, m_cooperation};
        switch(rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS: //ce qu'il a vu et ce qu'il a entendu
                m_console.display(new StringBuilder("ce que j'ai vu").append("ce que j'ai entendu").toString(), false); //trouver meilleure phrase
                break;
            case SUCCESS:
                //Donner un témoignage : soit ce qu'il a vu, soit ce qu'il a entendu
                m_console.display((Math.random() < 0.5)? "ce que j'ai vu" : "ce que j'ai entendu", false); //trouver meilleure phrase
                break;
            case FAILURE:
                m_console.display("Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !", false);
                break;
            case CRITIC_FAILURE:
                //effacer temoignage
                m_console.display("Euh... je ne m'en souviens pas...", false);
                break;
        }
        m_console.execContinue();
    }//end void giveTestimony
}
