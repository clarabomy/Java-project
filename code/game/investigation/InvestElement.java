
package project.game.investigation;

import java.util.ArrayList;
import project.game.UserInterface;
import project.game.character.Investigator;

/**
 *
 * @author ISEN
 */
public class InvestElement implements NoticeClues {
    protected ArrayList<Proof> m_proofList;//preuves savent d'ou elles proviennent
    protected UserInterface m_console;

    
    /*$$ CONSTRUCTOR $$*/
    public InvestElement(ArrayList<Proof> proofList) {
        this.m_proofList = new ArrayList(proofList);
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
        
        for (int i = 0; i < m_proofList.size(); i++) {
            proofText += "\n\t - " + m_proofList.get(i).m_content;
            player.setClue((Clue) m_proofList.get(i));//ajoute preuves à la liste d'indices
        }
        m_console.display(analyseText + proofText, false).execContinue();
    }
}
