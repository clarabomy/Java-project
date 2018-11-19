
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.character.Sex;
import project.game.investigation.Clue;
import static project.game.investigation.Investigation.suspectsNameList;
import project.game.investigation.Testimony;
import project.game.investigation.TestimonyType;

/**
 *
 * @author ISEN
 */
public class Murderer extends Suspect implements Lie  {//majoritairement codé - en cours de débugage
    protected String m_motive;
    protected String m_falseAlibi;
    
    /*$$ CONSTRUCTOR $$*/    
    public Murderer(String name, String surname, Sex sex, int age, int stressLevel, int[] testimonyRef, String look, String physicalAspect, String motive, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, stressLevel, look, physicalAspect, false, testimonyRef, clueList);
        this.m_motive = motive;
        this.m_falseAlibi = null;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getMotive() {//utile?
        return m_motive;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void giveAlibi() {
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                this.textLawyer();
                break;
            case SUCCESS:
                //crée alibi s'il n'en a pas
                if (this.m_falseAlibi == null) {
                    this.createFalseAlibi();
                }
                this.m_console.display(this.getFullName(), this.m_falseAlibi, false);
                break;
            case FAILURE:
                this.m_falseAlibi = null;
                this.textForget();
                break;
            case CRITIC_FAILURE:
                String part1 = "Vous voulez savoir ce que je faisais, ce " + "soir" + "là? Vraiment? Bien, je vais vous le dire : ",
                        part2 = "j'étais occupé à assassiner " + "nameVictim" + " !";
                m_console.display(this.getFullName(), part1 + part2, false);
                break;
        }
        this.m_console.execContinue();
    }

    @Override
    public void createFalseAlibi() {
        //crée alibi bidon avec aléatoire
        String[] activity   = {"J'ai travaillé", 
                                "Je me suis reposé(e)", 
                                "J'ai mangé", 
                                "Je suis sorti(e)", 
                                "J'étais"},
                    place   = {"au restaurant", 
                                "à l'hotel", 
                                "chez moi", 
                                "chez un ami", 
                                "dans un parc"};
        ArrayList <String> suspect = suspectsNameList();
        suspect.remove(this.getFullName());

        this.m_falseAlibi = activity[(int) (Math.random() * activity.length)] + " " + 
                    place[(int) (Math.random() * place.length)];
        
        int nbSuspectsIncluded = (int) (Math.random() * 3); //entre 0 et 3
        if (nbSuspectsIncluded == 0) {
            this.m_falseAlibi += ", seul";
        }
        else {
            for (int i = 0; i < nbSuspectsIncluded; i++) {
                if (i == 0) {
                    this.m_falseAlibi += " avec ";
                }
                else if (i != nbSuspectsIncluded - 2) {
                    this.m_falseAlibi += ", ";
                }
                else if (i != nbSuspectsIncluded - 1) {
                    this.m_falseAlibi += " et ";
                }
                String person = suspect.get((int) (Math.random() * suspect.size()));
                suspect.remove(person);
                this.m_falseAlibi += person;
            }
        }
        this.m_falseAlibi += ".";
    }

    @Override
    public void giveTestimony() {
        //crée temoignage si n'en a pas
        for (int i = 0; i < this.m_testimonyRef.length; i++) {
            if (this.m_testimonyRef[i] == -1) {
                this.createFalseTestimony(i == 0? TestimonyType.SEEN : TestimonyType.HEARD);//index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
            }
        }
        
        //affiche ce qu'il reussit a faire
        String seen = "J'ai vu " + this.m_clueList.get(this.m_testimonyRef[0]).getContent() + ".";
        String heard = "J'ai entendu" + this.m_clueList.get(this.m_testimonyRef[1]).getContent() + ".";
        
        int[] validStage = {M_COHERENCE_VALID[this.m_difficulty], M_CREDIBILITY_VALID[this.m_difficulty]};
        switch (rollMultiDice(validStage, null, false)) {
            case CRITIC_SUCCESS:
                m_console.display(this.getFullName(), seen + heard, false);
                break;
            case SUCCESS:
                m_console.display(this.getFullName(), (Math.random() < 0.5)? seen : heard, false);//soit ce qu'il a vu, soit ce qu'il a entendu
                break;
            case FAILURE:
                this.textNoSpeak();
                break;
            case CRITIC_FAILURE:
                for (int i = 0; i < this.m_testimonyRef.length; i++) {
                    this.m_clueList.remove(this.m_testimonyRef[i]);
                    this.m_testimonyRef[i] = -1;
                }
                this.textForget();
                break;
        }
    }
    
    @Override
    public void createFalseTestimony(TestimonyType category) {//crée témoigage bidon avec aléatoire
        //initialise variables
        String[] object  = {"une pipe", "un homme qui avait une forte carrure", "un homme qui avait une canne", "une femme de petite taille", "une femme classe"},
                    sound   = {"un chien", "un coup de feu", "une voix d'homme", "une voix de femme"};
    
        
        //cree temoignage
        String testimony = "";
        switch (category) {
            case SEEN:
                ArrayList <String> suspect = suspectsNameList();
                suspect.remove(this.getFullName());
                
                testimony = "J'ai vu " + suspect.get((int) (Math.random() * suspect.size())) + " avec " + object[(int) (Math.random() * object.length)];
                break;
            case HEARD:
                testimony = "J'ai entendu " + sound[(int) (Math.random() * sound.length)];
                break;
        }
        testimony += " près du lieu du crime.";
        
        
        //Dans le tableau d'indice, ajoute le témoignage avec islie = true et enregistre position
        this.m_testimonyRef[category == TestimonyType.SEEN? 0 : 1] = m_clueList.size();//index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
        Testimony lie = new Testimony(this, true, testimony);
        m_clueList.add(lie);
    }
    
    public void confess(){
        String nom = this.getFullName();
        m_console.display(nom, "C'est bon, je vais tout vous avouer...", false).execContinue();
        m_console.display(nom, "C'est moi le coupable ! AH AH AH AH AH !", false).execContinue();
        
        String text = "J'ai fait tout ça pour " + m_motive + ". Et vous, qu'auriez-vous fait à ma place?";
        m_console.display(nom, text, false).execContinue();
    }
}
