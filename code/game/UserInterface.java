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
public class UserInterface {
    protected int m_nbChoices;
    protected static final int M_CONSOLE_SIZE = 20;
    
    
    /*$$ METHODS $$*/
    private String constructPerso(String perso) {
        return new StringBuilder("(").append(perso).append(") ").toString();
    }
    
    public UserInterface clean() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) swipe = new StringBuilder("\n").append("\n").toString();//concatène tous les retours à la ligne
        System.out.println(swipe);//affiche tout d'un coup
        return this;
    }//end UserInterface clean
    
    
    public UserInterface display(String text){
        System.out.println(text);

        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String text, String choice){
        System.out.println(new StringBuilder(text).append('\n').append(choice).toString());

        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String perso, String text, String choice){
        System.out.println(new StringBuilder(constructPerso(perso)).append(text).append('\n').append(choice).toString());

        return this;
    }//end UserInterface display
    
    public UserInterface display(String text, String[] choices){
        System.out.println(new StringBuilder(text).append('\n').toString());
        
        m_nbChoices = choices.length;
        for (int index = 0; index < m_nbChoices; index++) System.out.println(new StringBuilder("Choix ").append(index + 1).append(" : ").append(choices[index]).toString());
        
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String perso, String text, String[] choices){
        System.out.println(new StringBuilder(constructPerso(perso)).append(text).append('\n').toString());
        
        m_nbChoices = choices.length;
        for (int index = 0; index < m_nbChoices; index++) System.out.println(new StringBuilder("Choix ").append(index + 1).append(" : ").append(choices[index]).toString());
        
        return this;
    }//end UserInterface display
    
    
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
