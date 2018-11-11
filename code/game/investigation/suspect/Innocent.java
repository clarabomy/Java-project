
package project.game.investigation.suspect;

import project.game.investigation.DiceResult;
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
    public void giveAlibi(DiceResult actionInvestigator) { //options à déterminer 
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + innocent utilise coopération pour l'aider et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si stresse pas trop : innocent dit ce qu'il sait
            //sinon : a du mal a parler
        //s'il réussit mal : 
            //si stresse pas trop : peut dire une partie de ce qu'il sait
            //sinon : ne dit rien
        int[] validStage = {m_stress, m_cooperation};
            
        //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS: 
            case SUCCESS:
                //System.out.printf("%s", this.getAlibi()); //trouver meilleure phrase
                break;
            case FAILURE:
            case CRITIC_FAILURE:
                m_console.display("Suspect", "Euh... je ne m'en souviens pas...", false).execContinue();
                break;
        }
    }//end void giveAlibi

    
    @Override
    public void giveTestimony(DiceResult actionInvestigator) {
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + innocent utilise coopération pour l'aider et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si stresse pas trop : innocent dit ce qu'il sait
            //sinon : a du mal a parler
        //s'il réussit mal : 
            //si stresse pas trop : peut dire une partie de ce qu'il sait
            //sinon : ne dit rien
            
        int[] validStage = {m_stress, m_cooperation};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS: //succès critique : ce qu'il a vu et ce qu'il a entendu
            case SUCCESS:
                //Donner un témoignage : soit ce qu'il a vu, soit ce qu'il a entendu
                break;
            case FAILURE://trop confus pour se souvenir
            case CRITIC_FAILURE:
                m_console.display("Suspect","Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !", false).execContinue();
                break;
        }
        //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void giveTestimony
}
