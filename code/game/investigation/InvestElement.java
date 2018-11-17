
package project.game.investigation;

import java.util.ArrayList;
import project.game.UserInterface;
import project.game.character.Investigator;

/**
 *
 * @author ISEN
 */
public class InvestElement implements NoticeClues {
    protected InvestElementType m_category; //scène de crime, arme
    protected ArrayList <Integer> m_refProof;
    protected UserInterface m_console;

    
    /*$$ CONSTRUCTOR $$*/
    public InvestElement(InvestElementType category, ArrayList <Integer> refProof) {
        this.m_category = category;
        this.m_refProof = new ArrayList(refProof);
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public void setRefProof(int ref) {
        this.m_refProof.add(ref);
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void analyse(Investigator player) {
        String analyseText;
        String proofText;
        
        if (InvestElementType.CRIME_SCENE == this.m_category) {
            analyseText = "L'équipe sur le terrain a fouillé la scène de crime.";
            proofText = "Elle a réuni les indices suivants : ";
        }
        
        else {
           analyseText = "Les scientifiques ont analysé l'arme du crime.";
           proofText = "Ils y ont relevé les indices suivants :";
        }
        
        for (int i = 0; i < m_refProof.size(); i++) {
            proofText += "\n\t - " + player.getClueList().get(m_refProof.get(i));
            player.getClueList().get(m_refProof.get(i)).setFounded(true);
        }
        m_console.display(analyseText + proofText, false).execContinue();
    }//end void analyse

    /*public String display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end String display*/
}
