/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    protected Clue[] m_clueList;// faire un getter et ou un setter
    protected String m_progress;

    
    /*$$ CONSTRUCTOR $$*/
    public Investigator(String name, Sex sex, int age, int manipulationLevel, int intelligenceLevel, int popularityLevel, Clue[] cluesList, String progress) {
        super(name, sex, age);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        //Description de l'enqueteur : nom, age, sexe
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void presentCharacter
    
    
    public void consultClues(){ //consulter indices
        //affiche les indices ayant été trouvés 
    }//end void lookForClues
    
    //public String crossClue(){
        /*
        joueur voit tous les indices et témoignages //consultClues()
        joueur voit phrase de display progress //displayProgress()
        joueur peut remplir phrase de display progress et peut se tromper donc remodifier
            joueur choisit trou à remplir
            joueur choisit une des possibilités
        */
    //}
    
    public void displayProgress(){
        //<tueur> a tué <victime> avec <arme> pour <mobile> raison.
        //Phrase type remplie à l'initialisation du nouvelle partie (phrase à troue) avec ce qu'a déterminé le joueur
    }//end void displayProgress
    
    public void checkContradiction(){
        //lance le dé d'intelligence 
        /*
        réussite critique : intique combien d'erreurs dans progress
        réussite : indique si erreur
        echec : ne sait pas
        echec critique : indique un nombre d erreurs au hasard
        */
    }
}
