
package project.game.character.suspect;

import project.game.character.DiceResult;
import project.game.character.Investigator;
import project.game.character.LiveCharacter;
import project.game.character.Sex;


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
    public Suspect(String name, String surname, Sex sex, int age, int stressLevel, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef) {
        super(name, surname, sex, age);
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
    
   
    /*$$ METHODS $$*/
    abstract void giveAlibi();
    abstract void giveTestimony();
    
    @Override
    public void displayStats() {
        //Affiche les niveaux de stress du suspect
        String name = new StringBuilder("Nom, prénom : ").append(this.getFullName()).toString();
        String sex = new StringBuilder("Sexe : ").append(this.m_sex.toString()).toString();
        String age = new StringBuilder("Age : ").append(this.m_age).append(" ans").toString();
        String condition = new StringBuilder("Etat : ").append(this.m_stress > 50? "stressé" : "détendu").toString();
        String physicalDescript = new StringBuilder("Description physique : ").append(this.getPhysicalAspect()).append(", ").append(this.getLook()).toString();
        
        m_console.display(name, true);
        m_console.display(sex, true);
        m_console.display(age, true);
        m_console.display(condition, true);
        m_console.display(physicalDescript, false).execContinue();
        
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        //Affiche la description littéraire de qui il est (nom, sexe, age) + description physique (look, physicalAspect)
        String text = new StringBuilder("Bonjour, je m'appelle ").append(this.getFullName()).append(". J'ai ").append(this.m_age).append(" ans. Vous vouliez me voir ?").toString();
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
        
        String[] choices = {"Que faisiez vous pendant le crime?", "Avez vous vu ou entendu quelque chose?"};
        int choix = m_console.display("Enquêteur", "Vous voilà au poste, dites moi...", choices, false).execSingleChoice();
        
        player.InvestigatorDices();//inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché)
        switch (choix) {
            case 0:
                this.giveAlibi();
                break;
            case 1:
                this.giveTestimony();
                break;
        }
    }//end void BeInterrogated
    
    
    public void BeDisculpated(){
        //on modifie findedInnocent + vous avez décidé de disculpté ... 
        this.setFindedInnocent(true);
        String text = new StringBuilder("Vous avez choisi de disculper ").append(this.getFullName()).toString();
        m_console.display(text, false).execContinue();
    }//end void BeDisculpated
    
    
    public void BeArrested(){
        //Si c'est coupable = bonne fin => enquête réussi
        //Sinon => il y a eu de nouveaux meurtres => vous êtes virés !
        if (this instanceof Murderer) m_console.display("Bravo, vous avez réussi à trouver le coupable",false);
        else m_console.display("Votre supérieur","Il y a eu de nouveaux meurtres ! Vous êtes renvoyé !", false);    
        m_console.execContinue();
    }//end void BeArrested
}
