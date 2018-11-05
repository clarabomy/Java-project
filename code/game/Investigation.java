
package project.game;

import project.game.investigation.Investigator;
import project.game.investigation.Victim;
import project.game.investigation.InvestElement;
import project.game.investigation.suspect.Suspect;



/**
 *
 * @author ISEN
 */
public class Investigation {
    protected Investigator m_player;
    protected Suspect[] m_suspectsList;
    protected Victim m_corpse;
    protected InvestElement[] m_elements;
    protected UserInterface m_console;//provisoire => pas hérité de Game?

    
    /*$$ CONSTRUCTOR $$*/
    public Investigation(Investigator player, Suspect[] suspectsList, Victim corpse, InvestElement[] elements) {
        this.m_player = player;
        this.m_suspectsList = new Suspect[suspectsList.length];
        System.arraycopy(suspectsList, 0, this.m_suspectsList, 0, suspectsList.length);
        this.m_corpse = corpse;
        this.m_elements = new InvestElement[elements.length];
        System.arraycopy(elements, 0, this.m_elements, 0, elements.length);
    }
    
    
    /*$$ METHODS $$*/
    public void mainMenu() {//menu général de la partie
        boolean gameMenu = false;
        do {
            String[] choicesList = {"Passer en revue les éléments de l'enquête.", 
                                    "M'occuper des suspects.", 
                                    "Lister les indices.\n",
                                    "Voir mon supérieur."}; //menu du jeu
            switch (m_console.display("Aujourd'hui, je vais...", choicesList, false).execSingleChoice()) {//choix unique
                case 0:
                    elementsMenu();
                    break;
                case 1:
                    suspectsMenu();
                    break;
                case 2:
                    cluesMenu();
                    break;
                case 3:
                    gameMenu = true;
                    break;
            }
        } while (!gameMenu);//retours au menu principal (touche échap donne -1 => mécanisme implémentable?)
    }//end mainMenu
    
    
    public void elementsMenu() {
        for (int index = 0; index < m_elements.length; index++) System.out.print(new StringBuilder("Element ").append(index + 1).append(" : ").append(m_elements[index].display()).toString());
        
        String[] choicesList = {"Analyser l'arme du crime.", 
                                "Autopsier la victime.", 
                                "Chercher des indices dans la scène de crime.\n",
                                "Faire autre chose."};  //menu principal
        int action = m_console.display("C'est parti pour...", choicesList, false).execSingleChoice();
        if (action < choicesList.length) m_elements[action].analyse(m_player);
    }//end elementsMenu
    
    
    public void suspectsMenu() {
        int nbSuspects = m_suspectsList.length;
        for (int index = 0; index < nbSuspects; index++) System.out.print(new StringBuilder("Suspect ").append(index + 1).append(" : ").append(m_elements[index].display()).toString());
        
        String[] choicesList = {"Interroger un suspect.", 
                                "Innocenter un suspect.", 
                                "Arrêter un suspect.\n",
                                "Faire autre chose."};  //menu principal
        int action = m_console.display("C'est parti pour...", choicesList, false).execSingleChoice();
        
        if (action < choicesList.length) {
            System.out.print("Et l'heureux élu est ");
            int choicePlayer = m_console.execSingleChoice();

            switch(action) {
                case 0:
                    m_suspectsList[choicePlayer].BeInterrogated();
                    break;
                case 1:
                    m_suspectsList[choicePlayer].BeDisculpated();
                    break;
                case 2:
                    m_suspectsList[choicePlayer].BeArrested();
                    break;
            }
        }
    }//end void suspectsMenu
    
    
    public void cluesMenu() {
        int nbClues = m_player.getClueList().length;
        int indexClues = 0;
        for (int index = 0; index < nbClues; index++) {
            do indexClues++; while (indexClues < nbClues && m_player.getClue(indexClues).isFounded() == false);
            if (indexClues < nbClues) {
                System.out.print(new StringBuilder("Indice ").append(index + 1).append(" : ").append(m_player.getClue(indexClues).display()).toString());
                indexClues++;
            }
        }
        
        String[] choicesList = {"Détailler un témoignage.",
                                "Relier les indices.", 
                                "Chercher des incohérences.\n", 
                                "Faire autre chose."};  //menu principal
        int action = m_console.display("C'est parti pour...", choicesList, false).execSingleChoice();
        
        if (action < choicesList.length) {
            System.out.print("Allez, indice ");
            int choicePlayer = m_console.execSingleChoice();

            switch(action) {
                case 0:
                    //player.getClue(indexClues).something();
                    break;
                case 1:
                    //player.getClue(indexClues).something();
                    break;
                case 2:
                    //player.getClue(indexClues).something();
                    break;
            }
        }
    }//end void cluesMenu
}
