
package project;

import project.game.Game;


/**
 *
 * @author ISEN
 */
public class Source {
    public static void main(String[] args) {
        //Exécution du jeu
        Game thread = new Game();
        thread.gameMenu();
    }
}
