
package project.game;

import java.io.File;
import java.util.ArrayList;
import project.game.character.Investigator;
import project.game.character.Sex;
import project.game.character.Victim;
import project.game.character.suspect.CrimePartner;
import project.game.character.suspect.Innocent;
import project.game.character.suspect.Murderer;
import project.game.character.suspect.Suspect;
import project.game.investigation.Clue;
import project.game.investigation.Deposition;
import project.game.investigation.DepositionType;
import project.game.investigation.InvestElement;
import project.game.investigation.Investigation;
import project.game.investigation.Proof;

/**
 *
 * @author ISEN
 */
public class Game {
    protected Investigation m_currentInvestigation;//memberOfClass_attributeName
    protected static Difficulties m_levelChoice;
    protected UserInterface m_console = new UserInterface();
    
    protected static ArrayList<String> m_victimList = new ArrayList();//victimes à l'initialisation
    protected static ArrayList<String> m_weaponList = new ArrayList();//armes à l'initialisation
    protected static ArrayList<String> m_mobileList = new ArrayList();//mobiles à l'initialisation
    protected static String m_murderer;//à l'initialisation
    protected static String m_victim;//à l'initialisation
    protected static String m_weapon;//à l'initialisation
    protected static String m_mobile;//à l'initialisation
    

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
    }
    
    //pour Test
    public Game(Difficulties levelchoice, String murderer, String victim, String weapon, String mobile) {
        m_levelChoice = levelchoice;
        m_murderer = murderer;
        m_victim = victim;
        m_weapon = weapon;
        m_mobile = mobile;
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public static Difficulties getLevelChoice() {
        return m_levelChoice;
    }
    
    public static int getDifficulty() {
        return m_levelChoice == Difficulties.SIMPLE? 0 : (m_levelChoice == Difficulties.MEDIUM? 1 : 2);
    }
    
    public static ArrayList<String> getWeaponList() {
        return m_weaponList;
    }
    
    public static ArrayList<String> getMobileList() {
        return m_mobileList;
    }
    
    public static ArrayList<String> getVictimList() {
        return m_victimList;
    }
    
    public static String getMurderer() {
        return m_murderer;
    }
    
    public static String getVictim() {
        return m_victim;
    }
    
    public static String getWeapon() {
        return m_weapon;
    }
    
    public static String getMobile() {
        return m_mobile;
    }
    
    /*$$ METHODS $$*/   
    public void gameMenu() {//menu général du jeu
        boolean exitGame = false;
        String[] choicesList = {"Règles du jeu\n",
                                "Commencer une nouvelle enquête",           //nouvelle partie
                                "Reprendre une enquête",                    //charger partie
                                "Déposer un rapport d'enquête (sauvegarde)",//sauvegarder partie
                                "Abandonner une enquête\n\n",               //abandonner partie
                                "Quitter le bureau d'enquête"};             //quitter jeu
        
        do {
            switch (m_console.clean().display("Menu principal", choicesList, false).execChoice()) {
                case 1:
                    gameRules();
                    break;
                case 2:
                    newInvestigation();
                    break;
                case 3:
                    continueInvestigation();
                    break;
                case 4:
                    saveInvestigation();
                    break;
                case 5:
                    dropInvestigation();
                    break;
                case 6:
                    exitGame = true;
                    break;
            }
        } while (!exitGame);//quitte le jeu en choisissant l'option dédiée
    }
    
    
    public void gameRules(){
        //affiche lecture en fichier
        /*
        deux grands axes : 
        personnages
            Enqueteur (capacités)
            Suspects (spécificités des catégories et capacités)
        éléments d'enquête
            Victime (spécificités)
            Arme et Scène du crime (spécificités)
            Indices (spécificités des catégories)
        */
    }
    
    
    public void newInvestigation(){//initialisation version décondensée
        String[] intro = {"Bonjour, inspecteur. Bon, écoute, j'ai trois enquêtes pour toi. Je te laisse choisir celle que tu préfères.",
                            "Tu as fais ton choix? Bien. Voyons voir ce qui t'attend."};
        String[][] choices = {{"Une enquête facile", "Une enquête classique", "Une enquête difficile"},
                                    {"Ouvrir le dossier"}};
        
        //intro
        switch (m_console.display("Votre supérieur", intro[0], choices[0], false).execChoice()) {
            case 1:
                m_levelChoice = Difficulties.SIMPLE;
                break;
            case 2:
                m_levelChoice = Difficulties.MEDIUM;
                break;
            case 3:
                m_levelChoice = Difficulties.DIFFICULT;
                break;
        }
        
        m_console.display("Votre supérieur", intro[1], choices[1], false).execContinue();//appel chaîné : renvoie this en fin de fonction
        
        
        
        //initialise classes avec aléatoire
        int nbInnocents = 4, nbPreuvesVictime = 3, nbPreuvesArme = 4, nbPreuvesSceneDeCrime = 6;
        int age = 35, manipulation = 60, intelligence = 75, stress = 50, cooperation = 30;
        
        //PERSONNAGES
        Investigator player = new Investigator("nom", "prenom", Sex.HOMME, age, manipulation, intelligence);
        
        ArrayList<Suspect> suspectsList = new ArrayList();
        Murderer criminal = new Murderer("nom", "prenom", Sex.HOMME, age, stress, "look", "aspect physique", "motivation");
        suspectsList.add(criminal);
        
        CrimePartner partner = new CrimePartner("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", criminal.getFullName());
        suspectsList.add(partner);
        
        for (int i = 0; i < nbInnocents; i++) {
            Innocent randomGuy = new Innocent("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", "entendu", "vu");
            suspectsList.add(randomGuy);
        }
        
        
        
        //ELEMENTS D'ENQUETE
        ArrayList <Proof> proofList = new ArrayList();
        
        for (int i = 0; i < nbPreuvesVictime; i++) {
            proofList = new ArrayList();
            Proof element = new Proof("victime", "contenu");
            proofList.add(element);
        }
        Victim corpse = new Victim("nom", "prenom", Sex.HOMME, age, "date et heure de la mort", "cause de la mort", proofList);
        
        for (int i = 0; i < nbPreuvesArme; i++) {
            proofList = new ArrayList();
            Proof element = new Proof("arme du crime", "contenu");
            proofList.add(element);
        }
        InvestElement weapon = new InvestElement(proofList);
        
        for (int i = 0; i < nbPreuvesSceneDeCrime; i++) {
            proofList = new ArrayList();
            Proof element = new Proof("scène de crime", "contenu");
            proofList.add(element);
        }
        InvestElement scene = new InvestElement(proofList);
        
        
        
        //ENQUETE
        m_currentInvestigation = new Investigation(player, suspectsList, corpse, weapon, scene);
        m_currentInvestigation.investigationMenu();
    }
  
    
    public void continueInvestigation(){//initialisation version condensée
        //initialise classes avec lecture fichier
        int nbInnocents = 4, nbPreuvesVictime = 3, nbPreuvesArme = 4, nbPreuvesSceneDeCrime = 6;
        int age = 35, manipulation = 60, intelligence = 75, stress = 50, cooperation = 30;
        
        
        
        //PERSONNAGES
        ArrayList<Clue> clueList = new ArrayList();
        clueList.add(new Proof("arme du crime", "sang"));
        clueList.add(new Deposition("suspect", "un homme brun", DepositionType.SEEN, false));
        String[] suppositions = {"criminel", "victime", "arme", "mobile"};
        Investigator player = new Investigator("nom", "prenom", Sex.HOMME, age, manipulation, intelligence, clueList, suppositions);
        
        ArrayList<Suspect> suspectsList = new ArrayList();
        suspectsList.add(new Murderer("nom", "prenom", Sex.HOMME, age, stress, "look", "aspect physique", "motivation", "faux alibi", "faux temoignage 1", "faux temoignage 2"));
        suspectsList.add(new CrimePartner("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", suspectsList.get(0).getFullName(), "faux temoignage 1", "faux temoignage 2"));
        for (int i = 0; i < nbInnocents; i++) {
            suspectsList.add(new Innocent("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", "entendu", "vu"));
        }
        
        
        
        //ELEMENTS D'ENQUETE
        ArrayList <Proof> proofList = new ArrayList();
        
        for (int i = 0; i < nbPreuvesVictime; i++) {
            proofList = new ArrayList();
            proofList.add(new Proof("victime", "contenu"));
        }
        Victim corpse = new Victim("nom", "prenom", Sex.HOMME, age, "date et heure de la mort", "cause de la mort", proofList);
        
        for (int i = 0; i < nbPreuvesArme; i++) {
            proofList = new ArrayList();
            proofList.add(new Proof("arme du crime", "contenu"));
        }
        InvestElement weapon = new InvestElement(proofList);
        
        for (int i = 0; i < nbPreuvesSceneDeCrime; i++) {
            proofList = new ArrayList();
            proofList.add(new Proof("scène de crime", "contenu"));
        }
        InvestElement scene = new InvestElement(proofList);
        
        
        
        //ENQUETE
        m_currentInvestigation = new Investigation(player, suspectsList, corpse, weapon, scene);
        m_currentInvestigation.investigationMenu();
    }
    
    
    public void saveInvestigation(){
        //ecrit en fichier contenu de classes
    }
    
    
    public void dropInvestigation(){
        //supprime le fichier 
        File file = new File("invest.txt");
        if(file.delete()) {
            m_console.display("Partie "+ file.getName() + " supprimée.", true);
        }
        else {
            m_console.display("Echec de suppression.", true);
        }
        m_console.display("Retours au menu principal.", false).execContinue();
    }
}
