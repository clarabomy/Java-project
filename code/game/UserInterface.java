
package project.game;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ISEN
 */
public class UserInterface {
    protected static int m_nbChoices;
    protected static final int M_CONSOLE_SIZE = 40;
    
    
    /*$$ METHODS $$*/
    public UserInterface() {
        m_nbChoices = 0;
    }
    
    private String concatList(String[] choicesList) {
        //afficher choix => crée le string d'affichage
        
        m_nbChoices = 0;
        String list = "\n\t";
        for (String choice : choicesList) {
            list += "(Choix "+ (++m_nbChoices) + ") " + choice + "\n\t";
        }
    
        return list.substring(0, list.length() - 1);//retire dernier \n
    }
    
    
    public UserInterface clean() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) {//concatène tous les retours à la ligne
            swipe += "\n";
        }
        System.out.print(swipe);//affiche tout d'un coup
        
        m_nbChoices = 0;
        return this;
    }
    
    
    public UserInterface display(String text){//version 1
        System.out.println(text + "\n");

        m_nbChoices = 0;
        return this;
    }
    
    
    public UserInterface display(String text, String[] choices){//version 2
        System.out.println(text + "\n" + concatList(choices));//concatList compte nb de choix
        
        return this;
    }
    
    
    public UserInterface display(String speaker, String text){//version 3
        System.out.println("(" + speaker + ") " + text + "\n");
        
        m_nbChoices = 0;
        return this;
    }
    
    
    public UserInterface display(String speaker, String text, String[] choices){//version 4
        System.out.println("(" + speaker + ") " + text + "\n" + concatList(choices));//concatList compte nb de choix
        
        return this;
    }
    
    public UserInterface execContinue(String text) {
        System.out.println("   -> " + (text == null? "Continuer" : text) + "...");
        
        try {
            System.in.read();
        } 
        catch(IOException e){
        }
        System.out.println();
        
        return this;
    }
    
    
    public int execChoice() {
        Scanner keyboard = new Scanner(System.in);
        boolean correctChoice = false;
        int choice = 0;

        do {
            try {
                System.out.printf("\t\tVotre choix : ");
                choice = keyboard.nextInt();
                if (1 <= choice && choice <= m_nbChoices) {
                    correctChoice = true;
                }
            } 
            catch (Exception e) {
                keyboard.next(); 
            }
        } while (correctChoice == false);
        System.out.println("\n");
        
        return choice;
    }
    
    public String execInput() {
        System.out.print(" - ");
        Scanner input = new Scanner(System.in);
        String keyboard = input.nextLine();
        System.out.println("\n");
        return keyboard;
    }
}
