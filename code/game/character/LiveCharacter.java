
package project.game.character;

import java.util.ArrayList;
import project.game.investigation.Clue;

/**
 *
 * @author ISEN
 */
public abstract class LiveCharacter extends Character {
    private static final int M_SIDES = 100;
    private static final int M_CRITICAL_SUCCESS = 15;
    private static final int M_CRITICAL_FAILURE = 95;
    private static final int M_ZONE_VALUE = 5; 
    private static int m_coeffDiff; //pour 50 : 1.25 = {44, 38, 32, 25} / 1 = {45, 40, 35, 30} / 0.75 = {47, 43, 39, 35}
    private static int m_lastDiceValue; //valeur tirée du dé précédent
    private static int m_lastDiceValidStage; //palier de validation du lancer précédent
    protected ArrayList <Clue> m_clueList = new ArrayList();// tous les personnages vivants peuvent modifier le tableau d'indices (modifient témoignage)

    
    /*$$ CONSTRUCTOR $$*/
    public LiveCharacter(String name, String surname, Sex sex, int age, ArrayList <Clue> clueList) {
        super(name, surname, sex, age);
        m_clueList.addAll(clueList);
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public Clue getClue(int index) {
        return m_clueList.get(index);
    }
    
    
    public ArrayList <Clue> getClueList() {
        return m_clueList;
    }
    
    
    public void addClue(Clue newClue) {
        m_clueList.add(newClue);
    }
    
    
    public void addClueList(ArrayList <Clue> newClueList) {
        m_clueList.addAll(newClueList);
    }
    
    
    public void setClueList(ArrayList <Clue> newClueList) {
        m_clueList = newClueList;
    }
    
    
    
    /*$$ METHODS $$*/
    public abstract void displayStats();
    
   
    public static DiceResult rollDice(int validStage, String display, boolean newThrow) {
        //lance un dé
        int diceValue = (int) (Math.random() * M_SIDES) + 1;//donne valeur entre 1 et 100

        
        //si ce lancé est dépendant du précédent, ajuste l'intervalle de validité
        //si échec simple au précédent lancer et qu'on prend en compte l'ancien lancer
        if (!newThrow && m_lastDiceValue > m_lastDiceValidStage && m_lastDiceValue < M_CRITICAL_FAILURE) {
            int zone = 0;
            do zone++; while (m_lastDiceValue > m_lastDiceValidStage + zone * ((M_SIDES - m_lastDiceValidStage) / 4));
            validStage -= (int)(M_ZONE_VALUE * zone * m_coeffDiff); //application du malus dû à l'échec précédent
        }
        //si echec critique = loupe autres lancers
        
        
        //met à jour les attributs
        m_lastDiceValue = diceValue;
        m_lastDiceValidStage = validStage;
    
        
        //détermine le résultat
        DiceResult result;
        //plus fréquent
        if (diceValue <= validStage && diceValue > M_CRITICAL_SUCCESS) result = DiceResult.SUCCESS;
        else if (diceValue > validStage && diceValue < M_CRITICAL_FAILURE) result = DiceResult.FAILURE;
        
        //moins fréquent
        else if (diceValue <= M_CRITICAL_SUCCESS) result = DiceResult.CRITIC_SUCCESS;
        else result = DiceResult.CRITIC_FAILURE;//if (throwResult >= M_CRITICAL_FAILURE)
        
        
        //affiche infos si souhaité
        if (display != null) {
            System.out.printf("Jet de %s : %d / %d\t%s\n", display, diceValue, validStage, result.toString());
        }
        
        
        return result;
    }
   
    
    public static DiceResult rollMultiDice(int[] validStage, String[] display, boolean newThrow) {
        DiceResult action = DiceResult.ERROR;
        for (int i = 0; i < validStage.length; i++) action = rollDice(validStage[i], (i < display.length)? display[i] : null, (i == 0)? newThrow : false);
        return action;
    }
}
