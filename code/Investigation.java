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
public class Investigation {
    Investigator player;
    Suspect[] suspectsList;
    Victim corpse;
    InvestElement[] elements;

    public Investigation(Investigator player, Suspect[] suspectsList, Victim corpse, InvestElement[] elements) {
        this.player = player;
        this.suspectsList = new Suspect[suspectsList.length];
        System.arraycopy(suspectsList, 0, this.suspectsList, 0, suspectsList.length);
        this.corpse = corpse;
        this.elements = new InvestElement[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }
    
    
}
