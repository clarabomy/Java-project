
package javaspector.game;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * Game user interface manager
 * @author Clara BOMY
 */ 
public class UserInterface {
    protected static int m_nbChoices;
    protected static final int M_CONSOLE_SIZE = 50;
    
    /** 
     * Formats a string array
     * @param choicesList   string array to format
     * @return stringList   text to display
     */ 
    private String concatList(String[] choicesList) {
        m_nbChoices = 0;//reinitialize the number of choices
        String list = "\n\t";
        for (String choice : choicesList) {
            list += "(Choix "+ (++m_nbChoices) + ") " + choice + "\n\t";
        }
    
        return list.substring(0, list.length() - 1);//remove the last \n
    }
    
    /** 
     * Cleans the console by passing lines
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface clean() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) {//concatenates all the line breaks
            swipe += "\n";
        }
        System.out.print(swipe);//shows all of a sudden
        
        m_nbChoices = 0;//reinitialize the number of choices
        return this;
    }
    
    /** 
     * Formatted display 1
     * @param text      message to display
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String text){
        System.out.println(text + "\n");

        m_nbChoices = 0;//no choice
        return this;
    }
    
    /** 
     * Formatted display 2
     * @param text      text to display
     * @param choices   message of the possible choices
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String text, String[] choices){
        System.out.println(text + "\n" + concatList(choices));//concatList compte nb de choix
        
        return this;
    }
    
    /** 
     * Formatted display 3
     * @param speaker   speaker designation
     * @param text      message to display
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String speaker, String text){
        System.out.println("(" + speaker + ") " + text + "\n");
        
        m_nbChoices = 0;
        return this;
    }

    /** 
     * Formatted display 4
     * @param speaker   speaker designation
     * @param text      message to display
     * @param choices   list of the possible choices
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String speaker, String text, String[] choices){
        System.out.println("(" + speaker + ") " + text + "\n" + concatList(choices));
        
        return this;
    }
    
    /** 
     * Pause the game
     * @param text      message to display
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface execContinue(String text) {
        System.out.println("   -> " + (text == null? "Continuer" : text) + "...");
        
        try {
            System.in.read();
        } 
        catch(IOException e){//no execution on readed : no possible error
        }
        System.out.println();
        
        return this;
    }
    
    /** 
     * Recovers the player's choice
     * @return choice   index of the choiceList printed before. Between 1 and m_nbChoices
     */ 
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
                keyboard.next();//if incorrect : try again
            }
        } while (correctChoice == false);
        System.out.println("\n");
        
        return choice;
    }
    
    /** 
     * Recovers the player's text
     * @return inputText    text entered by the player
     */ 
    public String execInput() {
        System.out.print(" - ");
        Scanner input = new Scanner(System.in);
        String keyboard = input.nextLine();
        System.out.println("\n");
        return keyboard;
    }
}
