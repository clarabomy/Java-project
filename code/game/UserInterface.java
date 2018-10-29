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
    private int m_nbChoices;
    
    
    public UserInterface display(String text, String[] choices){
        System.out.println(new StringBuilder(text).append('\n').toString());
        
        m_nbChoices = choices.length;
        for (int index = 0; index < m_nbChoices; index++) System.out.println(new StringBuilder("Choix ").append(index + 1).append(" : ").append(choices[index]).toString());
        
        return this;
    }//end UserInterface display()
    
    
    public void playerContinue() {
        //saisie bloquante d'un caractère au pif
    }
    
            
    public int playerSingleChoice(String categorie) {
        System.out.println(new StringBuilder("Choisissez votre ").append(categorie).append(" : ").toString());

        int choice = 0;
        do choice = /*something*/1; while (1 < choice && choice < m_nbChoices);
        
        return choice;
    }//end int playerSingleChoice()
    
    
    public int[] playerMultiChoices(){//nécéssaire?
        int[] tmp = { 0 };
        return tmp;
    }//end int[] playerMultiChoices()
}
