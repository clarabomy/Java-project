
package javaspector.game.character;

import java.util.ArrayList;
import static javaspector.game.Game.getConsole;
import javaspector.game.investigation.Clue;
import javaspector.game.investigation.Deposition;
import javaspector.game.investigation.Proof;

/**
 *
 * Contains the methods and attributes of the investigator
 * @author Clara BOMY
 */ 
public class Investigator extends LiveCharacter {
    protected int m_manipulation; //manipulation level 
    protected int m_intelligence; //intelligence level
    
    /** 
     * Constructor of the class Investigator
     * @param fullName          name of the investigator
     * @param gender            sex of the investigator
     * @param manipulationLevel manipulation level of the investigator
     * @param intelligenceLevel intelligence level of the investigator
     * @param clueList          list of clues and depositions
     */ 
    public Investigator(String fullName, Sex gender, int manipulationLevel, int intelligenceLevel, ArrayList<Clue> clueList) {
        super(fullName, gender, 30);
        m_manipulation = manipulationLevel;
        m_intelligence = intelligenceLevel;
        if (clueList != null) {
            m_clueList.addAll(clueList);
        }
    }

    /** 
     * Getter of the intelligence level
     * @return intelligence intelligence level of the investigator
     */ 
    public int getIntelligence() {
        return m_intelligence;
    }
    
    /** 
     * Getter of the manipulation level
     * @return manipulation manipulation level of the investigator
     */ 
    public int getManipulation() {
        return m_manipulation;
    }
       
    /** 
     * Displays a presentation sentence
     */ 
    @Override
    public void presentCharacter() {
        String part1 = "Bonjour. J'ai consulté votre dossier, et le moins qu'on puisse dire, c'est que vous êtes " + (m_sex.equals(Sex.FEMME)? "une sacrée enquêtrice !" : "un sacré enquêteur !");
        String part2 = " Mais on va quand même vérifier vos capacités.";
        getConsole().display("Lieutenant Chef", part1 + part2).execContinue("Vous passez un test d'aptitude");
        displayStats();
    }
    
    /** 
     * Displays intelligence and manipulation levels of the investigator
     * @return investigator : sends the reference to chain the calls
     */ 
    @Override
    public Investigator displayStats() {
        getConsole().display("Rapport du test d'aptitude de " + m_fullName + " :")
                    .display("Votre niveau d'intelligence : " + m_intelligence + "\nVotre niveau de manipulation : " + m_manipulation)
                    .execContinue("Vous reposez le rapport");
        
        return this;
    }
    
    /** 
     * Displays all the clues found by the investigator
     * @return investigator sends the reference to chain the calls
     */ 
    public Investigator consultClues(){
        getConsole().display("Indices découverts dans cette enquête :\n");
        for (int i = 0; i < m_clueList.size(); i++) {
            String origin = m_clueList.get(i) instanceof Deposition? ((Deposition) m_clueList.get(i)).getDepositor() : ((Proof) m_clueList.get(i)).getOrigin();
            getConsole().display("Indice " + (i+1) + " : " + origin + " - " + m_clueList.get(i).getContent());
        }
        return this;
    }
}
