
package javaspector.game.character.suspect;

import javaspector.game.Difficulty;
import static javaspector.game.Game.getConsole;
import static javaspector.game.Game.getLevelChoice;
import static javaspector.game.Game.setEndedGame;
import javaspector.game.character.Investigator;
import javaspector.game.character.LiveCharacter;
import javaspector.game.character.Sex;
import javaspector.game.investigation.Deposition;
import static javaspector.game.investigation.Investigation.getGenderJob;

/**
 *
 * Contains the methods and attributes of the suspect
 * @author Clara BOMY
 */ 
public abstract class Suspect extends LiveCharacter {
    protected int m_difficulty; //difficulty level of the game
    protected int m_stress; 
    protected int m_cooperation; 
    protected String m_look; 
    protected String m_physicalAspect;
    protected boolean m_consideredInnocent;
    protected Deposition m_heardTestimony;
    protected Deposition m_seenTestimony;
    protected Deposition m_alibi;

    /** 
     * Constructor of the class
     * @param fullName              full name of the suspect
     * @param sex                   sex of the suspect
     * @param age                   age of the suspect
     * @param stressLevel           stress level of the suspect
     * @param cooperationLevel      cooperation level of the suspect
     * @param look                  look of the suspect
     * @param physicalAspect        physical aspect of the suspect
     * @param consideredInnocent    if the suspect is considered innocent by the investigator
     */ 
    public Suspect(String fullName, Sex sex, int age, int stressLevel, int cooperationLevel, String look, String physicalAspect, boolean consideredInnocent) {
        super(fullName, sex, age);
        m_stress = stressLevel;
        m_cooperation = cooperationLevel;
        m_look = look;
        m_physicalAspect = physicalAspect;
        m_consideredInnocent = consideredInnocent;
        m_difficulty = getLevelChoice() == Difficulty.SIMPLE? 0 : (getLevelChoice() == Difficulty.MEDIUM? 1 : 2);
    }
    
    /** 
     * Getter of the attribute isConsideredInnocent
     * @return isConsideredInnocent if the suspect is considered innocent by the investigator
     */ 
    public boolean isConsideredInnocent() {
        return m_consideredInnocent;
    }

    /** 
     * Getter of the stress level
     * @return stress   stress level of the suspect
     */ 
    public int getStress() {
        return m_stress;
    }
    
    /** 
     * Getter of the cooperation level
     * @return cooperation   cooperation level of the suspect
     */ 
    public int getCooperation() {
        return m_cooperation;
    }

    /** 
     * Getter of the look
     * @return look    look of the suspect
     */ 
    public String getLook() {
        return m_look;
    }

    /** 
     * Getter of the physical aspect
     * @return aspect   physical aspect of the suspect
     */ 
    public String getAspect() {
        return m_physicalAspect;
    }

    /** 
     * Getter of the heard testimony of the suspect
     * @return heard    heard testimony of the suspect
     */ 
    public Deposition getHeard() {
        return m_heardTestimony;
    }

    /** 
     * Getter of the heard testimony of the suspect
     * @return seen    seen testimony of the suspect
     */ 
    public Deposition getSeen() {
        return m_seenTestimony;
    }

    /** 
     * Getter of the alibi of the suspect
     * @return alibi    alibi of the suspect
     */ 
    public Deposition getAlibi() {
        return m_alibi;
    }
    
    /** 
     * Displays a presentation sentence
     */ 
    @Override
    public void presentCharacter() {
        getConsole().display(m_fullName, "Bonjour, vous avez demandé à me voir ?").execContinue("Lui présenter une chaise");
    }
    
    /** 
     * Displays the inforation sheet of the suspect
     * @return suspect  sends the reference to chain the calls
     */ 
    @Override
    public Suspect displayStats() {
        String part1 = "Nom, prénom           : " + m_fullName.split(" ")[1] + " " + m_fullName.split(" ")[0],
                part2 = "Sexe                  : " + m_sex.toString(),
                part3 = "Age                   : " + m_age + " ans",
                part4 = "Etat                  : " + (m_stress > 50? "stressé" : "détendu") + (m_sex == Sex.FEMME? "e" : ""),
                part5 = "Description physique  : " + m_physicalAspect + ", " + m_look;
        getConsole().display("Informations sur le suspect : ")
                    .display(part1 + "\n" + part2 + "\n" + part3 + "\n" + part4 + "\n" + part5)
                    .execContinue("Vous reposez le rapport");
        
        return this;
    }
    
    /** 
     * To interrogate a suspect
     * @param player    investigator who interrogates the suspect
     */ 
    public void beInterrogated(Investigator player) {
        boolean previousMenu = false;
        
        do {
            String[] choices = {"Que faisiez-vous pendant le crime?", "Avez-vous vu ou entendu quelque chose de suspect?", "Vous pouvez partir."};
            int choix = getConsole().display(getGenderJob(), "Dites moi...", choices).execChoice();
            
            if (choix != 3) {
                // inspector uses intelligence and manipulation to try to retrieve info 
                getConsole().execContinue("Vous lancez vos dés");
                int[] stats = {player.getIntelligence(), player.getManipulation()};
                String[] category = {"d'intelligence", "de manipulation"};
                rollMultiDice(stats , category, true);

                // apply the choice
                getConsole().execContinue(m_fullName + " lance ses dés");
                switch (choix) {
                    case 1:
                        giveAlibi();
                        break;
                    case 2:
                        giveTestimony();
                        break;
                }
                getConsole().execContinue("Vous prenez des notes");
            }
            else {
                previousMenu = true;
            }
        } while (!previousMenu);
        getConsole().execContinue("Vous raccompagnez " + m_fullName + " vers la sortie");
    }
    
    /** 
     * Determines the alibi to display based on the results of rolls of dice
     */ 
    abstract void giveAlibi();
    
    /** 
     * Determines the testimony to display based on the results of rolls of dice
     */ 
    abstract void giveTestimony();
    
    /** 
     * Displays a sentence when the suspect doesn't want to talk
     */ 
    protected void textNoSpeak() {
        getConsole().display(getGenderJob(), "Le suspect refuse de parler.");
    }
    
    /** 
     * Displays a sentence when the suspect is too stressed
     */ 
    protected void textLawyer() {
        getConsole().display(m_fullName, "Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !");
    }
    
    /** 
     * Displays a sentence when the suspect doesn't want to cooperate
     */ 
    protected void textForget() {
        getConsole().display(m_fullName,"Euh... je ne m'en souviens plus...");
    }
    
    /** 
     * To disculp a suspect
     */ 
    public void beDisculpated(){
        m_consideredInnocent = true;
        String text = "Vous avez disculpé " + m_fullName + ". Pour le moment, rien ne prouve qu'" + (m_sex == Sex.FEMME? "elle" : "il") + " est coupable.";
        getConsole().display(text).execContinue("Vous laissez repartir " + m_fullName);
    }
    
    /** 
     * To arrest a suspect
     */ 
    public void beArrested(){
        if (this instanceof Murderer) {
            getConsole().display("Bravo, vous avez démasqué le coupable !");
            ((Murderer) this).confess();//cast to use the method
            getConsole().execContinue("Deux policiers débarquent alors pour arrêter le meurtrier")
                        .execContinue("Votre chef vous félicite pour avoir résolu cette affaire")
                        .execContinue("Vous rassemblez le contenu de l'enquête dans une boîte, que vous descendez ensuite aux archives")
                        .execContinue("Puis, enfin, vous rentrez chez vous, avec la satisfaction du travail accompli");
        }
        else {
            if (this instanceof CrimePartner) {
                getConsole().display("Vous avez envoyé " + (m_sex == Sex.HOMME? "le" : "la") + " partenaire du criminel en prison.")
                            .execContinue((m_sex == Sex.HOMME? "Il" : "Elle") + " a refusé de dire qui était le coupable");
            }
            else {
                getConsole().display("Vous avez envoyé un innocent en prison.");
            }
            getConsole().execContinue("Les crimes ont donc continués")
                        .execContinue("J'ai été renvoyé par mon supérieur pour incompétence")
                        .execContinue("Me revoilà donc sur le marché du travail... Toujours à la recherche d'une enquête à résoudre");
        }
        getConsole().execContinue("Merci d'avoir joué à JavaSpector ! ");
        setEndedGame();
    }
}
