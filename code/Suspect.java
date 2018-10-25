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
public abstract class Suspect extends LiveCharacter {
    int stress;
    int cooperation;
    String personality;
    String look;
    String physicalAspect;
    boolean findedInnocent;
    int[] testimonyRef;

    public Suspect(String name, boolean sex, int age, int stressLevel, int cooperationLevel, String personality, String look, String physicalAspect, boolean findedInnocent, int[] testimonyRef) {
        super(name, sex, age);
        this.stress = stressLevel;
        this.cooperation = cooperationLevel;
        this.personality = personality;
        this.look = look;
        this.physicalAspect = physicalAspect;
        this.findedInnocent = findedInnocent;
        this.testimonyRef = new int[testimonyRef.length];
        System.arraycopy(testimonyRef, 0, this.testimonyRef, 0, testimonyRef.length);
    }

    public int getStress() {
        return stress;
    }

    public int getCooperation() {
        return cooperation;
    }
    
    abstract void BeInterrogated();
    
    @Override
    public int diceThrow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void presentCharacter(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    void BeDisculpated(){
    }
    
    void BeArrested(){
    }
    
    public String display() {
        return "tmp";
    }
}
