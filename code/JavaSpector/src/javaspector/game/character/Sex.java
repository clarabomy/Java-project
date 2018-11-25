
package javaspector.game.character;

/**
 *
 * Possible values of the sex of a character
 * @author Clara BOMY
 */ 
public enum Sex {FEMME, HOMME;

@Override
    public String toString() {
        return name().toLowerCase();
    }
}
