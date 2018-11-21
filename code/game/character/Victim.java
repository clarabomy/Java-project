
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
    public Victim(String name, String surname, Sex sex, int age, String deathDate, String deathCause, ArrayList <Proof> proofList) {
        super(name, surname, sex, age);
        this.m_deathDate = deathDate;
        this.m_deathCause = deathCause;
        this.m_proofList = new ArrayList(proofList);
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
        //Victime : nom, sexe, age (phrase différente)
        String victimPresentation = "La victime est " + (m_sex == Sex.FEMME? "une femme de " : "un homme de ") + m_age + " ans. Sa carte d'indentité indique qu'" + (m_sex == Sex.FEMME? "elle s'appelait " : "il s'appelait ") + this.m_fullName + ".";
        getConsole().display(victimPresentation, false).execContinue();
    }

    @Override
    public void analyse(Investigator player) { //autopsie
        String analyseText = "Les médecins légistes ont réalisé une autopsie du corps. La victime serait morte le " + this.getDeathDate() + " pour cause de " + this.getDeathCause() + ". ";
        String proofText = "De plus, ils y ont trouvé les indices suivants : ";
        for (Proof actualProof : m_proofList) {
            proofText += "\n   - " + actualProof.getContent();
        }
        if (!player.m_clueList.containsAll(m_proofList)) {//ajoute tout ce qui a été trouvé à la liste d'indices d'un coup
            player.m_clueList.addAll(m_proofList);
        }
        getConsole().display(analyseText + proofText, false).execContinue();
    }
}