/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ISEN
 */
public class Console {
    public Console display(String text, String[] choices){
        System.out.println(new StringBuilder(text).append('\n').toString());
        for (int index = 0; index < choices.length; index++) System.out.println(new StringBuilder("Choix ").append(index + 1).append(" : ").append(choices[index]).toString());
        
        return this;
    }//end Console display()
    
    int playerSingleChoice() {
        /*
        goto(x, y)
            https://stackoverflow.com/questions/1001335/java-gotoxyx-y-for-console-applications
            char escCode = 0x1B;
            int row = 10; int column = 10;
            System.out.print(String.format("%c[%d;%df",escCode,row,column));
        
            utiliser lanterna? classe Screen, environnement semi graphique (ligne de texte seulement) et portable...
        */
        return 0;
    }//end int playerSingleChoice()
    
    int[] playerMultiChoices(){
        int[] tmp = { 0 };
        return tmp;
    }//end int[] playerMultiChoices()
}
