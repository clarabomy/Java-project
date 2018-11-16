
package project.game.character;

import java.util.ArrayList;
import project.game.investigation.Clue;
import static project.game.investigation.Investigation.suspectsNameList;

/**
 *
 * @author ISEN
 */
public class Investigator extends LiveCharacter {
    protected int m_manipulation;
    protected int m_intelligence;
    protected ArrayList <Clue> m_clueList;
    protected String m_progress;//"<meurtrier> a tué <victime> avec <arme> pour cause de <mobile>"//Phrase type remplie à l'initialisation du nouvelle partie (phrase à troue) avec ce qu'a déterminé le joueur

    
    /*$$ CONSTRUCTOR $$*/
    public Investigator(String name, String surname, Sex sex, int age, int manipulationLevel, int intelligenceLevel, ArrayList <Clue> clueList, String progress) {
        super(name, surname, sex, age);
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        this.m_clueList = new ArrayList(clueList);
        this.m_progress = progress;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public Clue getClue(int index) {
        return m_clueList.get(index);
    }
    
    
    public ArrayList <Clue> getClueList() {
        return m_clueList;
    }
    
    
    public String getProgress() {
        return m_progress;
    }

    
    /*$$ METHODS $$*/    
    @Override
    public void displayStats() {
        //afficher niveau manipulation, intelligence et popularité;
        String intelligence = "Votre niveau d'intelligence : " + this.m_intelligence;
        String manipulation = "Votre niveau de manipulation : " + this.m_manipulation;
        m_console.display(intelligence, true);
        m_console.display(manipulation, false).execContinue();
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        String text = "Vous êtes " + this.getFullName() + (m_sex.equals(Sex.WOMAN)? ", une enquêtrice" : ", un enquêteur") + " de talent!";
        m_console.display(text, false).execContinue();    
    }//end void presentCharacter
    
    
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
    
    
    public void crossClue(){
        /*
        joueur voit tous les indices et témoignages //consultClues()
        joueur voit phrase de display progress //displayProgress()
        joueur peut remplir phrase de display progress et peut se tromper donc remodifier
            joueur choisit trou à remplir
            joueur choisit une des possibilités
        */
        this.consultClues().displayProgress();
        String choices[] = {"<meurtrier>", /*"<victime>", */"<arme>", "<mobile>"};
        switch (m_console.display("Quel champ voulez-vous remplir ?", choices, false).execSingleChoice()) {
            case 1:
                String[] listSuspects = suspectsNameList();
                int designed = m_console.display("Le coupable serait le suspect...", listSuspects, false).execSingleChoice();
                m_progress.replace("<meurtrier>", listSuspects[designed]);//pour premier coup mais en cas d'erreur...?
                break;
            case 2:
                //String[m_clueList.length] listClues;
                
                break;
            case 3:
                break;
        }
    }
    
    public void displayProgress(){
        m_console.display(m_progress, true);
    }//end void displayProgress
    
    
    public void checkContradiction(){
        switch(rollDice(this.m_intelligence, "intelligence", true)) {
            case CRITIC_SUCCESS:
                //indique combien d'erreurs dans progrès
            case SUCCESS:
                //indique si erreur
            case FAILURE:
                //ne sait pas
            case CRITIC_FAILURE:
                //indique un nombre d'erreurs au hasard
        }
    }
    
    
    public DiceResult InvestigatorDices() {
        //inspecteur utilise intelligence et manipulation pour essayer de récupérer des infos (jet affiché)
        int[] stats = {this.m_intelligence, this.m_manipulation};
        String[] category = {"intelligence", "manipulation"};
        return rollMultiDice(stats , category, true);
    }

}
