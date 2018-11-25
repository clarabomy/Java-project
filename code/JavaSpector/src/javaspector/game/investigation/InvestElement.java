
package javaspector.game.investigation;

import java.util.ArrayList;
import static javaspector.game.Game.getConsole;
import javaspector.game.character.Investigator;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public class InvestElement implements NoticeClues {
    protected ArrayList<Proof> m_proofList;//preuves savent d'ou elles proviennent
    protected String m_type;

    /** 
     * Constructor of the class
     * @param type      explications
     * @param proofList explications
     */ 
    public InvestElement(String type, ArrayList<Proof> proofList) {
        m_type = type;
        m_proofList = proofList;
    }

    /** 
     * Getter of the class
     * @return type explications
     */ 
    public String getType() {
        return m_type;
    }
    
    /** 
     * Getter of the class
     * @return proofList    explications
     */ 
    public ArrayList<Proof> getProofList() {
        return m_proofList;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param player    explications
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
        
        for (Proof currentProof : m_proofList) {//parcours tout m_proofList en mettant élément courant dans currentProof
            currentProof.display();
            if (!player.getClueList().contains(currentProof)) {//ajoute tout ce qui a été trouvé à la liste d'indices d'un coup
                player.setClue((Clue) currentProof);//ajoute preuves à la liste d'indices
            }
        }
    }
}
