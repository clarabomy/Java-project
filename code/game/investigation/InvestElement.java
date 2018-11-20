
package project.game.investigation;

import java.util.ArrayList;
import static project.game.Game.getConsole;
import project.game.character.Investigator;

/**
 *
 * @author ISEN
 */
public class InvestElement implements NoticeClues {
    protected ArrayList<Proof> m_proofList;//preuves savent d'ou elles proviennent
    protected String m_type;

    
    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public InvestElement(String type, ArrayList<Proof> proofList) {
        this.m_type = type;
        this.m_proofList = proofList;
    }

    public String getType() {
        return m_type;
    }
    
    public ArrayList<Proof> getProofList() {
        return m_proofList;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void analyse(Investigator player) {
        String analyseText;
        String proofText;
        
        if (m_proofList.get(0).getOrigin().equals("crimeScene")) {
            analyseText = "L'équipe sur le terrain a fouillé la scène de crime.";
            proofText = "Elle a réuni les indices suivants : ";
        }
        else {//if (m_proofList.get(0).getOrigin().equals("crimeWeapon"))
           analyseText = "Les scientifiques ont analysé l'arme du crime.";
           proofText = "Ils y ont relevé les indices suivants :";
        }
        
        
        for (Proof currentProof : m_proofList) {//parcours tout m_proofList en mettant élément courant dans currentProof
            proofText += "\n\t - " + currentProof.m_content;
            player.setClue((Clue) currentProof);//ajoute preuves à la liste d'indices
        }
        getConsole().display(analyseText + proofText, false).execContinue();
    }
}
