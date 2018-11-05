
package project.game.investigation;

/**
 *
 * @author ISEN
 */
public class Victim extends Character implements NoticeClues {
    protected String m_deathDate;
    protected String m_deathCause;
    protected int[] m_refProof;


    /*$$ CONSTRUCTOR $$*/
    public Victim(String name, String surname, Sex sex, int age, String deathDate, String deathCause, int[] refProof) {
        super(name, surname, sex, age);
        this.m_deathDate = deathDate;
        this.m_deathCause = deathCause;
        this.m_refProof = new int[refProof.length];
        System.arraycopy(refProof, 0, this.m_refProof, 0, refProof.length);
    }

    
    /*$$ GETTERS & SETTERS $$*/
    public String getDeathDate() {
        return m_deathDate;
    }

    
    public String getDeathCause() {
        return m_deathCause;
    }
    
    
    /*$$ METHODS $$*/
    
    @Override
    public void presentCharacter() {
        //Victime : nom, sexe, age (phrase différente)
        System.out.printf("Victime : %s %s (%s, %d ans\n)", this.getSurname().substring(0,1).toUpperCase() + this.getSurname().substring(1).toLowerCase(), this.getName().substring(0,1).toUpperCase() + this.getName().substring(1).toLowerCase(), this.getSex().toString(), this.getAge());
    }//end void presentCharacter

    
    @Override
    public void analyse(Investigator player) { //autopsie
        //donne la cause de la mort + date de la mort
        //+ indices associées (passeront de non trouvé à trouvé)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//end void analyse
}