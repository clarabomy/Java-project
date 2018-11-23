
package project.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    protected static String m_saveFolderPath = ".\\src\\project\\savesGame\\";//chemin d'accès au dossier de sauvegardes
    protected static String m_saveFileExtension = ".sv";
    protected static UserInterface m_console = new UserInterface();//memberOfClass_attributeName
    protected static Difficulties m_levelChoice;
    
    protected Investigation m_currentInvestigation = null;
    protected static ArrayList<String> m_victimList = new ArrayList();//victimes à l'initialisation
    protected static ArrayList<String> m_weaponList = new ArrayList();//armes à l'initialisation
    protected static ArrayList<String> m_mobileList = new ArrayList();//mobiles à l'initialisation

    
    /*$$ CONSTRUCTOR $$*/
    public Game() {
        
    }
    
    private String createFullName() {
        String text;
        text = "Prenom Nom";
        return text;
    }
    
    /*$$ GETTERS & SETTERS $$*/
    public static Difficulties getLevelChoice() {
        return m_levelChoice;
    }
    
    public static UserInterface getConsole() {
        return m_console;
    }
    
    public static ArrayList<String> getVictimList() {
        return m_victimList;
    }
    
    public static ArrayList<String> getWeaponList() {
        return m_weaponList;
    }
    
    public static ArrayList<String> getMobileList() {
        return m_mobileList;
    }
    
    
    /*$$ METHODS $$*/
    public static String[] convertArrayList(ArrayList <String> choices) {
        String[] list = new String[choices.size()];
        
        int i = 0;
        for (String current : choices) {
            list[i++] = current;
        }
        
        return list;
    }
    
    
    public void gameMenu() {
        boolean exitGame = false;
        do {
            ArrayList choicesList = new ArrayList();
            choicesList.add("Commencer une nouvelle enquête");  //nouvelle partie
            choicesList.add("Reprendre une enquête");           //charger partie
            if (this.m_currentInvestigation != null) {          //sauvegarder partie
                choicesList.add("Déposer un rapport d'enquête (sauvegarde)");
            }
            choicesList.add("Abandonner une enquête\n\n");      //abandonner partie
            choicesList.add("Règles du jeu");
            choicesList.add("Quitter le bureau d'enquête");     //quitter jeu
        
            switch (m_console.clean().display("Standardiste", "Bienvenue au Bureau d'enquête. Que puis-je pour vous?", convertArrayList(choicesList), true).execChoice()) {
                case 1:
                    newInvestigation();
                    gameRules();
                    break;
                case 2:
                    continueInvestigation();
                    break;
                case 3:
                    if (this.m_currentInvestigation != null) {
                        saveInvestigation();
                    }
                    else {
                        dropInvestigation();
                    }
                    break;
                case 4:
                    if (this.m_currentInvestigation != null) {
                        dropInvestigation();
                    }
                    else {
                        gameRules();
                    }
                    break;
                case 5:
                    if (this.m_currentInvestigation != null) {
                        gameRules();
                    }
                    else {
                        exitGame = true;
                    }
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
    
    private String[] getSaveFilesName() {
        ArrayList <String> saveList = new ArrayList();
        FileFilter saveFilesOnly = (File f) -> f.getName().endsWith(m_saveFileExtension); //équivaut à new FileFilter() { public boolean accept(File f) { return f.getName().endsWith(".txt") ; } };
        
        for (File fichier : new File(m_saveFolderPath).listFiles(saveFilesOnly)) {//"." = répertoire courant => va dans répertoire de sauvegardes et récupère noms des fichiers avec extension .txt
            String nom = fichier.toString();
            saveList.add(nom.substring(nom.lastIndexOf("\\") + 1, nom.lastIndexOf(".")));//retire chemin et extension
        }
        return convertArrayList(saveList);
    }
    
    private String selectFile(String action) {//charger / supprimer
        String[] saveList = getSaveFilesName();
        int select = m_console.display("Choississez la sauvegarde à " + action + " :", saveList, false).execChoice() - 1;
        
        return m_saveFolderPath + saveList[select] + m_saveFileExtension;//retourne chemin vers fichier à traiter
    }
    
    public void newInvestigation() {
        //etape 1 : nom perso (nom fichier)
        String fullName = null;
        m_console.clean().display("Vous", "Je m'appelle ", true);
        do {
            fullName = m_console.execSaisie();
            if (Arrays.asList(getSaveFilesName()).contains(fullName)) {//si nom déjà utilisé (nom enquêteur = nom fichier save)
                fullName = null;
                m_console.display("Vous", "Mais non, bien sûr! Je ne m'appelle pas comme ça, mon vrai nom, c'est ", true);
            }
        } while (fullName == null);
        
        
        //etape 2 : sexe perso
        Sex sex = null;
        String[] sexList = {"Un talentueux inspecteur.", "Une talentueuse inspectrice."};
        switch (m_console.display("Vous", "Je suis", sexList, true).execChoice()) {
            case 1:
                sex = Sex.HOMME;
                break;
            case 2:
                sex = Sex.FEMME;
                break;
        }
        
        
        //etape 3 : difficulté enquête
        String[] diffList = {"Basiques.", "Classiques.", "Avancées."};
        switch (m_console.display("Vous", "Et je suis tout à fait capable de résoudre des enquêtes", diffList, true).execChoice()) {
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
        
        
        //etape 4 : initialisation
        m_console.display("Vous", "Alors voyons voir les nouvelles enquêtes !", true).execContinue("Vous choisissez une affaire");
        {
            //initialise classes avec aléatoire
            int nbInnocents = 4, nbPreuvesVictime = 3, nbPreuvesArme = 4, nbPreuvesSceneDeCrime = 6;
            int age = 35, manipulation = 60, intelligence = 75, stress = 50, cooperation = 30;


            //ELEMENTS D'ENQUETE
            ArrayList <Proof> proofList = new ArrayList();

            for (int i = 0; i < nbPreuvesVictime; i++) {
                proofList = new ArrayList();
                proofList.add(new Proof("victime", "contenu"));
            }
            Victim corpse = new Victim("Prenom Nom", Sex.HOMME, age, "date et heure de la mort", "cause de la mort", proofList);

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
            Murderer criminal = new Murderer("prenom criminel", Sex.HOMME, age, stress, "look", "aspect physique", "motivation");
            suspectsList.add(criminal);
            suspectsList.add(new CrimePartner("prenom partner", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", criminal.getFullName()));
            for (int i = 0; i < nbInnocents; i++) {
                suspectsList.add(new Innocent("prenom innocent" + i, Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", "entendu", "vu"));
            }
            Collections.shuffle(suspectsList);//melanger tableau

            Investigator player = new Investigator("fullName", sex, manipulation, intelligence, criminal.getFullName(), corpse.getFullName(), weapon.getType(), scene.getType());



            //ENQUETE
            m_currentInvestigation = new Investigation(player, suspectsList, corpse, weapon, scene);
        }
        
        
        //etape 5 : lancer jeu
        m_console.display("Vous", "Allez, " + fullName + ". Il faut se lancer, maintenant.", true).execContinue("Vous ouvrez le dossier"); 
        m_currentInvestigation.investigationMenu();
    }
  
    
    
    public void continueInvestigation(){//initialisation version condensée
        ArrayList <String> saveData = new ArrayList();
        {
            //lecture (charge données)
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectFile("charger")));
                String line;

                while ((line = reader.readLine()) != null) {
                    saveData.add(line);
                }

                reader.close();
            }
            catch (IOException e) {
                System.out.println("File not found");
            }
        }
        
        
        //initialisation (décharge données)
        {
            /*

            ...

            */
        }
        
        
        
        //$$$$$$$$$$$$$$$$$$
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
        Victim corpse = new Victim("prenom nom", Sex.HOMME, age, "date et heure de la mort", "cause de la mort", proofList);
        
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
        Murderer criminal = new Murderer("prenom nom", Sex.HOMME, age, stress, "look", "aspect physique", "motivation", "faux alibi", "faux temoignage 1", "faux temoignage 2");
        suspectsList.add(criminal);
        suspectsList.add(new CrimePartner("prenom nom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", suspectsList.get(0).getFullName(), "faux temoignage 1", "faux temoignage 2"));
        for (int i = 0; i < nbInnocents; i++) {
            suspectsList.add(new Innocent("prenom nom", Sex.HOMME, age, stress, cooperation, "look", "aspect physique", "alibi", "entendu", "vu"));
        }
        
        
        ArrayList<Clue> clueList = new ArrayList();
        clueList.add(new Proof("arme du crime", "sang"));
        clueList.add(new Deposition("suspect", "un homme brun", DepositionType.SEEN, false));
        String[] suppositions = {"criminel", "victime", "arme", "mobile"};
        Investigator player = new Investigator("prenom nom", Sex.FEMME, manipulation, intelligence, criminal.getFullName(), corpse.getFullName(), weapon.getType(), scene.getType(), clueList, suppositions);
        
        
        
        //ENQUETE
        m_currentInvestigation = new Investigation(player, suspectsList, corpse, weapon, scene);
        m_currentInvestigation.investigationMenu();
    }
        
    public void saveInvestigation() {
        ArrayList <String> gameData = new ArrayList();
        //récupération (charge données)
        //GAME
        gameData.add("fileName : " + m_currentInvestigation.getInvestigator().getFullName());
        gameData.add("gameDifficulty : " + m_levelChoice.toString());
        
        for (String element  : convertArrayList(m_victimList)) {
            gameData.add("gameVictimList : " + element);
        }
        for (String element  : convertArrayList(m_weaponList)) {
            gameData.add("gameWeaponList : " + element);
        }
        for (String element  : convertArrayList(m_mobileList)) {
            gameData.add("gameMobileList : " + element);
        }
        //END GAME
        
        
        //CHARACTERS
        //Investigator
        Investigator investigator = m_currentInvestigation.getInvestigator();

        gameData.add("SexInvestigator : " + investigator.getSex());
        gameData.add("manipulationInvestigator : " + investigator.getManipulation());
        gameData.add("intelligenceInvestigator : " + investigator.getIntelligence());
        gameData.add("supposedMurderer : " + investigator.getSupposedMurderer());
        gameData.add("supposedVictim   : " + investigator.getSupposedVictim());
        gameData.add("supposedWeapon   : " + investigator.getSupposedWeapon());
        gameData.add("supposedMobile   : " + investigator.getSupposedMobile());
        
        for (Clue currentClue : investigator.getClueList()) {
            gameData.add("contentClue : " + currentClue.getContent());
        }


        //Suspects
        ArrayList<Suspect> suspectsList = m_currentInvestigation.getSuspectsList();

        for (Suspect currentSuspect : suspectsList) {
            //common to all
            gameData.add("classSuspect : " + currentSuspect.getClass().getSimpleName());
            gameData.add("fullNameSuspect : " + currentSuspect.getFullName());
            gameData.add("ageSuspect      : " + currentSuspect.getAge());
            gameData.add("sexSuspect      : " + currentSuspect.getSex().toString());
            gameData.add("lookSuspect     : " + currentSuspect.getLook());
            gameData.add("aspectSuspect   : " + currentSuspect.getAspect());
            gameData.add("stressSuspect      : " + currentSuspect.getStress());
            gameData.add("cooperationSuspect : " + currentSuspect.getCooperation());
            gameData.add("innocentSuspect    : " + currentSuspect.isConsideredInnocent());

            gameData.add("alibiContentSuspect : " + (currentSuspect.getAlibi() != null? currentSuspect.getAlibi().getContent() : "null"));
            gameData.add("alibiSuspectIsLie   : " + (currentSuspect.getAlibi() != null? currentSuspect.getAlibi().isLie() : "null"));
            gameData.add("seenContentSuspect  : " + (currentSuspect.getSeen() != null? currentSuspect.getSeen().getContent() : "null"));
            gameData.add("seenSuspectIsLie    : " + (currentSuspect.getSeen() != null? currentSuspect.getSeen().isLie() : "null"));
            gameData.add("heardContentSuspect : " + (currentSuspect.getHeard() != null? currentSuspect.getHeard().getContent() : "null"));
            gameData.add("heardSuspectIsLie   : " + (currentSuspect.getHeard() != null? currentSuspect.getHeard().isLie() : "null"));

            if (currentSuspect instanceof Murderer) {
                gameData.add("motiveMurderer : " + ((Murderer) currentSuspect).getMotive());
            }
        }
        //END CHARACTERS
        
        //INVESTIGATION ELEMENTS
        //Victim
        Victim victim = m_currentInvestigation.getVictim();

        gameData.add("fullNameVictim : " + victim.getFullName());
        gameData.add("sexVictim      : " + victim.getSex().toString());
        gameData.add("ageVictim      : " + victim.getAge());
        gameData.add("deathDateVictim  : " + victim.getDeathDate());
        gameData.add("deathCauseVictim : " + victim.getDeathCause());

        for (Proof currentProof : victim.getProofList()) {
            gameData.add("contentProofVictim : " + currentProof.getContent());
        }

        
        //crimeWeapon
        gameData.add("typeWeapon : " + m_currentInvestigation.getCrimeWeapon().getType());
        for (Proof currentProof : m_currentInvestigation.getCrimeWeapon().getProofList()) {
            gameData.add("contentProofWeapon : " + currentProof.getContent());
        }

        
        //crimeScene
        gameData.add("typeScene : " + m_currentInvestigation.getCrimeScene().getType());
        for (Proof currentProof : m_currentInvestigation.getCrimeScene().getProofList()) {
            gameData.add("contentProofScene : " + currentProof.getContent());
        }
        //END INVESTIGATION ELEMENTS
        
        
        //écriture (décharge données)
        try {
            m_console.display("Sauvegarde en cours...", true);
            String fileName = m_saveFolderPath + m_currentInvestigation.getInvestigator().getFullName()+ m_saveFileExtension;
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            
            for (String content : gameData) {
                writer.println(content);
            }
            
            writer.close();
        }
        catch (IOException e){
            m_console.display("Erreur dans l'enregistrement de la partie.", true).execContinue(null);
        }
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
        m_console.display("Retours au menu principal.", false).execContinue(null);
    }
}
