
package project.game.character;


/**
 *
 * @author ISEN
 */
public abstract class Character {
    protected String m_fullName;
    protected Sex m_sex;
    protected int m_age;

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Character(String fullName, Sex sex, int age) {
        m_fullName = fullName;
        m_sex = sex;
        m_age = age;
    }
    
    /*$$ GETTERS & SETTERS $$*/
    public String getFullName() {
        return m_fullName;
    }

    public Sex getSex() {
        return m_sex;
    }

    public int getAge() {
        return m_age;
    }
    
    
    /*$$ METHODS $$*/
    
    public abstract void presentCharacter();
}
