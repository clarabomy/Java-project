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
public abstract class Clue {
    String content;
    boolean isFounded;

    public Clue(String content, boolean isFounded) {
        this.content = content;
        this.isFounded = isFounded;
    }
    
    
    void BeFounded(){
    }
    
    abstract void assign();
    
    
    public String display() {
        return "tmp";
    }
}
