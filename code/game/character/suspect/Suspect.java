
package project.game.character.suspect;

import project.game.Difficulties;
import static project.game.Game.getConsole;
import static project.game.Game.getLevelChoice;
import project.game.character.Investigator;
import project.game.character.LiveCharacter;
import project.game.character.Sex;
import project.game.investigation.Deposition;


/**
 *
 * @author ISEN
 */
public abstract class Suspect extends LiveCharacter {
    protected int m_difficulty;
    protected int m_stress;
    protected int m_cooperation;
    protected String m_look;
    protected String m_physicalAspect;
    protected boolean m_consideredInnocent;
    protected Deposition m_heardTestimony;
    protected Deposition m_seenTestimony;
    protected Deposition m_alibi;

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Suspect(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean consideredInnocent) {
        super(fullName, sex, age);
        this.m_stress = stressLevel;
        this.m_cooperation = cooperationLevel;
        this.m_look = look;
        this.m_physicalAspect = physicalAspect;
        this.m_consideredInnocent = consideredInnocent;
        this.m_difficulty = getLevelChoice() == Difficulties.SIMPLE? 0 : (getLevelChoice() == Difficulties.MEDIUM? 1 : 2);
    }
    
    
    public boolean isConsideredInnocent() {
        return m_consideredInnocent;
    }

    public int getStress() {
        return m_stress;
    }
    
    public int getCooperation() {
        return m_cooperation;
    }

    public String getLook() {
        return m_look;
    }

    public String getAspect() {
        return m_physicalAspect;
    }

    public Deposition getHeard() {
        return m_heardTestimony;
    }

    public Deposition getSeen() {
        return m_seenTestimony;
    }

    public Deposition getAlibi() {
        return m_alibi;
    }
    
    
   
    /*$$ METHODS $$*/
    @Override
    public void presentCharacter() {
        //Affiche la description littéraire de qui il est (nom, sexe, age) + description physique (look, physicalAspect)
        String text = "Bonjour, je m'appelle " + this.m_fullName + ". J'ai " + this.m_age + " ans. Vous vouliez me voir ?";
        getConsole().display(this.m_fullName, text, false).execContinue(null);
    }
    
    @Override
    public void displayStats() {
        //Affiche les niveaux de stress du suspect
        String surname = m_fullName.substring(0, m_fullName.indexOf(" "));
        String name = m_fullName.substring(m_fullName.indexOf(" "), m_fullName.length());
        String identity = "Nom, prénom : " + name + " " + surname;
        String sex = "Sexe : " + this.m_sex.toString();
        String age = "Age : " + this.m_age + " ans";
        String condition = "Etat : " + (this.m_stress > 50? "stressé" : "détendu");
        String physicalDescript = "Description physique : " + this.m_physicalAspect + ", " + this.m_look;
        
        getConsole().display(identity, false)
                .display(sex, false)
                .display(age, false)
                .display(condition, false)
                .display(physicalDescript, false).execContinue(null);
        
    }
    
    public void beInterrogated(Investigator player) {
        String[] choices = {"Que faisiez-vous pendant le crime?", "Avez-vous vu ou entendu quelque chose?"};
        int choix = getConsole().display("Enquêteur", "Vous voilà au poste, dites moi...", choices, false).execChoice();
       
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer des infos (jet affiché)
        int[] stats = {player.getIntelligence(), player.getManipulation()};
        String[] category = {"d'intelligence", "de manipulation"};
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
        getConsole().execContinue(null);
    }
    
    abstract void giveAlibi();
    
    abstract void giveTestimony();
    
    protected void textNoSpeak() {
        getConsole().display("Enquêteur", "Le suspect refuse de parler.", true);
    }
    
    protected void textLawyer() {
        getConsole().display(this.m_fullName, "Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !", true);
    }
    
    protected void textForget() {
        getConsole().display(this.m_fullName,"Euh... je ne m'en souviens pas...", true);
    }
    
    public void beDisculpated(){
        this.m_consideredInnocent = true;
        String text = "Vous avez choisi de disculper " + this.m_fullName;
        getConsole().display(text, false).execContinue(null);
    }
    
    public void beArrested(){
        //Si c'est coupable = bonne fin => enquête réussi
        //Sinon => il y a eu de nouveaux meurtres => vous êtes virés !
        if (this instanceof Murderer) {
            getConsole().display("Bravo, vous avez réussi à trouver le coupable",false).execContinue(null);
            ((Murderer) this).confess();//cast pour utiliser la méthode
        }
        else {
            //perso clame son innocence lors de son proces...
            getConsole().display("Votre supérieur","Il y a eu de nouveaux meurtres ! Vous êtes renvoyé !", false);
            getConsole().display("GAME OVER", false).execContinue(null);
        }
    }
}
