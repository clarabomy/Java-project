
package project.game.investigation;

/**
 *
 * @author ISEN
 */
public abstract class LiveCharacter extends Character {
    protected static final int M_SIDES = 100;
    protected static final int M_CRITICAL_SUCCESS = 15;
    protected static final int M_CRITICAL_FAILURE = 95;
    protected static int m_bonus; //{30, 20, 10};//simple, medium, difficult
    protected static int m_malus; //{10, 20, 30};//same

    
    /*$$ CONSTRUCTOR $$*/
    public LiveCharacter(String name, String surname, Sex sex, int age) {
        super(name, surname, sex, age);
    }
    
    
    /*$$ METHODS $$*/
    public abstract void displayStats();
    
    public static int rollDice(){
        return((int) (Math.random() * M_SIDES) + 1);  //donne valeur entre 1 et 100
    }//end int rollDice
    
    
    public static int throwSimpleDice(int validThrow) {
        int throwResult = rollDice();
        //System.out.printf("jet : %d\n", throwResult);
        
        if (throwResult <= validThrow && throwResult > M_CRITICAL_SUCCESS) return 2;//succes => plus courant
        else if (throwResult > validThrow && throwResult < M_CRITICAL_FAILURE) return 3;//echec => plus courant
        else if (throwResult <= M_CRITICAL_SUCCESS) return 1;//succès critique => moins courant
        else /*if (throwResult >= M_CRITICAL_FAILURE)*/ return 4;//echec critique => moins courant
    }
    
    
    public static int throwDoubleDices(int validFirstThrow, int validSecondThrow) {
        switch(throwSimpleDice(validFirstThrow)) {
            case 1://succes critique
                return throwSimpleDice(validSecondThrow + m_bonus);
            case 2://succes
                return throwSimpleDice(validSecondThrow);
            case 3://echec
                return throwSimpleDice(validSecondThrow - m_malus);
            case 4://echec critique
                return 4;
        }
        
        return -1;//erreur
        
        /*
        int firstThrow = rollDice(), secondThrow = rollDice(), action = 0;
        
        
        //version moyenne
        {
            if (firstThrow >= M_CRITICAL_FAILURE) action = 4;
            else if ((firstThrow <= validFirstThrow && 
                secondThrow <= validSecondThrow + ((firstThrow <= M_CRITICAL_SUCCESS)? m_bonus : 0))
                ||
                (firstThrow > validFirstThrow && //firstThrow < M_CRITICAL_FAILURE && déjà vérifié
                secondThrow <= validSecondThrow - m_malus)) {
                action = (secondThrow <= M_CRITICAL_SUCCESS)? 1 : 2 ;
            }
            else if ((firstThrow <= validFirstThrow &&
                secondThrow > validSecondThrow + ((firstThrow < M_CRITICAL_SUCCESS)? m_bonus : 0))
                ||
                (firstThrow > validFirstThrow && //firstThrow < M_CRITICAL_FAILURE && déjà vérifié
                secondThrow > validSecondThrow - m_malus)) {
                action = (secondThrow < M_CRITICAL_FAILURE)? 3 : 4 ;
            }
        }
        
        
        //version longue
        {
            if (firstThrow < validFirstThrow) {
                //si réussite critique au premier : bonus pour le second
                if (secondThrow < validSecondThrow + ((firstThrow <= M_CRITICAL_SUCCESS)? m_bonus : 0)) {
                    if (secondThrow < M_CRITICAL_SUCCESS) action = 1;//réussite critique
                    else action = 2;//réussite simple
                }
                else {
                    if (secondThrow < M_CRITICAL_FAILURE) action = 3;//échec simple
                    else action = 4;//échec critique
                }
            }
            else {
                if (firstThrow < M_CRITICAL_FAILURE) {
                    //si échec simple pour le premier, malus pour le second
                    if (secondThrow < validSecondThrow - m_malus) {
                        if (secondThrow < M_CRITICAL_SUCCESS) action = 1;//réussite critique
                        else action = 2;//réussite simple
                    }
                    else {
                        if (secondThrow < M_CRITICAL_FAILURE) action = 3;//échec simple
                        else action = 4;//échec critique
                    }
                }
                else action = 4;//échec critique
            }
        }
        
        
        //affiche pour debug
        String[] category = {"la cohérence", "la crédibilité"};
        int[] result = {firstThrow, secondThrow};
        m_console.displayThrow(category, result, action).execContinue();
        
        return action;
        */
    }
    
    public static int throwTripleDices(int validFirstThrow, int validSecondThrow, int validThirdThrow) {//utile?
        switch(throwDoubleDices(validFirstThrow, validSecondThrow)) {
            case 1://succes critique
                return throwSimpleDice(validThirdThrow + m_bonus);
            case 2://succes
                return throwSimpleDice(validThirdThrow);
            case 3://echec
                return throwSimpleDice(validThirdThrow - m_malus);
            case 4://echec critique
                return 4;
        }
        
        return -1;//erreur
    }
}
