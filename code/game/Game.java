
package project.game;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
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
    protected static UserInterface m_console = new UserInterface();//memberOfClass_attributeName
    protected static Difficulties m_levelChoice;
    
    protected Investigation m_currentInvestigation;
    protected static ArrayList<String> m_victimList = new ArrayList();//victimes à l'initialisation
    protected static ArrayList<String> m_weaponList = new ArrayList();//armes à l'initialisation
    protected static ArrayList<String> m_mobileList = new ArrayList();//mobiles à l'initialisation

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
    }
    
    
    /*$$ GETTERS & SETTERS $$*/
    public static Difficulties getLevelChoice() {
        return m_levelChoice;
    }
    
    public static UserInterface getConsole() {
        return m_console;
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
    
    
    /*$$ METHODS $$*/
    private String selectFile(String action) {//charger / supprimer
        FileFilter txtFilesOnly = (File f) -> f.getName().endsWith(".txt"); //équivaut à new FileFilter() { public boolean accept(File f) { return f.getName().endsWith(".txt") ; } };
        File repertoryContent[] = new File("./saves").listFiles(txtFilesOnly);//"." = répertoire courant => va dans répertoire de sauvegardes et récupère noms des fichiers avec extension .txt
        
        int select = m_console.display("Choississez la sauvegarde à " + action + " :", Arrays.toString(repertoryContent), true).execChoice();
        
        return /*"./saves" +*/ repertoryContent[select].getPath();//retourne chemin vers fichier à traiter
    }
    
    
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
        InvestElement weapon = new InvestElement("type d'arme", proofList);
        
        for (int i = 0; i < nbPreuvesSceneDeCrime; i++) {
            proofList = new ArrayList();
            proofList.add(new Proof("scène de crime", "contenu"));
        }
        InvestElement scene = new InvestElement("type d'endroit", proofList);
        
        
        
        //PERSONNAGES
        ArrayList<Suspect> suspectsList = new ArrayList();
        Murderer criminal = new Murderer("nom", "prenom", Sex.HOMME, age, stress, "look", "aspect physique", "motivation");
        suspectsList.add(criminal);
        suspectsList.add(new CrimePartner("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", criminal.getFullName()));
        for (int i = 0; i < nbInnocents; i++) {
            suspectsList.add(new Innocent("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", "entendu", "vu"));
        }
        
        Investigator player = new Investigator(manipulation, intelligence, criminal.getFullName(), corpse.getFullName(), weapon.getType(), scene.getType());
        
        
        
        //ENQUETE
        m_currentInvestigation = new Investigation(player, suspectsList, corpse, weapon, scene);
        m_currentInvestigation.investigationMenu();
    }
  
    
    public void continueInvestigation(){//initialisation version condensée
        File file = new File(selectFile("charger"));
        if (!file.exists()) {
            //throw new exception();//peut pas lire : sort du programme
        }
        
        
        
        //initialise classes avec lecture fichier
        int nbInnocents = 4, nbPreuvesVictime = 3, nbPreuvesArme = 4, nbPreuvesSceneDeCrime = 6;
        int age = 35, manipulation = 60, intelligence = 75, stress = 50, cooperation = 30;
        
        
        
        
        m_levelChoice = Difficulties.MEDIUM;
        
        
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
        InvestElement weapon = new InvestElement("type d'arme", proofList);
        
        for (int i = 0; i < nbPreuvesSceneDeCrime; i++) {
            proofList = new ArrayList();
            proofList.add(new Proof("scène de crime", "contenu"));
        }
        InvestElement scene = new InvestElement("type d'endroit", proofList);
        
        
        
        //PERSONNAGES
        ArrayList<Suspect> suspectsList = new ArrayList();
        Murderer criminal = new Murderer("nom", "prenom", Sex.HOMME, age, stress, "look", "aspect physique", "motivation", "faux alibi", "faux temoignage 1", "faux temoignage 2");
        suspectsList.add(criminal);
        suspectsList.add(new CrimePartner("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", suspectsList.get(0).getFullName(), "faux temoignage 1", "faux temoignage 2"));
        for (int i = 0; i < nbInnocents; i++) {
            suspectsList.add(new Innocent("nom", "prenom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", "entendu", "vu"));
        }
        
        
        ArrayList<Clue> clueList = new ArrayList();
        clueList.add(new Proof("arme du crime", "sang"));
        clueList.add(new Deposition("suspect", "un homme brun", DepositionType.SEEN, false));
        String[] suppositions = {"criminel", "victime", "arme", "mobile"};
        Investigator player = new Investigator(manipulation, intelligence, criminal.getFullName(), corpse.getFullName(), weapon.getType(), scene.getType(), clueList, suppositions);
        
        
        
        //ENQUETE
        m_currentInvestigation = new Investigation(player, suspectsList, corpse, weapon, scene);
        m_currentInvestigation.investigationMenu();
    }
    
    
    public void saveInvestigation() /*throws IOException*/{
        //crée nouveau fichier de sauvegarde
        /*
        int i = 0;
        File file;
        do {
            file = new File("./save/invest" + (++i));//sinon, demande nom au joueur et teste voir si le fichier existe déjà
        } while (file.exists());
        
        file.createNewFile();
        if (!file.exists()) {
            //throw new exception...
        }
        */
        
        //récupère contenu de classes et l'enregistre en fichier
        
        //GAME
        String gameDifficulty = m_levelChoice.toString();
        String[] gameVictimList = (String[]) m_victimList.toArray();
        String[] gameWeaponList = (String[]) m_weaponList.toArray();
        String[] gameMobileList = (String[]) m_mobileList.toArray();
        
        
        //PERSONNAGES
        {
            Investigator investigator = m_currentInvestigation.getInvestigator();
        
            int     manipulationInvestigator    = investigator.getManipulation();
            int     intelligenceInvestigator    = investigator.getIntelligence();

            String  progressInvestigator        = investigator.getProgress();
            String  supposedMurderer            = investigator.getSupposedMurderer();
            String  supposedVictim              = investigator.getSupposedVictim();
            String  supposedWeapon              = investigator.getSupposedWeapon();
            String  supposedMobile              = investigator.getSupposedMobile();
        }
        
        {
            ArrayList<Suspect> suspectsList = m_currentInvestigation.getSuspectsList();
            
            for (Suspect currentSuspect : suspectsList) {
                //common to all
                String  fullNameSuspect     = currentSuspect.getFullName();
                String  sexSuspect          = currentSuspect.getSex().toString();
                String  lookSuspect         = currentSuspect.getLook();
                String  aspectSuspect       = currentSuspect.getAspect();
                int     ageSuspect          = currentSuspect.getAge();
                int     stressSuspect       = currentSuspect.getStress();
                int     cooperationSuspect  = currentSuspect.getCooperation();
                boolean innocentSuspect     = currentSuspect.isConsideredInnocent();

                String  alibiContentSuspect = currentSuspect.getAlibi().getContent();
                boolean alibiSuspectIsLie   = currentSuspect.getAlibi().isLie();

                String  seenContentSuspect  = currentSuspect.getSeen().getContent();
                boolean seenSuspectIsLie    = currentSuspect.getSeen().isLie();

                String  heardContentSuspect = currentSuspect.getHeard().getContent();
                boolean heardSuspectIsLie   = currentSuspect.getHeard().isLie();

                if (currentSuspect instanceof Murderer) {
                    String motiveMurderer = ((Murderer) currentSuspect).getMotive();
                }
            }
        }
        
        
        //ELEMENTS D'ENQUETE
        {
            Victim victim = m_currentInvestigation.getVictim();
            
            String  fullNameVictim  = victim.getFullName();
            String  sexVictim       = victim.getSex().toString();
            int     ageVictim       = victim.getAge();
            
            String deathDateVictim  = victim.getDeathDate();
            String deathCauseVictim = victim.getDeathCause();
            
            for (Proof currentProof : victim.getProofList()) {
                String contentProofVictim = currentProof.getContent();
            }
        }
        
            //crimeWeapon
        for (Proof currentProof : m_currentInvestigation.getCrimeWeapon().getProofList()) {
            String contentProofWeapon = currentProof.getContent();
        }
        String typeWeapon = m_currentInvestigation.getCrimeWeapon().getType();
        
            //crimeScene
        for (Proof currentProof : m_currentInvestigation.getCrimeScene().getProofList()) {
            String contentProofScene = currentProof.getContent();
        }
        String typeScene = m_currentInvestigation.getCrimeScene().getType();
    }
    
    
    public void dropInvestigation(){
        //supprime le fichier 
        File file = new File(selectFile("supprimer"));//récupère nom du fichier à supprimer du dossier de sauvegardes
        if(file.delete()) {
            m_console.display("Partie " + file.getName() + " supprimée.", true);
        }
        else {
            m_console.display("Echec de suppression.", true);
        }
        m_console.display("Retours au menu principal.", false).execContinue();
    }
}
