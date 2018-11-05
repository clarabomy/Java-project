
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
        System.out.printf("Votre niveau d'intelligence : %d\nVotre niveau de manipulation : %d\nVotre niveau de popularité : %d\n", this.getIntelligence(), this.getManipulation(), this.getPopularity());
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        //Description de l'enqueteur : nom, age, sexe
        if (this.getSex().toString().equals("woman")) System.out.printf("Vous êtes %s %s, une enquêtrice de talent !\n", this.getName().toUpperCase(), this.getSurname().substring(0,1).toUpperCase() + this.getSurname().substring(1).toLowerCase());
        else System.out.printf("Vous êtes %s %s, un enquêteur de talent !\n",  this.getSurname().substring(0,1).toUpperCase() + this.getSurname().substring(1).toLowerCase(), this.getName().substring(0,1).toUpperCase() + this.getName().substring(1).toLowerCase());  
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
        System.out.println("<tueur> a tué <victime> avec <arme> pour cause de <mobile>");
        //Phrase type remplie à l'initialisation du nouvelle partie (phrase à troue) avec ce qu'a déterminé le joueur
    }//end void displayProgress
    
    public void checkContradiction(){
        switch(throwSimpleDice(this.getIntelligence())) {
            case 1://succes critique
                //indique combien d'erreurs dans progrès
            case 2://succes
                //indique si erreur
            case 3://echec
                //ne sait pas
            case 4://echec critique
                //indique un nombre d'erreurs au hasard
        }
    }
}
