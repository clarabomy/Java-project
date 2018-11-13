
package project.game.character;

import static project.game.investigation.Investigation.suspectsNameList;
import project.investigation.InvestElement.Clue;

/**
 *
 * @author ISEN
 */
public class Investigator extends LiveCharacter {
    protected int m_manipulation;
    protected int m_intelligence;
    protected Clue[] m_clueList;
    protected String m_progress;//"<meurtrier> a tué <victime> avec <arme> pour cause de <mobile>"//Phrase type remplie à l'initialisation du nouvelle partie (phrase à troue) avec ce qu'a déterminé le joueur

    
    /*$$ CONSTRUCTOR $$*/
    public Investigator(String name, String surname, Sex sex, int age, int manipulationLevel, int intelligenceLevel, Clue[] cluesList, String progress) {
        super(name, surname, sex, age);
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        this.m_clueList = new Clue[cluesList.length];
        System.arraycopy(cluesList, 0, this.m_clueList, 0, cluesList.length);
        this.m_progress = progress;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public Clue getClue(int index) {
        return m_clueList[index];
    }
    
    
    public Clue[] getClueList() {
        return m_clueList;
    }
    
    
    public String getProgress() {
        return m_progress;
    }
    
    
    public void setClueList(Clue[] m_clueList) {
        this.m_clueList = m_clueList;
    }

    
    /*$$ METHODS $$*/    
    @Override
    public void displayStats() {
        //afficher niveau manipulation, intelligence et popularité;
        String intelligence = new StringBuilder("Votre niveau d'intelligence : ").append(this.m_intelligence).toString();
        String manipulation = new StringBuilder("Votre niveau de manipulation : ").append(this.m_manipulation).toString();
        m_console.display(intelligence, true);
        m_console.display(manipulation, false).execContinue();
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        //m_console.display(new StringBuilder("Vous êtes").append(m_name).append(" ").append(m_surname).append(m_sex.equals(Sex.WOMAN)? ", une enquêtrice" : ", un enquêteur").append("de talent!").toString(), false).execContinue();
        
        //Description de l'enqueteur : nom, prenom
        String text;
        if (this.getSex().toString().equals("woman")) text = new StringBuilder("").append(this.getFullName()).append(", une enquêtrice de talent !").toString();
        else text = new StringBuilder("").append(this.getFullName()).append(", un enquêteur de talent !").toString();
        m_console.display(text, false).execContinue();    
    }//end void presentCharacter
    
    
    public Investigator consultClues(){
        //affiche les indices ayant été trouvés
        //for (int i = 0; i < m_clueList.length; i++) if (m_clueList[i].isFounded()) m_console.display(new StringBuilder("Indice ").append(i+1).append(" : ").append(m_clueList[i].getContent()).toString(), true);
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
        switch (m_console.display("Quel champ voulez vous remplir?", choices, false).execSingleChoice()) {
            case 1:
                String[] listSuspects = suspectsNameList();
                int designed = m_console.display("Le coupable est le suspect...", listSuspects, false).execSingleChoice();
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
