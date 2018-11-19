
package project.game.character;

import java.util.ArrayList;
import static project.game.Game.getMobile;
import static project.game.Game.getMobileList;
import static project.game.Game.getMurderer;
import static project.game.Game.getVictim;
import static project.game.Game.getVictimList;
import static project.game.Game.getWeapon;
import static project.game.Game.getWeaponList;
import project.game.investigation.Clue;
import static project.game.investigation.Investigation.suspectsNameList;

/**
 *
 * @author ISEN
 */
public class Investigator extends LiveCharacter {
    protected int m_manipulation;
    protected int m_intelligence;
    protected String m_progress;//"<meurtrier> a tué <victime> avec <arme> pour cause de <mobile>"//Phrase type remplie à l'initialisation du nouvelle partie (phrase à troue) avec ce qu'a déterminé le joueur
    protected String m_progressMurderer;
    protected String m_progressVictim;
    protected String m_progressWeapon;
    protected String m_progressMobile;
    
    /*$$ CONSTRUCTOR $$*/
    public Investigator(String name, String surname, Sex sex, int age, int manipulationLevel, int intelligenceLevel, ArrayList <Clue> clueList, String progress) {
        super(name, surname, sex, age, clueList);
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        this.m_progress = progress;
        
        m_progressMurderer = "<Meurtrier>";
        m_progressVictim = "<Victime>";
        m_progressWeapon = "<Arme>";
        m_progressMobile = "<Mobile>";
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getProgress() {
        return m_progress;
    }
    
    public int getIntelligence() {
        return m_intelligence;
    }
    
    public int getManipulation() {
        return m_manipulation;
    }
    
    
    /*$$ METHODS $$*/    
    @Override
    public void presentCharacter() {
        String text = "Vous êtes " + this.getFullName() + (m_sex.equals(Sex.FEMME)? ", une enquêtrice" : ", un enquêteur") + " de talent!";
        m_console.display(text, false).execContinue();    
    }//end void presentCharacter
    
    @Override
    public void displayStats() {
        //afficher niveau manipulation, intelligence et popularité;
        String intelligence = "Votre niveau d'intelligence : " + this.m_intelligence;
        String manipulation = "Votre niveau de manipulation : " + this.m_manipulation;
        m_console.display(intelligence, true);
        m_console.display(manipulation, false).execContinue();
    }//end void displayInfos
    
    public void crossClue(){
        /*
        joueur voit tous les indices et témoignages //consultClues()
        joueur voit phrase de display progress //displayProgress()
        joueur peut remplir phrase de display progress et peut se tromper donc remodifier
            joueur choisit trou à remplir
            joueur choisit une des possibilités
        */
        this.consultClues().displayProgress();//affiche indices puis progression
        int designed = 0;
        String choices[] = {m_progressMurderer, m_progressVictim, m_progressWeapon, m_progressMobile};
        switch (m_console.display("Quel champ voulez-vous changer ?", choices, false).execSingleChoice()) {
            case 1:
                String[] listSuspects = (String[]) suspectsNameList().toArray();
                designed = m_console.display("Le coupable serait le suspect...", listSuspects, false).execSingleChoice();
                m_progressMurderer = listSuspects[designed];//met à jour le suspect
                break;
            case 2:
                //liste des victimes à l'initialisation
                String[] morgue = (String[]) getVictimList().toArray();
                designed = m_console.display("La victime est...", morgue, false).execSingleChoice();
                m_progressWeapon = morgue[designed];
                break;
            case 3:
                //liste des armes à l'initialisation
                String[] arsenal = (String[]) getWeaponList().toArray();
                designed = m_console.display("L'arme du crime est...", arsenal, false).execSingleChoice();
                m_progressWeapon = arsenal[designed];
                break;
            case 4:
                //liste des mobiles à l'initialisation
                String[] possibility = (String[]) getMobileList().toArray();
                designed = m_console.display("Le mobile est...", possibility, false).execSingleChoice();
                m_progressWeapon = possibility[designed];
                break;
        }
    }
    
    public Investigator consultClues(){
        //affiche les indices ayant été trouvés
        boolean none = true;
        for (int i = 0; i < m_clueList.size(); i++) {
            if (m_clueList.get(i).isFounded()) {
                m_console.display("Indice " + (i+1) + " : " + m_clueList.get(i).getContent(), true);
                none = false;
            }
        }
        if (none) {
            m_console.display("Vous n'avez pas encore trouvé d'indices...", false);
        }
        m_console.execContinue();
        return this;
    }//end void lookForClues
    
    public void displayProgress(){
        String progress = m_progress.replace("<Murderer>", m_progressMurderer)
                                    .replace("<Victim>", m_progressVictim)
                                    .replace("<Weapon>", m_progressWeapon)
                                    .replace("<Mobile>", m_progressMobile);
        m_console.display(progress, true);
    }
    
    public void checkContradiction(){
        int nbErrors = 0;
        if (this.m_progressMurderer.equals(getMurderer())) {
            nbErrors++;
        }
        if (this.m_progressVictim.equals(getVictim())) {
            nbErrors++;
        }
        if (this.m_progressWeapon.equals(getWeapon())) {
            nbErrors++;
        }
        if (this.m_progressMobile.equals(getMobile())) {
            nbErrors++;
        }
        
        String text = "";
        switch(rollDice(this.m_intelligence, "intelligence", true)) {
            case CRITIC_SUCCESS:
                text = nbErrors == 0? "Il y a " + nbErrors : "Il n'y a pas de";
                break;
            case SUCCESS:
                text = nbErrors == 0? "Il y a des" : "Il n'y a pas de";
                break;
            case FAILURE:
                text = "Mmmmh... Je me demande s'il y a des";
                break;
            case CRITIC_FAILURE:
                text = "Je pense qu'il y a " + (int) (Math.random() * 4);
                break;
        }
        this.m_console.display("Enquêteur", text + " failles dans mon raisonnement.", false).execContinue();
    }
}
