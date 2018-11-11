
package project.game.investigation.suspect;

import project.game.investigation.DiceResult;
import project.game.investigation.Investigator;
import project.game.investigation.LiveCharacter;
import project.game.investigation.Sex;


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
    abstract void giveAlibi(DiceResult throwPlayer);
    abstract void giveTestimony(DiceResult throwPlayer);
    
    @Override
    public void displayStats() {
        //Affiche les niveaux de stress du suspect
        System.out.printf("Niveau de stress de %s %s : %d\n", this.getSurname().substring(0,1).toUpperCase() + this.getSurname().substring(1).toLowerCase(), this.getName().substring(0,1).toUpperCase() + this.getName().substring(1).toLowerCase(), this.getStressLevel());
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter(){
        //Affiche la description littéraire de qui il est (nom, sexe, age) + description physique (look, physicalAspect)
        System.out.printf("Voici %s %s (%s, %d ans)...\n", this.getSurname().substring(0,1).toUpperCase() + this.getSurname().substring(1).toLowerCase(), this.getName().substring(0,1).toUpperCase() + this.getName().substring(1).toLowerCase(), this.getSex().toString(), this.getAge());
        System.out.printf("Aspect physique :%s\nLook : %s\n", this.getPhysicalAspect(), this.getLook());
    }//end void presentCharacter
    
    
    public void BeInterrogated(Investigator player) {
        //Présentation du personnage presenterPerso() = description littéraire de qui il est
        //AfficherInfos() = stats du perso + description physique
        
        //Menu => 2 fonctions
            //Connaître alibi -> suspect lance le dé pour niveau de coopération => donne son alibi ou non    
            //Obtenir témoignage -> avez-vous vu qqch ? Lancer le dé pour voir le niveau de stress 
                //si lancer réussi : afficher ce qu'il sait, a vu (passer l'indice de non trouvé à trouvé) => témoignages
                //si lancer échoué : afficher qu'il ne coopère pas (indice non trouvé)
        
        String[] choices = {"que faisiez vous pendant le crime?", "avez vous vu ou entendu quelque chose?"};
        int choix = m_console.display("Inspecteur", "Vous voilà au poste, dites moi...", choices, false).execSingleChoice();
        
        DiceResult throwPlayer = player.InvestigatorDices();//inspecteur utilise intelligence et manipulation pour essayer de récupérer les infos (jet affiché)
        switch (choix) {
            case 0:
                this.giveAlibi(throwPlayer);
                break;
            case 1:
                this.giveTestimony(throwPlayer);
                break;
        }
    }//end void BeInterrogated
    
    
    public void BeDisculpated(){
        this.setFindedInnocent(true);
        System.out.printf("Vous avez choisi de disculpté %s %s...", this.getSurname().substring(0,1).toUpperCase() + this.getSurname().substring(1).toLowerCase(), this.getName().substring(0,1).toUpperCase() + this.getName().substring(1).toLowerCase());
    //on modifie findedInnocent + vous avez décidé de disculpté ... 
    }//end void BeDisculpated
    
    
    public void BeArrested(){
        //Si c'est coupable = bonne fin => enquête réussi
        //Sinon => il y a eu de nouveaux meurtres => vous êtes virés !
        if (this instanceof Murderer) System.out.println("Bravo, vous avez réussi à trouver le coupable !");
        else System.out.println("Il y a eu de nouveaux meurtres ! Vous êtes renvoyés !");    
    }//end void BeArrested
}
