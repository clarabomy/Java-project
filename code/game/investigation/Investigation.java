
package project.game.investigation;

import java.util.ArrayList;
import static project.game.Game.convertArrayList;
import static project.game.Game.getConsole;
import static project.game.Game.isEndedGame;
import project.game.character.Investigator;
import static project.game.character.Investigator.yourself;
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
        m_player = player;
        Investigation.m_suspectsList = suspectsList;
        m_victim = corpse;
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
        String[] choicesList = {"Revoir mon test d'aptitude\n",
                                "Passer en revue les éléments de l'enquête.",
                                "Consulter mes indices.",
                                "Appeler un suspect.\n", 
                                "Retourner au bureau."}; //menu du jeu
        
        do {
            switch (getConsole().clean().display(m_player.getFullName(), "Aujourd'hui, je vais...", choicesList).execChoice()) {
                case 1:
                    getConsole().clean();
                    m_player.displayStats();
                    break;
                case 2:
                    elementsMenu();
                    break;
                case 3:
                    getConsole().clean();
                    if (m_player.getClueList().isEmpty()) {
                        getConsole().display(yourself(), "Je n'ai pas encore le moindre indice...");
                    }
                    else {
                        m_player.consultClues();
                    }
                    getConsole().execContinue("Continuer");
                    break;
                case 4:
                    suspectsMenu();
                    if (isEndedGame()) {
                        previousMenu = true;
                    }
                    break;
                case 5:
                    previousMenu = true;
                    break;
            }
        } while (!previousMenu);
    }
    
    public void elementsMenu() {
        boolean previousMenu = false;
        String[] choicesList = {"Autopsier la victime.", 
                                "Analyser l'arme du crime.", 
                                "Fouiller la scène du crime.\n",
                                "Retourner à l'enquête."};  //menu principal
        
        do {
            switch(getConsole().clean().display(yourself(), "Voyons voir les éléments de l'enquête...", choicesList).execChoice()) {
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
            if (!previousMenu) {
                getConsole().execContinue("Vous relevez les indices");
            }
        } while (!previousMenu);
    }
    
    public void suspectsMenu() {
        getConsole().clean();
        boolean previousMenu = false;
        
        do {
            String[] suspectsList = new String[m_suspectsList.size() + 1];
            for (int i = 0; i < m_suspectsList.size(); i++) {
                String text = m_suspectsList.get(i).getFullName();
                if (m_suspectsList.get(i).isConsideredInnocent()) {
                    text += " - potentiellement innocent";
                }
                if (i == m_suspectsList.size() - 1) {
                    text += "\n";
                }
                suspectsList[i] = text;
            }
            suspectsList[m_suspectsList.size()] = "Annuler";
            
            int target = getConsole().clean().display(yourself(), "Je veux voir", suspectsList).execChoice() - 1;
            if (target == m_suspectsList.size()) {
                previousMenu = true;
            }
            else {
                m_suspectsList.get(target).displayStats().presentCharacter();
                
                ArrayList <String> choicesList = new ArrayList();
                choicesList.add("L'interroger.");
                if (!m_suspectsList.get(target).isConsideredInnocent()) {
                    choicesList.add("L'innocenter."); 
                }
                choicesList.add("L'arrêter.\n"); 
                choicesList.add("Retourner à l'enquête.");
                
                int choice = getConsole().display("Que voulez vous faire?", convertArrayList(choicesList)).execChoice();
                if (m_suspectsList.get(target).isConsideredInnocent() && choice >= 2) {
                    choice++;
                }
                switch(choice) {
                    case 1: 
                        m_suspectsList.get(target).beInterrogated(m_player);
                        break;
                    case 2:
                        m_suspectsList.get(target).beDisculpated();
                        break;
                    case 3:
                        m_suspectsList.get(target).beArrested();
                        if (isEndedGame()) {
                            previousMenu = true;
                        }
                        break;
                    case 4:
                        previousMenu = true;
                        break;
                }
            }
        } while (!previousMenu);
    }
}
