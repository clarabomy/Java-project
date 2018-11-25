
package javaspector;

import javaspector.game.Game;

/**
 *
 * Main class of the program: it contains the entry point of the program
 * @author Clara BOMY
 */ 
public class Source {
    /** 
     * Main function
     * @param args  command line arguments
     */ 
    public static void main(String[] args) {
        //Game execution
        Game JavaSpector = new Game();
        JavaSpector.gameMenu();
    }
}
