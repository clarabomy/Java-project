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
    public Investigator(String name, boolean sex, int age, int manipulationLevel, int intelligenceLevel, int popularityLevel, Clue[] cluesList, String progress) {
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
    public int rollDice() {
        int roll = (int) (Math.random() * M_SIDES) + 1;
        System.out.println(roll);
        return(roll); 
    }//end int rollDice

    
    @Override
    public void displayInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void displayInfos
    
    
    @Override
    public void presentCharacter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void presentCharacter
    
    
    public void lookForClues(){
    }//end void lookForClues
    
    /*
    String crossClues(){
    }*/
    
    public void displayProgress(){
    }//end void displayProgress
}
