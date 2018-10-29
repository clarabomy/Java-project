/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation;

/**
 *
 * @author ISEN
 */
public abstract class LiveCharacter extends Character {
    protected static final int M_SIDES = 100;

    public LiveCharacter(String name, boolean sex, int age) {
        super(name, sex, age);
    }
    
    public abstract int rollDice();
    
}
