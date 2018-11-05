/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game;

import java.io.IOException;

/**
 *
 * @author ISEN
 */
public class UserInterface {//majoritairement codé - en cours de débug => dernières fonctions
    protected static int m_nbChoices;
    protected static final int M_CONSOLE_SIZE = 20;
    
    
    /*$$ METHODS $$*/
    private String constructList(String[] choicesList) {
        String list = "\t";
        for (int index = 0; index < choicesList.length; index++) list = new StringBuilder(list).append("Choix ").append(index + 1).append(" : ").append(choicesList[index]).append("\n\t").toString();
        
        return list.substring(0, list.length() - 2);//retire dernier \n\t
    }
    
    
    public UserInterface clean() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) swipe = new StringBuilder(swipe).append("\n").toString();//concatène tous les retours à la ligne
        System.out.println(swipe);//affiche tout d'un coup
        
        m_nbChoices = 0;
        return this;
    }//end UserInterface clean
    
    
    public UserInterface display(String text, boolean lineBreak){//version 1
        System.out.printf("%s\n%s", text, (lineBreak? "\n" : ""));

        m_nbChoices = 0;
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String text, String[] choices, boolean lineBreak){//version 2
        System.out.printf("%s\n%s\n%s", text, constructList(choices), (lineBreak? "\n" : ""));
        
        m_nbChoices = choices.length;
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String speaker, String text, boolean lineBreak){//version 3
        System.out.printf("(%s) %s\n%s", speaker, text, (lineBreak? "\n" : ""));
        
        m_nbChoices = 0;
        return this;
    }//end UserInterface display
    
    
    public UserInterface display(String speaker, String text, String[] choices, boolean lineBreak){//version 4
        System.out.printf("(%s) %s\n %s\n%s", speaker, text, constructList(choices), (lineBreak? "\n" : ""));
        
        m_nbChoices = choices.length;
        return this;
    }//end UserInterface display
    
    
    public UserInterface displayThrow(String[] category, int[] value, int result, boolean lineBreak) {
        for (int i = 0; i < category.length; i++) System.out.printf("Lancer de dés pour %s... %d\n", category[i], value[i]);
        System.out.printf("%s%s\n%s", ((result <= 2)? "Réussite": "Echec"), ((result == 1 || result == 4)? " critique" : ""), (lineBreak? "\n" : "") );
        
        m_nbChoices = 0;
        return this;
    }//end UserInterface displayThrow
    
    
    public UserInterface execContinue() {
        //saisie bloquante d'un caractère (au pif?) entrée
        System.out.println("\tContinuer...");
        try {
            System.in.read();
        } 
        catch(IOException e){
        }
        
        return this;
    }//end void execContinue
    
    
    public int execSingleChoice() {//refaire bien
        System.out.printf("\t\tFaites votre choix : ");

        int choice = 0;
        try {
            do choice = System.in.read(); while (1 < choice && choice < m_nbChoices);
        } 
        catch(IOException e){
        }
        
        return choice;
    }//end int playerSingleChoice
    
    
    public int[] execMultiChoice(){//nécéssaire?
        System.out.printf("\t\tFaites vos choix (saisie vide pour continuer) : ");
        
        int[] tmp = { 0 };
        return tmp;
    }//end int[] playerMultiChoices
}
