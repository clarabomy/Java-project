
package project.game.investigation;

import project.game.investigation.clue.Clue;

/**
 *
 * @author ISEN
 */
public class Investigator extends LiveCharacter {
    protected int m_manipulation;
    protected int m_intelligence;
    protected int m_popularity;
    protected Clue[] m_clueList;
    protected String m_progress;

    
    /*$$ CONSTRUCTOR $$*/
    public Investigator(String name, String surname, Sex sex, int age, int manipulationLevel, int intelligenceLevel, int popularityLevel, Clue[] cluesList, String progress) {
        super(name, surname, sex, age);
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        this.m_popularity = popularityLevel;
        this.m_clueList = new Clue[cluesList.length];
        System.arraycopy(cluesList, 0, this.m_clueList, 0, cluesList.length);
        this.m_progress = progress;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public int getManipulation() {
        return m_manipulation;
    }

    
    public int getIntelligence() {
        return m_intelligence;
    }

    
    public int getPopularity() {
        return m_popularity;
    }

    
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
        String intelligence = new StringBuilder("Votre niveau d'intelligence : ").append(this.getIntelligence()).toString();
        String manipulation = new StringBuilder("Votre niveau de manipulation : ") .append(this.getManipulation()).toString();
        String popularity = new StringBuilder("Votre niveau de popularité : ").append(this.getPopularity()).toString();
        m_console.display(intelligence, true);
        m_console.display(manipulation, true);
        m_console.display(popularity, false).execContinue();
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        //Description de l'enqueteur : nom, prenom
        String text;
        if (this.getSex().toString().equals("woman")) text = new StringBuilder("").append(this.getFullName()).append(", une enquêtrice de talent !").toString();
        else text = new StringBuilder("").append(this.getFullName()).append(", un enquêteur de talent !").toString();
        m_console.display(text, false).execContinue();    
    }//end void presentCharacter
    
    
    public void consultClues(){ //consulter indices
        //affiche les indices ayant été trouvés 
    }//end void lookForClues
    
    
    public void crossClue(){
        /*
        joueur voit tous les indices et témoignages //consultClues()
        joueur voit phrase de display progress //displayProgress()
        joueur peut remplir phrase de display progress et peut se tromper donc remodifier
            joueur choisit trou à remplir
            joueur choisit une des possibilités
        */
    }
    
    public void displayProgress(){
        //Phrase type remplie à l'initialisation du nouvelle partie (phrase à troue) avec ce qu'a déterminé le joueur
        //"<tueur> a tué <victime> avec <arme> pour cause de <mobile>"
        m_console.display(m_progress, false).execContinue();
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
