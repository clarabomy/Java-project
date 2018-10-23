/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ISEN
 */
public class Investigator extends LiveCharacter {
    int manipulation;
    int intelligence;
    int popularity;
    Clue[] cluesList;
    String progress;

    public Investigator(String name, boolean sex, int age, int manipulationLevel, int intelligenceLevel, int popularityLevel, Clue[] cluesList, String progress) {
        super(name, sex, age);
        this.manipulation = manipulationLevel;
        this.intelligence = intelligenceLevel;
        this.popularity = popularityLevel;
        this.cluesList = new Clue[cluesList.length];
        System.arraycopy(cluesList, 0, this.cluesList, 0, cluesList.length);
        this.progress = progress;
    }

    public int getManipulation() {
        return manipulation;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getPopularity() {
        return popularity;
    }

    public Clue[] getCluesList() {
        return cluesList;
    }

    public String getProgress() {
        return progress;
    }
    
    @Override
    public int diceThrow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
