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
public abstract class LiveCharacter extends Character {

    public LiveCharacter(String name, boolean sex, int age) {
        super(name, sex, age);
    }
    
    public abstract int rollDice();
    
}
