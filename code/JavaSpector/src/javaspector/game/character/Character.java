
package javaspector.game.character;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public abstract class Character {
    protected String m_fullName;
    protected Sex m_sex;
    protected int m_age;

    /** 
     * Constructor of the class
     * @param fullName  explications
     * @param sex       explications
     * @param age       explications
     */ 
    public Character(String fullName, Sex sex, int age) {
        m_fullName = fullName;
        m_sex = sex;
        m_age = age;
    }
    
    /** 
     * Getter of the class
     * @return fullName explications
     */ 
    public String getFullName() {
        return m_fullName;
    }

    /** 
     * Getter of the class
     * @return sex  explications
     */ 
    public Sex getSex() {
        return m_sex;
    }

    /** 
     * Getter of the class
     * @return age  explications
     */ 
    public int getAge() {
        return m_age;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    public abstract void presentCharacter();
}
