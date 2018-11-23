
package project.game.character;

import java.util.ArrayList;
import static project.game.Game.convertArrayList;
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
    public Investigator(String fullName, Sex gender, int manipulationLevel, int intelligenceLevel, String trueMurderer, String trueVictim, String trueWeapon, String trueMobile) {
        super(fullName, gender, 30);
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        
        this.m_trueMurderer = trueMurderer;
        this.m_trueVictim = trueVictim;
        this.m_trueWeapon = trueWeapon;
        this.m_trueMobile = trueMobile;
        
        //this.constructProgress(null, null, null, null);
    }
    
    //chargement partie
    public Investigator(String fullName, Sex gender, int manipulationLevel, int intelligenceLevel, String trueMurderer, String trueVictim, String trueWeapon, String trueMobile, ArrayList<Clue> clueList, String[] suppositions) {
        super(fullName, gender, 30);
        m_clueList.addAll(clueList);// équivaut à m_clueList = clueList;
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        
        this.m_trueMurderer = trueMurderer;
        this.m_trueVictim = trueVictim;
        this.m_trueWeapon = trueWeapon;
        this.m_trueMobile = trueMobile;
        /*
        if (suppositions == null) {
            this.constructProgress(null, null, null, null);
        }
        else {
            this.constructProgress(suppositions[0], suppositions[1], suppositions[2], suppositions[3]);
        }
        */
    }

    
    /*$$ GETTERS & SETTERS $$*/
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
        String text = "Vous êtes " + (m_sex.equals(Sex.FEMME)? "une enquêtrice" : "un enquêteur") + " de talent!";
        getConsole().display(text, false).execContinue(null);    
    }
    
    @Override
    public void displayStats() {
        //afficher niveau manipulation, intelligence et popularité;
        String intelligence = "Votre niveau d'intelligence : " + this.m_intelligence;
        String manipulation = "Votre niveau de manipulation : " + this.m_manipulation;
        getConsole().display(intelligence, false);
        getConsole().display(manipulation, false).execContinue(null);
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
                String[] listSuspects = convertArrayList(suspectsNameList());
                designed = getConsole().display("Le coupable serait le suspect...", listSuspects, false).execChoice();
                m_supposedMurderer = listSuspects[designed];//met à jour le suspect
                break;
            case 2:
                //liste des victimes à l'initialisation
                String[] morgue = convertArrayList(getVictimList());
                designed = getConsole().display("La victime est...", morgue, false).execChoice();
                m_supposedWeapon = morgue[designed];
                break;
            case 3:
                //liste des armes à l'initialisation
                String[] arsenal = convertArrayList(getWeaponList());
                designed = getConsole().display("L'arme du crime est...", arsenal, false).execChoice();
                m_supposedWeapon = arsenal[designed];
                break;
            case 4:
                //liste des mobiles à l'initialisation
                String[] possibility = convertArrayList(getMobileList());
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
                //m_clueList.get(i).display();
                getConsole().display("Indice " + (i+1) + " : " + m_clueList.get(i).getContent(), true);
            }
        }
        getConsole().execContinue(null);
        return this;
    }
    
    public void displayProgress(){
        String progress = "<1> à tué <2> avec <3> pour cause de <4>";
        progress = progress.replace("<1>", m_supposedMurderer != null? m_supposedMurderer : "<meurtrier>")
                            .replace("<2>", m_supposedVictim  != null? m_supposedVictim   : "<victime>")
                            .replace("<3>", m_supposedWeapon  != null? m_supposedWeapon   : "<arme>")
                            .replace("<4>", m_supposedMobile  != null? m_supposedMobile   : "<mobile>");
        getConsole().display(progress, true);
    }
    
    public void checkContradiction(){//sur progression ou sur listClues?
        int nbErrors = 0, nbNull = 0;
        
        if (m_supposedMurderer == null) {
            nbNull++;
        }
        else if (!m_supposedMurderer.equals(m_trueMurderer)) {
            nbErrors++;
        }
        
        if (m_supposedVictim == null) {
            nbNull++;
        }
        else if (!m_supposedVictim.equals(m_trueVictim)) {
            nbErrors++;
        }
        
        if (m_supposedWeapon == null) {
            nbNull++;
        }
        else if (!m_supposedWeapon.equals(m_trueWeapon)) {
            nbErrors++;
        }
        
        if (m_supposedMobile == null) {
            nbNull++;
        }
        else if (!m_supposedMobile.equals(m_trueMobile)) {
            nbErrors++;
        }
        
        String text = "";
        if (nbNull == 4) {
            text = "Je n'ai pas encore réfléchi à ce qui s'est passé.";
        }
        else {
            switch(rollDice(m_intelligence, "intelligence", true)) {
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
            text += " failles dans mon raisonnement." + (nbNull > 0? " Il reste aussi " + nbNull + " paramètre" + (nbNull > 1? "s" : "") + " à déterminer." : "");
        }
        getConsole().display("Enquêteur", text, false).execContinue(null);
    }
}
