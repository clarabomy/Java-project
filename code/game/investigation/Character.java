/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation;

import project.game.UserInterface;

/**
 *
 * @author ISEN
 */
public abstract class Character {
    protected String m_name;
    protected boolean m_sex;
    protected int m_age;
    protected int m_diff;
    protected UserInterface m_console;

    
    /*$$ CONSTRUCTOR $$*/
    public Character(String name, boolean sex, int age) {
        this.m_name = name;
        this.m_sex = sex;
        this.m_age = age;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getName() {
        return m_name;
    }

    
    public boolean isSex() {
        return m_sex;
    }

    
    public int getAge() {
        return m_age;
    }
    
    
    /*$$ METHODS $$*/
    public abstract void displayInfos();
    public abstract void presentCharacter();
}
