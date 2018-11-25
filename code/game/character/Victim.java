
package project.game.character;

import java.util.ArrayList;
import static project.game.Game.getConsole;
import project.game.investigation.NoticeClues;
import project.game.investigation.Proof;

/**
 *
 * @author ISEN
 */
public class Victim extends Character implements NoticeClues {
    protected String m_deathDate;
    protected String m_deathCause;
    protected ArrayList <Proof> m_proofList;//preuves sur l'élément d'enquête



    /*$$ CONSTRUCTOR $$*/
    //nouvelle partie et chargement
    public Victim(String fullName, Sex sex, int age, String deathDate, String deathCause, ArrayList <Proof> proofList) {
        super(fullName, sex, age);
        m_deathDate = deathDate;
        m_deathCause = deathCause;
        m_proofList = new ArrayList(proofList);
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getDeathDate() {
        return m_deathDate;
    }

    public String getDeathCause() {
        return m_deathCause;
    }

    public ArrayList<Proof> getProofList() {
        return m_proofList;
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void presentCharacter() {
        String victimPresentation = "La victime est " + (m_sex == Sex.FEMME? "une femme de " : "un homme de ") + m_age + " ans. Sa carte d'indentité indique qu'" + (m_sex == Sex.FEMME? "elle s'appelait " : "il s'appelait ") + m_fullName + ".";
        getConsole().display(victimPresentation);
    }

    @Override
    public void analyse(Investigator player) { //autopsie
        presentCharacter();
        String analyseText = "Les médecins légistes ont réalisé une autopsie du corps. La victime serait morte " + m_deathDate + " pour cause " + getDeathCause() + ".\n";
        String proofText = "De plus, ils y ont trouvé les indices suivants :";
        getConsole().display(analyseText + proofText);
        for (Proof currentProof : m_proofList) {
            getConsole().display("   - " + currentProof.getContent());
            if (!player.getClueList().contains(currentProof)) {//ajoute tout ce qui a été trouvé à la liste d'indices d'un coup
                player.setClue((Proof) currentProof);//ajoute preuves à la liste d'indices
            }
        }
    }
}