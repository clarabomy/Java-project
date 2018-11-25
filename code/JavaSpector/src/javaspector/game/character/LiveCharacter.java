
package javaspector.game.character;

import java.util.ArrayList;
import javaspector.game.Difficulty;
import static javaspector.game.Game.getConsole;
import static javaspector.game.Game.getLevelChoice;
import javaspector.game.investigation.Clue;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public abstract class LiveCharacter extends Character {
    private static final int M_SIDES = 100;
    private static final int M_CRITICAL_SUCCESS = 15;
    private static final int M_CRITICAL_FAILURE = M_SIDES - M_CRITICAL_SUCCESS;
    private static final int M_ZONE_VALUE = 5; 
    private static float m_coeffDiff;
    private static int m_lastDiceValue; //valeur tirée du dé précédent
    private static int m_lastDiceValidStage; //palier de validation du lancer précédent
    
    protected static ArrayList <Clue> m_clueList = new ArrayList();

    /** 
     * Constructor of the class
     * @param fullName  explications
     * @param sex       explications
     * @param age       explications
     */ 
    public LiveCharacter(String fullName, Sex sex, int age) {
        super(fullName, sex, age);
        
        //facile : 0.75 | moyen : 1 | difficile : 1.25
        m_coeffDiff = (float) (getLevelChoice() == Difficulty.SIMPLE? 0.75 : (getLevelChoice() == Difficulty.MEDIUM? 1 : 1.25));
    }
    
    /** 
     * Getter of the class
     * @param index explications
     * @return clue explications
     */ 
    public Clue getClue(int index) {
        return m_clueList.get(index);
    }

    /** 
     * Getter of the class
     * @return clueList explications
     */ 
    public ArrayList <Clue> getClueList() {
        return m_clueList;
    }
    
    /** 
     * Setter of the class
     * @param newClue   explications
     */ 
    public void setClue(Clue newClue) {
        m_clueList.add(newClue);
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     */ 
    public static void dropClueList() {
        m_clueList = new ArrayList();
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @return liveCharacter    sends the reference to chain the calls
     */ 
    public abstract LiveCharacter displayStats();
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param validStage    explications
     * @param display       explications
     * @param newThrow      explications
     * @return diceResult   explications
     */ 
    public static DiceResult rollDice(int validStage, String display, boolean newThrow) {
        //lance un dé
        int diceValue = (int) (Math.random() * M_SIDES) + 1;//donne valeur entre 1 et 100

        
        //si ce lancé est dépendant du précédent, ajuste l'intervalle de validité
        if (!newThrow) {
            //si echec critique au précédent lancer 
            if (m_lastDiceValue >= M_CRITICAL_FAILURE) {
                diceValue = 100;//loupe autres lancers
            }
            //si réussite critique au précédent lancer
            else if (m_lastDiceValue <= M_CRITICAL_SUCCESS) {
                validStage += 10; //application du bonus dû au succès précédent
            }
            //si échec simple au précédent lancer
            else if (m_lastDiceValue > m_lastDiceValidStage && m_lastDiceValue < M_CRITICAL_FAILURE) {
                int zone = 0;
                do zone++; while (m_lastDiceValue > m_lastDiceValidStage + zone * ((M_SIDES - m_lastDiceValidStage) / 4));
                validStage -= (int)(M_ZONE_VALUE * zone * m_coeffDiff); //application du malus dû à l'échec précédent
            }
        }
        
        
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
        else /*if (diceValue >= M_CRITICAL_FAILURE)*/ {
            result = DiceResult.CRITIC_FAILURE;
        }
        
        
        //affiche infos si souhaité
        if (display != null) {
            getConsole().display("Jet " + display + " : " + diceValue + " / " + validStage + "\t" + result.toString());
        }
        
        
        return result;
    }
   
    /** 
     * Utilité / fonctionnement de la méthode
     * @param validStage    explications
     * @param display       explications
     * @param newThrow      explications
     * @return diceResult   explications
     */ 
    public static DiceResult rollMultiDice(int[] validStage, String[] display, boolean newThrow) {
        DiceResult action = DiceResult.ERROR;
        for (int i = 0; i < validStage.length; i++) {
            String text = (display != null && i < display.length)? display[i] : null;//y a t'il du texte à afficher pour ce lancer
            action = rollDice(validStage[i], text, newThrow);
            newThrow = false;//seul le premier lancer est une nouvelle suite de lancers
        }
        return action;
    }
}
