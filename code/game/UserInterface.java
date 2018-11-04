/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game;

/**
 *
 * @author ISEN
 */
public class UserInterface {//majoritairement codé - en cours de débuggage
    protected int m_nbChoices;
    protected static final int M_CONSOLE_SIZE = 20;
    
    
    /*$$ METHODS $$*/
    private String constructList(String[] choicesList) {
        String list = "";
        for (int index = 0; index < choicesList.length; index++) list = new StringBuilder(list).append("Choix ").append(index + 1).append(" : ").append(choicesList[index]).append("\n\t").toString();
        
        return list;
    }
    
    
    public UserInterface clean() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) swipe = new StringBuilder(swipe).append("\n").toString();//concatène tous les retours à la ligne
        System.out.println(swipe);//affiche tout d'un coup
        
        m_nbChoices = 0;
        return this;
    }//end UserInterface clean
    
    
    public UserInterface display(String text){
        System.out.printf("%s\n\n", text);

        m_nbChoices = 0;
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String text, String choice){
        System.out.printf("%s\n\t%s\n\n", text, choice);

        m_nbChoices = 1;
        return this;
    }//end UserInterface display
    
    public UserInterface display(String text, String[] choices){
        this.display(text, constructList(choices));
        
        m_nbChoices = choices.length;
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String speaker, String text, String choice){
        System.out.printf("(%s) %s\n\t%s\n\n", speaker, text, choice);

        m_nbChoices = 1;
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String speaker, String text, String[] choices){
        this.display(speaker, text, constructList(choices));
        
        m_nbChoices = choices.length;
        return this;
    }//end UserInterface display
    
    /*$ avancement modifs $*/
    public UserInterface displayThrow(String[] category, int[] value, int result) {
        for (int i = 0; i < category.length; i++) this.display(new StringBuilder("Lancer de dés pour ").append(category[i]).append("... ").append(String.valueOf(value[i])).toString(), "Continuer");
        System.out.println();
        this.display(((result <= 2)? "Réussite": "Echec") + ((result == 1 || result == 4)? " critique" : ""), "continuer");
        
        return this;
    }//end UserInterface displayThrow
    
    
    public void execContinue() {
        System.out.println("\t Passer à la suite...");
        //saisie bloquante d'un caractère au pif
    }//end void execContinue
    
            
    public int execSingleChoice(String categorie) {
        System.out.println(new StringBuilder("\t Choisissez votre ").append(categorie).append(" : ").toString());

        int choice = 0;
        do choice = /*something*/1; while (1 < choice && choice < m_nbChoices);
        
        return choice;
    }//end int playerSingleChoice
    
    
    public int[] execMultiChoice(){//nécéssaire? peut réutiliser single choices
        int[] tmp = { 0 };
        return tmp;
    }//end int[] playerMultiChoices
}
