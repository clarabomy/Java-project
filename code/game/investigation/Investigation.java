
package project.game.investigation;

import java.util.ArrayList;
import static project.game.Game.getConsole;
import project.game.character.Investigator;
import project.game.character.Victim;
import project.game.character.suspect.Suspect;




/**
 *
 * @author ISEN
 */
public class Investigation {
    protected Investigator m_player;
    protected static ArrayList <Suspect> m_suspectsList;
    protected Victim m_victim;
    protected InvestElement m_crimeWeapon;
    protected InvestElement m_crimeScene;

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Investigation(Investigator player, ArrayList <Suspect> suspectsList, Victim corpse, InvestElement weapon, InvestElement scene) {
        this.m_player = player;
        Investigation.m_suspectsList = suspectsList;
        this.m_victim = corpse;
        m_crimeWeapon = weapon;
        m_crimeScene = scene;
    }
    
    
    /*$ GETTERS & SETTERS $*/
    public static ArrayList <String> suspectsNameList() {
        ArrayList <String> listName = new ArrayList();
        for (Suspect currentSuspect : m_suspectsList) {//parcours tout m_suspectsList en mettant élément courant dans currentSuspect
            listName.add(currentSuspect.getFullName());
        }
        return listName;
    }
    
    public Investigator getInvestigator() {
        return m_player;
    }
    
    public ArrayList <Suspect> getSuspectsList() {
        return m_suspectsList;
    }
    
    public Victim getVictim() {
        return m_victim;
    }
    
    public InvestElement getCrimeWeapon() {
        return m_crimeWeapon;
    }
    
    public InvestElement getCrimeScene() {
        return m_crimeScene;
    }
    
    
    /*$$ METHODS $$*/
    public void investigationMenu() {//menu général de la partie
        boolean previousMenu = false;
        String[] choicesList = {"Consulter mes indices.",
                                "Passer en revue les éléments de l'enquête.",
                                "M'occuper des suspects.\n", 
                                "Aller voir mon supérieur."}; //menu du jeu
        
        do {
            switch (getConsole().clean().display("Enquêteur", "Aujourd'hui, je vais...", choicesList, false).execChoice()) {
                case 1:
                    cluesMenu();
                    break;
                case 2:
                    elementsMenu();
                    break;
                case 3:
                    suspectsMenu();
                    break;
                case 4:
                    previousMenu = true;
                    break;
            }
        } while (!previousMenu);
    }
    
    public void cluesMenu() {
        boolean previousMenu = false;
        String[] choicesList = {"Chercher des incohérences.", 
                                "Relier les indices.\n", 
                                "S'occuper d'autre chose."};  //menu principal
        
        do {
            getConsole().clean();
            m_player.consultClues();
            switch(getConsole().display("Enquêteur", "C'est parti pour...", choicesList, false).execChoice()) {
                case 1: 
                    m_player.checkContradiction();
                    break;
                case 2:
                    m_player.crossClue();
                    break;
                case 3:
                    previousMenu = true;
                    break;
            }
        } while (!previousMenu);
    }
    
    public void elementsMenu() {
        boolean previousMenu = false;
        String[] choicesList = {"Sur la victime (autopsie).", 
                                "Sur l'arme du crime (analyse).", 
                                "Sur la scène du crime (fouille).\n",
                                "Ailleurs."};  //menu principal
        
        do {
            switch(getConsole().clean().display("Enquêteur", "Voyons voir ce qu'on trouve...", choicesList, false).execChoice()) {
                case 1: 
                    m_victim.analyse(m_player);
                    break;
                case 2:
                    m_crimeWeapon.analyse(m_player);
                    break;
                case 3:
                    m_crimeScene.analyse(m_player);
                    break;
                case 4:
                    previousMenu = true;
                    break;
            }
        } while (!previousMenu);
    }
    
    public void suspectsMenu() {
        boolean previousMenu = false;
        String[] choicesList = {"L'interroger.", 
                                "L'innocenter.", 
                                "L'arrêter (présumé coupable).\n",
                                "Hum. Je ne sais plus."};  //menu principal
        
        do {
            String[] suspectsList = new String[m_suspectsList.size()];
            for (int i = 0; i < m_suspectsList.size(); i++) {
                String text = m_suspectsList.get(i).getFullName();
                if (m_suspectsList.get(i).isConsideredInnocent()) {
                    text += " - potentiellement innocent";
                }
                suspectsList[i] = text;
            }
            
            int target = getConsole().clean().display("Enquêteur", "Je dois voir...", suspectsList, false).execChoice();
            switch(getConsole().display("Enquêteur", "Pour...", choicesList, false).execChoice()) {
                case 1: 
                    m_suspectsList.get(target).beInterrogated(m_player);
                    break;
                case 2:
                    m_suspectsList.get(target).beDisculpated();
                    break;
                case 3:
                    m_suspectsList.get(target).beArrested();
                    break;
                case 4:
                    previousMenu = true;
                    break;
            }
        } while (!previousMenu);
    }
}
