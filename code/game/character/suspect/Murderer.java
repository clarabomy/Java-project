
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.character.Sex;
import project.game.investigation.Clue;
import static project.game.investigation.Investigation.suspectsNameList;
import project.game.investigation.TestimonyType;
import project.investigation.InvestElement.Testimony;

/**
 *
 * @author ISEN
 */
public class Murderer extends Suspect implements Lie  {//majoritairement codé - en cours de débugage
    protected String m_motive;
    protected String m_partnerName;
    
    /*$$ CONSTRUCTOR $$*/    
    public Murderer(String name, String surname, Sex sex, int age, int stressLevel, int[] testimonyRef, String look, String physicalAspect, String motive, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, false, testimonyRef, clueList);
        this.m_motive = motive;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getMotive() {//utile?
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        int[] validStage = {M_COHERENCE_VALID, M_CREDIBILITY_VALID};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                this.createFalseAlibi();//affiche comme pour innocent
                break;
            case SUCCESS:
                this.createFalseAlibi();
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
    public void createFalseAlibi() {
        //crée alibi bidon avec aléatoire
        String[] activity   = {"J'ai travaillé", "Je me suis reposé(e)", "J'ai mangé", "Je suis sorti(e)", "J'étais"},
                    place   = {"au restaurant", "à l'hotel", "chez moi", "chez un ami", "dans un parc", },
                    witness = {"seul", "avec un ami", "avec ma femme", "avec mon équipe", "avec mon chien"};

        String alibi = activity[(int) (Math.random() * activity.length)] + " " + witness[(int) (Math.random() * witness.length)] + " " + place[(int) (Math.random() * place.length)] + ".";
        
        //l'affiche
        m_console.display(this.getFullName(), alibi, false).execContinue();
    }//end void alibi_FalseLead

    
    @Override
    public void giveTestimony() {
        //crée temoignage si n'en a pas
        for (int i = 0; i < this.m_testimonyRef.length; i++) {
            if (this.m_testimonyRef[i] == -1) {
                this.m_testimonyRef[i] = this.createFalseTestimony(i == 0? TestimonyType.SEEN : TestimonyType.HEARD);//index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
            }
        }
        
        //affiche ce qu'il reussit a faire
        int[] validStage = {M_COHERENCE_VALID, M_CREDIBILITY_VALID};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display("ce que j'ai vu" + "\n" + "ce que j'ai entendu", false); //afficher comme pour innocent
                break;
            case SUCCESS:
                //Donner un témoignage : soit ce qu'il a vu, soit ce qu'il a entendu
                m_console.display((Math.random() < 0.5)? "ce que j'ai vu" : "ce que j'ai entendu", false); //afficher comme pour innocent
                break;
            case FAILURE:
                this.contradiction();
                break;
            case CRITIC_FAILURE:
                this.confess();
                /*
                //pour justifier createFalseTestimony? confess qu'à la fin?
                //effacer temoignage
                this.textForget();
                */
                break;
        }
    }//end void giveTestimony
    
    @Override
    public int createFalseTestimony(TestimonyType category) {//crée témoigage bidon avec aléatoire
        //initialise variables
        String[] suspect    = suspectsNameList(),
                    object  = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"},
                    sound   = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
    
        //remplace noms des mechants par d'autres
        int[] unreachablesIndex = {
            java.util.Arrays.asList(suspect).indexOf(this.getFullName()),
            java.util.Arrays.asList(suspect).indexOf(this.m_partnerName)
        };
        for (int i = 0; i < unreachablesIndex.length; i++) {
            int newIndex = -1;
            do {
                newIndex = (int) (Math.random() * suspect.length);
            } while (newIndex != unreachablesIndex[0] && newIndex != unreachablesIndex[1]);
            suspect[unreachablesIndex[i]] = suspect[newIndex];
        }
        
        //cree temoignage
        String testimony = "";
        switch (category) {
            case SEEN:
                testimony = "J'ai vu " + suspect[(int) (Math.random() * suspect.length)] + " avec " + object[(int) (Math.random() * object.length)];
                break;
            case HEARD:
                testimony = "J'ai entendu " + sound[(int) (Math.random() * sound.length)];
                break;
        }
        testimony += " près du lieu du crime.";
        
        
        //Dans le tableau d'indice, ajoute le témoignage avec islie = true
        Testimony lie = new Testimony();
        //lie = new Testimony(this, true, testimony);
        m_clueList.add(lie);
                
        //l'affiche
        m_console.display(this.getFullName(), testimony, false).execContinue();
        
        int indexTab = 1;
        return indexTab;
    }//end void testimony_addTestimony
    
    
    @Override
    public void contradiction() {
        m_console.display("Enquêteur", "Le suspect semble mal à l'aise. Ses propos sont contradictoires. Il cache quelque chose...", false).execContinue();
    }//end void contradiction()

    
    
    public void confess(){
        String nom = this.getFullName();
        m_console.display(nom, "C'est bon, je vais tout vous avouer...", false).execContinue();
        m_console.display(nom, "Raconte ce qui s'est passé (pourquoi, comment)", false).execContinue();
        
        String text = "J'ai fait tout ça pour " + m_motive + ". Et vous, qu'auriez-vous fait à ma place?";
        m_console.display(nom, text, false).execContinue();
    }//end void confess
}
