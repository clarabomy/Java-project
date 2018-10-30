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
public abstract class Character {
    protected String m_name;
    protected boolean m_sex;
    protected int m_age;

    public Character(String name, boolean sex, int age) {
        this.m_name = name;
        this.m_sex = sex;
        this.m_age = age;
    }

    public String getM_name() {
        return m_name;
    }

    public boolean isM_sex() {
        return m_sex;
    }

    public int getM_age() {
        return m_age;
    }
    
    public abstract void displayInfos();
    public abstract void presentCharacter();
}
