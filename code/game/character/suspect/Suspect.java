
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.character.Investigator;
import project.game.character.LiveCharacter;
import project.game.character.Sex;
import project.game.investigation.Clue;
import project.game.investigation.TestimonyType;


/**
 *
 * @author ISEN
 */
public abstract class Suspect extends LiveCharacter {
    protected int m_stress;
    protected String m_look;
    protected String m_physicalAspect;
    protected boolean m_findedInnocent;
    protected int[] m_testimonyRef;

    
    /*$$ CONSTRUCTOR $$*/
    public Suspect(String name, String surname, Sex sex, int age, int stressLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, clueList);
        this.m_stress = stressLevel;
        this.m_look = look;
        this.m_physicalAspect = physicalAspect;
        this.m_findedInnocent = findedInnocent;
        this.m_testimonyRef = new int[testimonyRef.length];
        System.arraycopy(testimonyRef, 0, this.m_testimonyRef, 0, testimonyRef.length);
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public int getStressLevel() {
        return m_stress;
    }

    public String getLook() {
        return m_look;
    }

    public String getPhysicalAspect() {
        return m_physicalAspect;
    }

    public void setFindedInnocent(boolean findedInnocent) {
        this.m_findedInnocent = findedInnocent;
    }

    public void setTestimonyRef(int ref, TestimonyType index) {
        int key = index == TestimonyType.SEEN ? 0 : 1; //index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
        this.m_testimonyRef[key] = ref;
    }
    
   
    /*$$ METHODS $$*/
    abstract void giveAlibi();
    abstract void giveTestimony();
    
    @Override
    public void displayStats() {
        //Affiche les niveaux de stress du suspect
        String name = "Nom, prénom : " + this.getFullName();
        String sex = "Sexe : " + this.m_sex.toString();
        String age = "Age : " + this.m_age + " ans";
        String condition = "Etat : " + (this.m_stress > 50? "stressé" : "détendu");
        String physicalDescript = "Description physique : " + this.getPhysicalAspect() + ", " + this.getLook();
        
        m_console.display(name, true);
        m_console.display(sex, true);
        m_console.display(age, true);
        m_console.display(condition, true);
        m_console.display(physicalDescript, false).execContinue();
        
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        //Affiche la description littéraire de qui il est (nom, sexe, age) + description physique (look, physicalAspect)
        String text = "Bonjour, je m'appelle " + this.getFullName() + ". J'ai " + this.m_age + " ans. Vous vouliez me voir ?";
        m_console.display(text, false).execContinue();
    }//end void presentCharacter
    
    
    public void BeInterrogated(Investigator player) {
        //Présentation du personnage presenterPerso() = description littéraire de qui il est
        //AfficherInfos() = stats du perso + description physique
        
        //Menu => 2 fonctions
            //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
            //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)
        
        String[] choices = {"Que faisiez-vous pendant le crime?", "Avez-vous vu ou entendu quelque chose?"};
        int choix = m_console.display("Enquêteur", "Vous voilà au poste, dites moi...", choices, false).execSingleChoice();
       
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer des infos (jet affiché)
        int[] stats = {player.getIntelligence(), player.getManipulation()};
        String[] category = {"intelligence", "manipulation"};
        rollMultiDice(stats , category, true);
        
        //applique le choix
        switch (choix) {
            case 1:
                this.giveAlibi();
                break;
            case 2:
                this.giveTestimony();
                break;
        }
    }//end void BeInterrogated
    
    
    public void BeDisculpated(){
        //on modifie findedInnocent + vous avez décidé de disculpté ... 
        this.setFindedInnocent(true);
        String text = "Vous avez choisi de disculper " + this.getFullName();
        m_console.display(text, false).execContinue();
    }//end void BeDisculpated
    
    
    public void BeArrested(){
        //Si c'est coupable = bonne fin => enquête réussi
        //Sinon => il y a eu de nouveaux meurtres => vous êtes virés !
        if (this instanceof Murderer) {
            m_console.display("Bravo, vous avez réussi à trouver le coupable",false);
        }
        else {
            m_console.display("Votre supérieur","Il y a eu de nouveaux meurtres ! Vous êtes renvoyé !", false);
        }    
        m_console.execContinue();
    }//end void BeArrested
    
    
    protected void textAvocat() {
        m_console.display(this.getFullName(), "Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !", false);
    }
    
    protected void textForget() {
        m_console.display(this.getFullName(),"Euh... je ne m'en souviens pas...", false);
    }
}
