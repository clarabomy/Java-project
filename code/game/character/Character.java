
package project.game.character;

import project.game.UserInterface;

/**
 *
 * @author ISEN
 */
public abstract class Character {
    protected String m_fullName;
    protected Sex m_sex;
    protected int m_age;
    protected UserInterface m_console = new UserInterface();

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Character(String name, String surname, Sex sex, int age) {
        this.m_fullName = this.fullName(name, surname);
        this.m_sex = sex;
        this.m_age = age;
    }
    
    /*$$ GETTERS & SETTERS $$*/
    public String getFullName() {
        return m_fullName;
    }

    
    /*$$ METHODS $$*/
    private String fullName (String name, String surname) {
        return surname + " " + name;
    }
    
    public abstract void presentCharacter();
}
