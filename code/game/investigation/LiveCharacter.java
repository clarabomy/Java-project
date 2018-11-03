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
    protected static final int M_CRITICAL_SUCCESS = 15;
    protected static final int M_CRITICAL_FAILURE = 15;
    protected static final int[] M_BONUS = {30, 20, 10};//simple, medium, difficult
    protected static final int[] M_MALUS = {10, 20, 30};//same

    
    /*$$ CONSTRUCTOR $$*/
    public LiveCharacter(String name, boolean sex, int age) {
        super(name, sex, age);
    }
    
    
    /*$$ METHODS $$*/
    public abstract int rollDice();
}
