
package project.game.character;

import java.util.ArrayList;
import project.game.investigation.NoticeClues;

/**
 *
 * @author ISEN
 */
public class Victim extends Character implements NoticeClues {
    protected String m_deathDate;
    protected String m_deathCause;
    protected ArrayList <Integer> m_refProof;



    /*$$ CONSTRUCTOR $$*/
    public Victim(String name, String surname, Sex sex, int age, String deathDate, String deathCause, ArrayList <Integer> refProof) {
        super(name, surname, sex, age);
        this.m_deathDate = deathDate;
        this.m_deathCause = deathCause;
        this.m_refProof = new ArrayList(refProof);
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getDeathDate() {
        return m_deathDate;
    }

    public String getDeathCause() {
        return m_deathCause;
    }

    public void setRefProof(int ref) {
        this.m_refProof.add(ref);
    }
    
    
    /*$$ METHODS $$*/
    @Override
    public void presentCharacter() {
        //Victime : nom, sexe, age (phrase différente)
        String victimPresentation = "La victime est " + (m_sex == Sex.WOMAN? "une femme de " : "un homme de ") + m_age + " ans. Sa carte d'indentité indique qu'" + (m_sex == Sex.WOMAN? "elle s'appelait" : "il s'appelait") + this.getFullName() + ".";
        m_console.display(victimPresentation, false).execContinue();
    }

    @Override
    public void analyse(Investigator player) { //autopsie
        //donne la cause de la mort + date de la mort
        //+ indices associées (passeront de non trouvé à trouvé)
        
        String analyseText = "Les médecins légistes ont réalisé une autopsie du corps. La victime serait morte le " + this.getDeathDate() + " pour cause de " + this.getDeathCause();
        String proofText = "De plus, ils y ont trouvé les indices suivants ";
        for (int i = 0; i < m_refProof.size(); i++) {
            proofText += "\n\t - " + player.m_clueList.get(m_refProof.get(i));
            player.m_clueList.get(m_refProof.get(i)).setFounded(true);
        }
        m_console.display(analyseText + proofText, false).execContinue();
    }
}