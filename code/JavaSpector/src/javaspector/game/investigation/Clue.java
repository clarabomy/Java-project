
package javaspector.game.investigation;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public abstract class Clue {
    protected String m_content;
    
    /** 
     * Constructor of the class
     * @param content   explications
     */ 
    public Clue(String content) {
        m_content = content;
    }

    /** 
     * Getter of the class
     * @return content  explications
     */ 
    public String getContent() {
        return m_content;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    public abstract void display();
}
