
package project.game.character;

import project.game.UserInterface;

/**
 *
 * @author ISEN
 */
public abstract class Character {
    protected String m_name;
    protected String m_surname;
    protected Sex m_sex;
    protected int m_age;
    protected UserInterface m_console = new UserInterface();

    
    /*$$ CONSTRUCTOR $$*/
    public Character(String name, String surname, Sex sex, int age) {
        this.m_name = name;
        this.m_surname = surname;
        this.m_sex = sex;
        this.m_age = age;
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getFullName() {
        return m_surname + " " + m_name;
    }

    
    /*$$ METHODS $$*/
    public abstract void presentCharacter();
}
