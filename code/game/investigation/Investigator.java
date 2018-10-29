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
    int m_manipulation;
    int m_intelligence;
    int m_popularity;
    public Clue[] m_cluesList;
    String m_progress;

    public Investigator(String name, boolean sex, int age, int manipulationLevel, int intelligenceLevel, int popularityLevel, Clue[] cluesList, String progress) {
        super(name, sex, age);
        this.m_manipulation = manipulationLevel;
        this.m_intelligence = intelligenceLevel;
        this.m_popularity = popularityLevel;
        this.m_cluesList = new Clue[cluesList.length];
        System.arraycopy(cluesList, 0, this.m_cluesList, 0, cluesList.length);
        this.m_progress = progress;
    }

    public int getM_manipulation() {
        return m_manipulation;
    }

    public int getM_intelligence() {
        return m_intelligence;
    }

    public int getM_popularity() {
        return m_popularity;
    }

    public Clue[] getM_cluesList() {
        return m_cluesList;
    }

    public String getM_progress() {
        return m_progress;
    }
    
    @Override
    public int rollDice() {
        int roll = (int) (Math.random() * M_SIDES) + 1;
        System.out.println(roll);
        return(roll); 
    }

    @Override
    public void displayInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        @Override
    public void presentCharacter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    void lookForClues(){
    }
    
    /*
    String crossClues(){
    }*/
    
    void displayProgress(){
    }


}
