
package javaspector.game.character;

/**
 *
 * Contains the methods and attributes common to all characters
 * @author Clara BOMY
 */ 
public abstract class Character {
    protected String m_fullName;
    protected Sex m_sex;
    protected int m_age;

    /** 
     * Constructor of the class character
     * @param fullName :  full name of the character
     * @param sex      :  sex of the character
     * @param age      :  age of the character
     */ 
    public Character(String fullName, Sex sex, int age) {
        m_fullName = fullName;
        m_sex = sex;
        m_age = age;
    }
    
    /** 
     * Getter of the full name
     * @return fullName full name of the character
     */ 
    public String getFullName() {
        return m_fullName;
    }

    /** 
     * Getter of the sex
     * @return sex : sex of the character
     */ 
    public Sex getSex() {
        return m_sex;
    }

    /** 
     * Getter of the age
     * @return age : age o fthe character
     */ 
    public int getAge() {
        return m_age;
    }

    /** 
     * Displays a presentation sentence
     */ 
    public abstract void presentCharacter();
}
