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
    Console window;//provisoire => pas hérité de Game?

    public Investigation(Investigator player, Suspect[] suspectsList, Victim corpse, InvestElement[] elements) {
        this.player = player;
        this.suspectsList = new Suspect[suspectsList.length];
        System.arraycopy(suspectsList, 0, this.suspectsList, 0, suspectsList.length);
        this.corpse = corpse;
        this.elements = new InvestElement[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }//end constructor
    
    
    public void mainMenu() {//menu général de la partie
        boolean gameMenu = false;
        do {
            String[] choicesList = {"Passer en revue les éléments de l'enquête.", 
                                    "M'occuper des suspects.", 
                                    "Lister les indices.\n",
                                    "Voir mon supérieur."}; //menu du jeu
            switch (window.display("Aujourd'hui, je vais...", choicesList).playerSingleChoice()) {//choix unique
                case 0:
                    elementsMenu();
                    break;
                case 1:
                    suspectsMenu();
                    break;
                case 2:
                    cluesMenu();
                    break;
                case 3:
                    gameMenu = true;
                    break;
            }
        } while (!gameMenu);//retours au menu principal (touche échap donne -1 => mécanisme implémentable?)
    }//end mainMenu()
    
    public void elementsMenu() {
        for (int index = 0; index < elements.length; index++) System.out.print(new StringBuilder("Element ").append(index + 1).append(" : ").append(elements[index].display()).toString());
        
        String[] choicesList = {"Analyser l'arme du crime.", 
                                "Autopsier la victime.", 
                                "Chercher des indices dans la scène de crime.\n",
                                "Faire autre chose."};  //menu principal
        int action = window.display("C'est parti pour...", choicesList).playerSingleChoice();
        if (action < choicesList.length) elements[action].analyse(player);
    }//end elementsMenu()
    
    public void suspectsMenu() {
        int nbSuspects = suspectsList.length;
        for (int index = 0; index < nbSuspects; index++) System.out.print(new StringBuilder("Suspect ").append(index + 1).append(" : ").append(elements[index].display()).toString());
        
        String[] choicesList = {"Interroger un suspect.", 
                                "Innocenter un suspect.", 
                                "Arrêter un suspect.\n",
                                "Faire autre chose."};  //menu principal
        int action = window.display("C'est parti pour...", choicesList).playerSingleChoice();
        
        if (action < choicesList.length) {
            System.out.print("Et l'heureux élu est ");
            int choicePlayer = 0;//récupérer saisie joueur
            /*
            do choicePlayer = something() - 1; while (choicePlayer < 0 || choicePlayer > nbSuspects); 
            */

            switch(action) {
                case 0:
                    suspectsList[choicePlayer].BeInterrogated();
                    break;
                case 1:
                    suspectsList[choicePlayer].BeDisculpated();
                    break;
                case 2:
                    suspectsList[choicePlayer].BeArrested();
                    break;
            }
        }
    }//end void suspectsMenu()
    
    public void cluesMenu() {
        int nbClues = player.cluesList.length;
        int indexClues = 0;
        for (int index = 0; index < nbClues; index++) {
            do indexClues++; while (indexClues < nbClues && player.cluesList[indexClues].isFounded == false);
            if (indexClues < nbClues) {
                System.out.print(new StringBuilder("Indice ").append(index + 1).append(" : ").append(player.cluesList[indexClues].display()).toString());
                indexClues++;
            }
        }
        
        String[] choicesList = {"Détailler un témoignage.",
                                "Relier les indices.", 
                                "Chercher des incohérences.\n", 
                                "Faire autre chose."};  //menu principal
        int action = window.display("C'est parti pour...", choicesList).playerSingleChoice();
        
        if (action < choicesList.length) {
            System.out.print("Allez, indice ");
            int choicePlayer = 0;//récupérer saisie joueur
            /*
            do choicePlayer = something() - 1; while (choicePlayer < 0 || choicePlayer > nbSuspects); 
            */

            switch(action) {
                case 0:
                    //player.cluesList[indexClues].something();
                    break;
                case 1:
                    //player.cluesList[indexClues].something();
                    break;
                case 2:
                    //player.cluesList[indexClues].something();
                    break;
            }
        }
    }//end void cluesMenu()
    
}
