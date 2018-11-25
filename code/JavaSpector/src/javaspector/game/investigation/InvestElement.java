
package javaspector.game.investigation;

import java.util.ArrayList;
import static javaspector.game.Game.getConsole;
import javaspector.game.character.Investigator;

/**
 *
 * Contains the methods and attributes of an investigation element
 * @author Clara BOMY
 */ 
public class InvestElement implements NoticeClues {
    protected ArrayList<Proof> m_proofList; //proofs present on the investigation element
    protected String m_type;

    /** 
     * Constructor of the class InvestElement
     * @param type      type of the investigation element
     * @param proofList proofs on the investigation element
     */ 
    public InvestElement(String type, ArrayList<Proof> proofList) {
        m_type = type;
        m_proofList = proofList;
    }

    /** 
     * Getter of the type of the investigation element
     * @return type   type of the investigation element
     */ 
    public String getType() {
        return m_type;
    }
    
    /** 
     * Getter of the ArrayList of proofs present on the investigation element
     * @return proofList    proofs present on the investigation element
     */ 
    public ArrayList<Proof> getProofList() {
        return m_proofList;
    }
    
    /** 
     * Get the proofs present on the investigation element
     * @param player    investigator
     */ 
    @Override
    public void analyse(Investigator player) {
        String analyseText;
        String proofText;
        
        if (m_proofList.get(0).getOrigin().equals("Scène de crime")) {
            analyseText = "L'équipe sur le terrain a fouillé la scène de crime. ";
            proofText = "Elle a réuni les indices suivants : ";
        }
        else {//if (m_proofList.get(0).getOrigin().equals("Arme du crime"))
           analyseText = "Les scientifiques ont analysé l'arme du crime. ";
           proofText = "Ils y ont relevé les indices suivants :";
        }
        getConsole().display(analyseText + proofText);
        
        //go throw the m_proofList list by putting current element into currentProof
        for (Proof currentProof : m_proofList) {
            currentProof.display();
            if (!player.getClueList().contains(currentProof)) {// add everything that was found to the list of found clues at once
                player.setClue((Clue) currentProof);// add proofs to the list of found clues
            }
        }
    }
}
