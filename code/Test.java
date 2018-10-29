/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import project.game.investigation.Investigator;
import project.game.investigation.clue.Clue;
import project.game.investigation.clue.Proof;
import project.game.investigation.investElement.InvestElement;


/**
 *
 * @author ISEN
 */
public class Test {
    public static void main(String[] args) {
        int[] tab = {1,2,3};
        InvestElement sang = new InvestElement(tab);
        Proof clue = new Proof(sang, "sang", false);
        Proof clue2 = new Proof(sang, "sang", false);
        Clue[] clue_tab = {clue,clue2};
        Investigator player = new Investigator("Michel", false, 34, 60, 70, 32, clue_tab, "100");
        player.rollDice();
    }
}
