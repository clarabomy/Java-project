
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
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
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

    /** 
     * Constructor of the class
     * @param fullName              explications
     * @param sex                   explications
     * @param age                   explications
     * @param stressLevel           explications
     * @param cooperationLevel      explications
     * @param look                  explications
     * @param physicalAspect        explications
     * @param consideredInnocent    explications
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
     * Getter of the class
     * @return isConsideredInnocent explications
     */ 
    public boolean isConsideredInnocent() {
        return m_consideredInnocent;
    }

    /** 
     * Getter of the class
     * @return stress   explications
     */ 
    public int getStress() {
        return m_stress;
    }
    
    /** 
     * Getter of the class
     * @return cooperation  explications
     */ 
    public int getCooperation() {
        return m_cooperation;
    }

    /** 
     * Getter of the class
     * @return look explications
     */ 
    public String getLook() {
        return m_look;
    }

    /** 
     * Getter of the class
     * @return aspect   explications
     */ 
    public String getAspect() {
        return m_physicalAspect;
    }

    /** 
     * Getter of the class
     * @return heard    explications
     */ 
    public Deposition getHeard() {
        return m_heardTestimony;
    }

    /** 
     * Getter of the class
     * @return seen     explications
     */ 
    public Deposition getSeen() {
        return m_seenTestimony;
    }

    /** 
     * Getter of the class
     * @return alibi    explications
     */ 
    public Deposition getAlibi() {
        return m_alibi;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    @Override
    public void presentCharacter() {
        getConsole().display(m_fullName, "Bonjour, vous avez demandé à me voir ?").execContinue("Lui présenter une chaise");
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
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
     * Utilité / fonctionnement de la méthode
     * @param player    explications
     */ 
    public void beInterrogated(Investigator player) {
        boolean previousMenu = false;
        
        do {
            String[] choices = {"Que faisiez-vous pendant le crime?", "Avez-vous vu ou entendu quelque chose de suspect?", "Vous pouvez partir."};
            int choix = getConsole().display(getGenderJob(), "Dites moi...", choices).execChoice();
            
            if (choix != 3) {
                //inspecteur utilise intelligence et manipulation pour essayer de récupérer des infos (jet affiché)
                getConsole().execContinue("Vous lancez vos dés");
                int[] stats = {player.getIntelligence(), player.getManipulation()};
                String[] category = {"d'intelligence", "de manipulation"};
                rollMultiDice(stats , category, true);

                //applique le choix
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
     * Utilité / fonctionnement de la méthode
     */ 
    abstract void giveAlibi();
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    abstract void giveTestimony();
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    protected void textNoSpeak() {
        getConsole().display(getGenderJob(), "Le suspect refuse de parler.");
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    protected void textLawyer() {
        getConsole().display(m_fullName, "Je n'ai rien à vous dire ! Je ne parlerai qu'en présence d'un avocat !");
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    protected void textForget() {
        getConsole().display(m_fullName,"Euh... je ne m'en souviens plus...");
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    public void beDisculpated(){
        m_consideredInnocent = true;
        String text = "Vous avez disculpé " + m_fullName + ". Pour le moment, rien ne prouve qu'" + (m_sex == Sex.FEMME? "elle" : "il") + " est coupable.";
        getConsole().display(text).execContinue("Vous laissez repartir " + m_fullName);
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    public void beArrested(){
        if (this instanceof Murderer) {
            getConsole().display("Bravo, vous avez démasqué le coupable !");
            ((Murderer) this).confess();//cast pour utiliser la méthode
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
