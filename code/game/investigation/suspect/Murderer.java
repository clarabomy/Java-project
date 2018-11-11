
package project.game.investigation.suspect;

import static project.game.Investigation.suspectsNameList;
import project.game.investigation.DiceResult;
import project.game.investigation.Investigator;
import project.game.investigation.Sex;

/**
 *
 * @author ISEN
 */
public class Murderer extends Suspect implements Lie  {//majoritairement codé - en cours de débugage
    protected String m_motive;
    
    /*$$ CONSTRUCTOR $$*/
    public Murderer(String name, String surname, Sex sex, int age, int stressLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, String motive) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, findedInnocent, testimonyRef);
        this.m_motive = motive;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getMotive() {//utile?
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi(DiceResult actionInvestigator) {
        
        //si inspecteur n a pas bien réussi : (pas du tout : bonus)
            //si perso ne stresse pas trop :    réussit bien
            //sinon :                           plus de mal à faire passer un mensonge
        //sinon : (completement : malus)
            //si perso ne stresse pas trop :    plus de mal à faire passer un mensonge
            //sinon :                           ne réussit pas à mentir
            
            
        //Lance dé pour crédibilité et cohérence
        //Si ok : créer fausse piste (donner faux alibi) | sinon : seContredit()
        int[] validStage = {M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame]};
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
                this.confess();
                break;
        }
    }//end void giveAlibi

    
    @Override
    public void giveTestimony(DiceResult actionInvestigator) {
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché) + meurtricer utilise cohérence et crédibilité pour mentir et lutte contre stress (jet caché)
        //si inspecteur réussit bien : 
            //si stresse pas trop : meurtrier va avoir plus de mal à faire passer un mensonge
            //sinon : ne va pas réussit à mentir
        //s'il réussit mal : 
            //si stresse pas trop : meurtrier va réussir plus facilement
            //sinon : meurtrier va avoir plus de mal à faire passer un mensonge
        //int retours1 = Investigator.interrogateSuspect();
        //int retours2 = throwSimpleDice(m_stress, false);
        //int retours3 = throwDoubleDices(M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame], false);
        
        
            
        //Lance dé pour crédibilité et cohérence
           //Si ok, inventeTémoignage()
           //sinon, seContredit()
        int[] validStage = {M_COHERENCE_VALID[m_diffGame], M_CREDIBILITY_VALID[m_diffGame]};

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
                m_console.display("Enquêteur", "Le suspect refuse de parler. Bon, tant pis...", false).execContinue();
                break;
        }
    }//end void giveTestimony

    
    @Override
    public void contradiction() {
        m_console.display("Enquêteur", "Le suspect semble mal à l'aise. Ses propos sont contradictoires. Il cache quelque chose...", false).execContinue();
    }//end void contradiction()

    
    @Override
    public void createFalseLead() {
        //crée alibi bidon avec aléatoire
        String[] activity   = {"J'ai travaillé", "Je me suis reposé(e)", "J'ai mangé", "Je suis sorti(e)", "J'étais"},
                    place   = {"au restaurant", "à l'hotel", "chez moi", "chez un ami", "dans un parc", },
                    witness = {"seul", "avec un ami", "avec ma femme", "avec mon équipe", "avec mon chien"};
        
        String alibi = new StringBuilder(activity[(int) (Math.random() * activity.length)]).append(" ")
                                .append(witness[(int) (Math.random() * witness.length)]).append(" ")
                                .append(place[(int) (Math.random() * place.length)]).append(".").toString();
        
        //l'affiche
        m_console.display(this.getFullName(), alibi, false).execContinue();
    }//end void alibi_FalseLead

    
    @Override
    public void addTestimony() {
        //crée témoigage bidon avec aléatoire
        String[] suspect    = suspectsNameList(),
                    object  = {"une pipe", "un homme qui avait forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"},
                    sound   = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
        
        
        String testimony = "";
        switch ((int) (Math.random() * 2)) {//0, 1 ou 2
            case 0 :
                testimony = "Je n'ai rien vu ni entendu";
                break;
            case 1:
                testimony = new StringBuilder("J'ai vu ").append(suspect[(int) (Math.random() * suspect.length)])
                                        .append(" avec ").append(object[(int) (Math.random() * object.length)]).toString();
                break;
            case 2:
                testimony = new StringBuilder("J'ai entendu ").append(sound[(int) (Math.random() * sound.length)]).toString();
                break;
        }
        
        testimony = new StringBuilder(testimony).append(" près du lieu du crime.").toString();
        
        //Dans le tableau d'indice, ajoute le témoignage avec islie = true
        //...
        
        //l'affiche
        m_console.display(this.getFullName(), testimony, false).execContinue();
    }//end void testimony_addTestimony
    
    
    public void confess(){
        String nom = this.getFullName();
        m_console.display(nom, "C'est bon, je vais tout vous avouer...", false).execContinue();
        m_console.display(nom, "Raconte ce qui s'est passé (pourquoi, comment)", false).execContinue();
        
        String text = new StringBuilder("J'ai fait tout ça pour ").append(m_motive).append(". Et vous, qu'auriez-vous fait à ma place?").toString();
        m_console.display(nom, text, false).execContinue();
    }//end void confess
}
