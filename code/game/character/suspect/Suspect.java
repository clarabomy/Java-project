
package project.game.character.suspect;

import java.util.ArrayList;
import project.game.Game;
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
    protected int m_difficulty;

    
    /*$$ CONSTRUCTOR $$*/
    public Suspect(String name, String surname, Sex sex, int age, int stressLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef, ArrayList <Clue> clueList) {
        super(name, surname, sex, age, clueList);
        this.m_stress = stressLevel;
        this.m_look = look;
        this.m_physicalAspect = physicalAspect;
        this.m_findedInnocent = findedInnocent;
        this.m_testimonyRef = new int[testimonyRef.length];
        System.arraycopy(testimonyRef, 0, this.m_testimonyRef, 0, testimonyRef.length);
        this.m_difficulty = Game.getDifficulty();
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public void setFindedInnocent(boolean findedInnocent) {
        this.m_findedInnocent = findedInnocent;
    }

    public void setTestimonyRef(int ref, TestimonyType index) {
        int key = index == TestimonyType.SEEN ? 0 : 1; //index 0 : ce qu'il a vu, 1 : ce qu'il a entendu
        this.m_testimonyRef[key] = ref;
    }
    
   
    /*$$ METHODS $$*/
    @Override
    public void presentCharacter() {
        //Affiche la description littéraire de qui il est (nom, sexe, age) + description physique (look, physicalAspect)
        String text = "Bonjour, je m'appelle " + this.getFullName() + ". J'ai " + this.m_age + " ans. Vous vouliez me voir ?";
        m_console.display(text, false).execContinue();
    }
    
    @Override
    public void displayStats() {
        //Affiche les niveaux de stress du suspect
        String name = "Nom, prénom : " + this.getFullName();
        String sex = "Sexe : " + this.m_sex.toString();
        String age = "Age : " + this.m_age + " ans";
        String condition = "Etat : " + (this.m_stress > 50? "stressé" : "détendu");
        String physicalDescript = "Description physique : " + this.m_physicalAspect + ", " + this.m_look;
        
        m_console.display(name, true);
        m_console.display(sex, true);
        m_console.display(age, true);
        m_console.display(condition, true);
        m_console.display(physicalDescript, false).execContinue();
        
    }
    
    public void BeInterrogated(Investigator player) {
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
    }
    
    abstract void giveAlibi();
    
    abstract void giveTestimony();
    
    protected void textNoSpeak() {
        m_console.display("Enquêteur", "Le suspect refuse de parler.", false);
    }
    
    protected void textLawyer() {
        m_console.display(this.getFullName(), "Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !", false);
    }
    
    protected void textForget() {
        m_console.display(this.getFullName(),"Euh... je ne m'en souviens pas...", false);
    }
    
    public void BeDisculpated(){
        this.setFindedInnocent(true);
        String text = "Vous avez choisi de disculper " + this.getFullName();
        m_console.display(text, false).execContinue();
    }
    
    public void BeArrested(){
        //Si c'est coupable = bonne fin => enquête réussi
        //Sinon => il y a eu de nouveaux meurtres => vous êtes virés !
        if (this instanceof Murderer) {
            m_console.display("Bravo, vous avez réussi à trouver le coupable",false).execContinue();
            ((Murderer) this).confess();//cast pour utiliser la méthode
        }
        else {
            //perso clame son innocence lors de son proces...
            m_console.display("Votre supérieur","Il y a eu de nouveaux meurtres ! Vous êtes renvoyé !", false).execContinue();
        }
    }
}
