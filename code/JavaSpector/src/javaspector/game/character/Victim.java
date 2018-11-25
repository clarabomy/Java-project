
package javaspector.game.character;

import java.util.ArrayList;
import static javaspector.game.Game.getConsole;
import javaspector.game.investigation.NoticeClues;
import javaspector.game.investigation.Proof;

/**
 *
 * Contains the methods and attributes of the victim
 * @author Clara BOMY
 */ 
public class Victim extends Character implements NoticeClues {
    protected String m_deathDate;
    protected String m_deathCause;
    protected ArrayList <Proof> m_proofList; //proofs present on the victim

    /** 
     * Constructor of the class Victim
     * @param fullName      full name of the victim
     * @param sex           sex of the victim
     * @param age           age of the victim
     * @param deathDate     date of the death
     * @param deathCause    cause of the death
     * @param proofList     proofs on the victim
     */ 
    public Victim(String fullName, Sex sex, int age, String deathDate, String deathCause, ArrayList <Proof> proofList) {
        super(fullName, sex, age);
        m_deathDate = deathDate;
        m_deathCause = deathCause;
        m_proofList = new ArrayList(proofList);
    }

    /** 
     * Getter of the date of the death
     * @return deathDate   date of the death
     */ 
    public String getDeathDate() {
        return m_deathDate;
    }

    /** 
     * Getter of the cause of the death
     * @return deathCause   cause of the death
     */ 
    public String getDeathCause() {
        return m_deathCause;
    }

    /** 
     * Getter of the ArrayList of proofs present on the victim
     * @return proofList    proofs present on the victim
     */ 
    public ArrayList<Proof> getProofList() {
        return m_proofList;
    }
    
    /** 
     * Displays a presentation sentence
     */ 
    @Override
    public void presentCharacter() {
        String victimPresentation = "La victime est " + (m_sex == Sex.FEMME? "une femme de " : "un homme de ") + m_age + " ans. Sa carte d'indentité indique qu'" + (m_sex == Sex.FEMME? "elle s'appelait " : "il s'appelait ") + m_fullName + ".";
        getConsole().display(victimPresentation);
    }

    /** 
     * Get the proofs present on the victim
     * @param player    investigator
     */ 
    @Override
    public void analyse(Investigator player) { //autopsy
        presentCharacter();
        String analyseText = "Les médecins légistes ont réalisé une autopsie du corps. La victime serait morte " + m_deathDate + " pour cause " + getDeathCause() + ".\n";
        String proofText = "De plus, ils y ont trouvé les indices suivants :";
        getConsole().display(analyseText + proofText);
        
        //Go throw the list of proofs present on the victim
        for (Proof currentProof : m_proofList) {
            getConsole().display("   - " + currentProof.getContent());
            if (!player.getClueList().contains(currentProof)) {//add everything that was found to the list of clues found at once
                player.setClue((Proof) currentProof);//add proofs to the clues found list
            }
        }
    }
}