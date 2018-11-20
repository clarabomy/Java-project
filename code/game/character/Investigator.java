
package project.game.character;

import java.util.ArrayList;
import static project.game.Game.getConsole;
import static project.game.Game.getMobileList;
import static project.game.Game.getVictimList;
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
    protected String m_progress;//Phrase type remplie à l'initialisation du nouvelle partie (phrase à trous) avec ce qu'a déterminé le joueur
    protected String m_supposedMurderer;
    protected String m_supposedVictim;
    protected String m_supposedWeapon;
    protected String m_supposedMobile;
    protected String m_trueMurderer;
    protected String m_trueVictim;
    protected String m_trueWeapon;
    protected String m_trueMobile;
    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie
    public Investigator(int manipulationLevel, int intelligenceLevel, String trueMurderer, String trueVictim, String trueWeapon, String trueMobile) {
        super("", "", null, 30);//pas d'impact
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        
        this.m_trueMurderer = trueMurderer;
        this.m_trueVictim = trueVictim;
        this.m_trueWeapon = trueWeapon;
        this.m_trueMobile = trueMobile;
        
        this.constructProgress(null, null, null, null);
    }
    
    //chargement partie
    public Investigator(int manipulationLevel, int intelligenceLevel, String trueMurderer, String trueVictim, String trueWeapon, String trueMobile, ArrayList<Clue> clueList, String[] suppositions) {
        super("", "", null, 30);//pas d'impact
        m_clueList.addAll(clueList);// équivaut à m_clueList = clueList;
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        
        this.m_trueMurderer = trueMurderer;
        this.m_trueVictim = trueVictim;
        this.m_trueWeapon = trueWeapon;
        this.m_trueMobile = trueMobile;
        
        if (suppositions == null) {
            this.constructProgress(null, null, null, null);
        }
        else {
            this.constructProgress(suppositions[0], suppositions[1], suppositions[2], suppositions[3]);
        }
    }

    private void constructProgress(String murderer, String victim, String weapon, String mobile) {
        m_supposedMurderer = murderer != null? murderer : "<meurtrier>";
        m_supposedVictim = victim != null? victim : "<victime>";
        m_supposedWeapon = weapon != null? weapon : "<arme>";
        m_supposedMobile = mobile != null? mobile : "<mobile>";
        
        m_progress = m_supposedMurderer + " à tué " + m_supposedVictim + " avec " + m_supposedWeapon + " pour cause de " + m_supposedMobile;
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

    public String getSupposedMurderer() {
        return m_supposedMurderer;
    }

    public String getSupposedVictim() {
        return m_supposedVictim;
    }

    public String getSupposedWeapon() {
        return m_supposedWeapon;
    }

    public String getSupposedMobile() {
        return m_supposedMobile;
    }
    
    
    
    /*$$ METHODS $$*/    
    @Override
    public void presentCharacter() {
        String text = "Vous êtes " + (m_sex.equals(Sex.FEMME)? ", une enquêtrice" : ", un enquêteur") + " de talent!";
        getConsole().display(text, false).execContinue();    
    }//end void presentCharacter
    
    @Override
    public void displayStats() {
        //afficher niveau manipulation, intelligence et popularité;
        String intelligence = "Votre niveau d'intelligence : " + this.m_intelligence;
        String manipulation = "Votre niveau de manipulation : " + this.m_manipulation;
        getConsole().display(intelligence, false);
        getConsole().display(manipulation, false).execContinue();
    }
    
    public void crossClue(){
        /*
        joueur voit tous les indices et témoignages //consultClues()
        joueur voit phrase de display progress //displayProgress()
        joueur peut remplir phrase de display progress et peut se tromper donc remodifier
            joueur choisit trou à remplir
            joueur choisit une des possibilités
        */
        this.consultClues().displayProgress();//affiche indices puis progression
        int designed;
        String choices[] = {m_supposedMurderer, m_supposedVictim, m_supposedWeapon, m_supposedMobile};
        switch (getConsole().display("Quel champ voulez-vous changer ?", choices, false).execChoice()) {
            case 1:
                String[] listSuspects = (String[]) suspectsNameList().toArray();
                designed = getConsole().display("Le coupable serait le suspect...", listSuspects, false).execChoice();
                m_supposedMurderer = listSuspects[designed];//met à jour le suspect
                break;
            case 2:
                //liste des victimes à l'initialisation
                String[] morgue = (String[]) getVictimList().toArray();
                designed = getConsole().display("La victime est...", morgue, false).execChoice();
                m_supposedWeapon = morgue[designed];
                break;
            case 3:
                //liste des armes à l'initialisation
                String[] arsenal = (String[]) getWeaponList().toArray();
                designed = getConsole().display("L'arme du crime est...", arsenal, false).execChoice();
                m_supposedWeapon = arsenal[designed];
                break;
            case 4:
                //liste des mobiles à l'initialisation
                String[] possibility = (String[]) getMobileList().toArray();
                designed = getConsole().display("Le mobile est...", possibility, false).execChoice();
                m_supposedWeapon = possibility[designed];
                break;
        }
    }
    
    public Investigator consultClues(){
        //affiche les indices ayant été trouvés
        if (m_clueList.isEmpty()) {
            getConsole().display("Vous n'avez pas encore trouvé d'indices...", false);
        }
        else {
            for (int i = 0; i < m_clueList.size(); i++) {
                getConsole().display("Indice " + (i+1) + " : " + m_clueList.get(i).getContent(), true);
            }
        }
        getConsole().execContinue();
        return this;
    }
    
    public void displayProgress(){
        String progress = m_progress.replace("<Murderer>", m_supposedMurderer)
                                    .replace("<Victim>", m_supposedVictim)
                                    .replace("<Weapon>", m_supposedWeapon)
                                    .replace("<Mobile>", m_supposedMobile);
        getConsole().display(progress, true);
    }
    
    public void checkContradiction(){//sur progression ou sur listClues?
        int nbErrors = 0;
        if (this.m_supposedMurderer.equals(this.m_trueMurderer)) {
            nbErrors++;
        }
        if (this.m_supposedVictim.equals(this.m_trueVictim)) {
            nbErrors++;
        }
        if (this.m_supposedWeapon.equals(this.m_trueWeapon)) {
            nbErrors++;
        }
        if (this.m_supposedMobile.equals(this.m_trueMobile)) {
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
        getConsole().display("Enquêteur", text + " failles dans mon raisonnement.", false).execContinue();
    }
}
