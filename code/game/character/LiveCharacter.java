
package project.game.character;

import java.util.ArrayList;
import project.game.Difficulties;
import static project.game.Game.getLevelChoice;
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
    private static float m_coeffDiff;
    private static int m_lastDiceValue; //valeur tirée du dé précédent
    private static int m_lastDiceValidStage; //palier de validation du lancer précédent
    protected ArrayList <Clue> m_clueList = new ArrayList();// tous les personnages vivants peuvent accéder au tableau d'indices (modifient témoignages ou les regardent)

    
    /*$$ CONSTRUCTOR $$*/
    public LiveCharacter(String name, String surname, Sex sex, int age, ArrayList <Clue> clueList) {
        super(name, surname, sex, age);
        m_clueList.addAll(clueList);
        
        //facile : 0.75 | moyen : 1 | difficile : 1.25
        m_coeffDiff = (float) (getLevelChoice() == Difficulties.SIMPLE? 0.75 : (getLevelChoice() == Difficulties.MEDIUM? 1 : 1.25));
    }
    

    /*$$ GETTERS & SETTERS $$*/
    public Clue getClue(int index) {
        return m_clueList.get(index);
    }

    
    public ArrayList <Clue> getClueList() {
        return m_clueList;
    }
    
    
    /*$$ METHODS $$*/
    public abstract void displayStats();
    
   
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
            if (m_lastDiceValue <= M_CRITICAL_SUCCESS) {
                validStage += 10; //application du bonus dû au succès précédent
            }
            //si échec simple au précédent lancer
            if (m_lastDiceValue > m_lastDiceValidStage && m_lastDiceValue < M_CRITICAL_FAILURE) {
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
            System.out.printf("Jet %s : %d / %d\t%s\n", display, diceValue, validStage, result.toString());
        }
        
        
        return result;
    }
   
    
    public static DiceResult rollMultiDice(int[] validStage, String[] display, boolean newThrow) {
        DiceResult action = DiceResult.ERROR;
        for (int i = 0; i < validStage.length; i++) {
            String text = (display != null && i < display.length)? display[i] : null;//y a t'il du texte à afficher pour ce lancer
            boolean previousImpact = (i == 0)? newThrow : false;//(seulement pour le premier lancer) est ce une nouvelle suite de lancers?
            action = rollDice(validStage[i], text, previousImpact);
        }
        return action;
    }
}
