
package javaspector.game;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * Utilité / fonctionnement de la classe
 * @author Clara BOMY
 */ 
public class UserInterface {
    protected static int m_nbChoices;
    protected static final int M_CONSOLE_SIZE = 50;
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param choicesList   explications
     * @return stringList   explications
     */ 
    private String concatList(String[] choicesList) {
        //afficher choix => crée le string d'affichage
        
        m_nbChoices = 0;
        String list = "\n\t";
        for (String choice : choicesList) {
            list += "(Choix "+ (++m_nbChoices) + ") " + choice + "\n\t";
        }
    
        return list.substring(0, list.length() - 1);//retire dernier \n
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface clean() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) {//concatène tous les retours à la ligne
            swipe += "\n";
        }
        System.out.print(swipe);//affiche tout d'un coup
        
        m_nbChoices = 0;
        return this;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param text      explications
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String text){//version 1
        System.out.println(text + "\n");

        m_nbChoices = 0;
        return this;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param text      explications
     * @param choices   explications
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String text, String[] choices){//version 2
        System.out.println(text + "\n" + concatList(choices));//concatList compte nb de choix
        
        return this;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param speaker   explications
     * @param text      explications
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String speaker, String text){//version 3
        System.out.println("(" + speaker + ") " + text + "\n");
        
        m_nbChoices = 0;
        return this;
    }

    /** 
     * Utilité / fonctionnement de la méthode
     * @param speaker   explications
     * @param text      explications
     * @param choices   explications
     * @return console  sends the reference to chain the calls
     */ 
    public UserInterface display(String speaker, String text, String[] choices){//version 4
        System.out.println("(" + speaker + ") " + text + "\n" + concatList(choices));//concatList compte nb de choix
        
        return this;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @param text      explications
     * @return console  sends the reference to chain the calls
     */ 
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
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @return choice   explications
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
                keyboard.next(); 
            }
        } while (correctChoice == false);
        System.out.println("\n");
        
        return choice;
    }
    
    /** 
     * Utilité / fonctionnement de la méthode
     * @return inputText    explications
     */ 
    public String execInput() {
        System.out.print(" - ");
        Scanner input = new Scanner(System.in);
        String keyboard = input.nextLine();
        System.out.println("\n");
        return keyboard;
    }
}
