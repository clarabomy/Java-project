/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game;

import java.util.ArrayList;
import java.util.Collections;
import static project.game.Game.m_levelChoice;
import project.game.character.Investigator;
import static project.game.character.LiveCharacter.dropClueList;
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
public class DataManager {
    protected final int m_nbInnocentsByLevel = 3;
    protected final int m_ageMin = 15;
    protected final int m_ageMax = 80 - m_ageMin;
    
    
    protected static String[] m_weaponList = {
        "couteau",
        "batte de baseball",
        "revolver",
        "pelle",
        "pioche"
    };
    protected static String[] m_mobileList = {
        "la folie",
        "l'argent",
        "l'adrénaline",
        "le pouvoir"
    };
    protected String[] m_manList = {
        "Antoine",
        "Thomas",
        "Luc",
        "Paul",
        "Pierre",
        "Guillaume",
        "Alexandre"
    };
    protected String[] m_whomanList = {
        "Claire",
        "Lucie",
        "Alice",
        "Morgane",
        "Camille",
        "Laure",
        "Marine"
    };
    protected String[] m_nameList = {
        "Juzeau",
        "Dael",
        "Dang",
        "Morelle",
        "Deolivera",
        "Pasquier",
        "Fourmond",
        "Cordier",
        "Boore"
    };
    protected String[] m_lookList = {
        "classe",
        "casual",
        "négligé",
        "gothique"                  
    };
    protected String[] m_aspectList = {
        "grand",
        "petit",
        "mince",
        "corpulent"
    };
    protected String[] m_causeList = {
        "hémorragie interne",
        "blessures profondes",
        "arrêt cardiaque"
    };
    protected String[] m_proofContentList = {
        "tâches de sang",
        "empreintes digital",
        "mégot de cigarette",
        "ticket de métro"
    };
    
    public DataManager() {
    }
    
    
    public static String createDeposition(DepositionType type, Murderer murderer) {
        String text = "";
        switch(type) {
            case ALIBI:
                String[] activity   = {"J'ai travaillé", 
                                        "Je me suis reposé(e)", 
                                        "J'ai mangé", 
                                        "Je suis sorti(e)", 
                                        "J'étais"},
                           place   = {"au restaurant", 
                                        "à l'hotel", 
                                        "chez moi", 
                                        "dans un café", 
                                        "dans un parc"},
                           company = {"avec ma femme", 
                                       "avec mes enfants",
                                       "avec mes parents",
                                       "avec mes amis",
                                       "avec mes collègues de bureau"};
         
                text = activity[(int) (Math.random() * activity.length)] + " " + place[(int) (Math.random() * place.length)] + " " + company[(int) (Math.random() * company.length)] + ".";
                break;
                
            case SEEN:
                String[] seen = {"une personne ayant un look",
                                 "une personne qui était",
                                 "une personne"}; 
                String[] uselessSeen = {"un chat trop mignon",
                                    "un mec trop beau",
                                    "un camion de pompier"};
                
                int indexSeen = (int) (Math.random() * seen.length);
                
                text += " " + (Math.random() < 0.5? seen[indexSeen] : uselessSeen[indexSeen]) + " ";
                switch (indexSeen) {
                    case 0:
                        text += murderer.getLook();
                        break;
                    case 1:
                        text += murderer.getAspect();
                        break;
                    case 2:
                        text += murderer.getAge() < 40 ? "jeune" : "âgée";
                        break;
                }
                break;
                
            case HEARD:
                String[] heard = {"une voix de",
                                 "un coup de feu",
                                 "des cris"}; 
                String[] uselessHeard = {"un enfant pleurer",
                                    "un chien aboyer",
                                    "des rires"};
                int indexHeard = (int) (Math.random() * heard.length);
                
                text += " " + (Math.random() < 0.5? heard[indexHeard] : uselessHeard[indexHeard]) + " ";
                if (indexHeard == 0) { text += murderer.getSex().toString(); }
                break;
        }
        text += ".";
        return text;
    }
    
    private String createFullName(Sex genre) {
        String surname = (genre == Sex.HOMME? m_manList : m_whomanList)[(int) (Math.random() * (genre == Sex.HOMME? m_manList : m_whomanList).length)];
        return surname + ' ' + m_nameList[(int) (Math.random() * m_nameList.length)];
    }
    
    
    public Investigation createGame(String fullName, Sex gender) {
        dropClueList();
        
        //variables
        Sex genre;
        int age, stress, coop, manip, intel;
        String look, aspect, motivation, deathDate, deathCause;
        ArrayList<Suspect> suspectsList = new ArrayList();
        ArrayList <Proof> proofList;

        //criminel
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age = (int) (m_ageMin + Math.random() * m_ageMax);
        stress = (int) (m_levelChoice == Difficulty.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        look        = m_lookList[(int) (Math.random() * m_lookList.length)];
        aspect      = m_aspectList[(int) (Math.random() * m_aspectList.length)];
        motivation  = m_mobileList[(int) (Math.random() * m_mobileList.length)];
        Murderer criminal = new Murderer(createFullName(genre), genre, age, stress, look, aspect, motivation);
        suspectsList.add(criminal);

        //partenaire de crime
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age = (int) (m_ageMin + Math.random() * m_ageMax);
        stress  = (int) (m_levelChoice == Difficulty.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        coop    = (int) (m_levelChoice == Difficulty.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        look        = m_lookList     [(int) (Math.random() * m_lookList.length)];
        aspect      = m_aspectList[(int) (Math.random() * m_aspectList.length)];
        suspectsList.add(new CrimePartner(createFullName(genre), genre, age, stress, coop, look, aspect, createDeposition(DepositionType.ALIBI, criminal), criminal.getFullName()));

        //victime
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age = (int) (m_ageMin + Math.random() * m_ageMax);
        deathDate = "il y a " + (int) (2 + Math.random() * 5) + " jours, à " + (int) (Math.random() * 23) + "h" + (int) (Math.random() * 59);
        deathCause = m_causeList[(int) (Math.random() * m_causeList.length)];
        proofList = new ArrayList();
        for (int i = 0; i < (int) (Math.random() * 6); i++) {//nbProofVictim
            proofList.add(new Proof("Victime", m_proofContentList[i]));
        }
        Victim corpse = new Victim(createFullName(genre), genre, age, deathDate, deathCause, proofList);

        //arme
        proofList = new ArrayList();
        for (int i = 0; i < (int) (Math.random() * 6 + 1); i++) {//nbProofWeapon
            proofList.add(new Proof("Arme du crime", m_proofContentList[i]));
        }
        InvestElement weapon = new InvestElement(m_weaponList[(int) (Math.random() * m_weaponList.length)], proofList);

        //scene de crime
        proofList = new ArrayList();
        for (int i = 0; i < (int) (Math.random() * 6 + 1); i++) {//nbProofScene
            proofList.add(new Proof("Scène de crime", m_proofContentList[i]));
        }
        InvestElement scene = new InvestElement("Scène de crime", proofList);

        //innocents
        int nbInnocents = m_nbInnocentsByLevel * (m_levelChoice == Difficulty.SIMPLE? 1 : (m_levelChoice == Difficulty.MEDIUM? 2 : 3));
        for (int i = 0; i < nbInnocents; i++) {
            genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
            age = (int) (m_ageMin + Math.random() * m_ageMax);
            stress  = (int) (m_levelChoice == Difficulty.SIMPLE? 80 - Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 70 - Math.random() * 30 : 60 - Math.random() * 30));
            coop    = (int) (m_levelChoice == Difficulty.SIMPLE? 80 - Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 70 - Math.random() * 30 : 60 - Math.random() * 30));
            look        = m_lookList     [(int) (Math.random() * m_lookList.length)];
            aspect      = m_aspectList[(int) (Math.random() * m_aspectList.length)];
            suspectsList.add(new Innocent(createFullName(genre), genre, age, stress, coop, look, aspect, false, createDeposition(DepositionType.ALIBI, criminal), createDeposition(DepositionType.HEARD, criminal), createDeposition(DepositionType.SEEN, criminal)));
        }

        //enquêteur
        manip   = (int) (m_levelChoice == Difficulty.SIMPLE? 60 + Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 20 + Math.random() * 50));
        intel   = (int) (m_levelChoice == Difficulty.SIMPLE? 60 + Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 20 + Math.random() * 50));
        Investigator player = new Investigator(fullName, gender, manip, intel, null);
        
        //enquête
        Collections.shuffle(suspectsList);//melanger tableau
        return new Investigation(player, suspectsList, corpse, weapon, scene);
    }
    
    public Investigation importGame(ArrayList <String> saveData) {//charger
        dropClueList();
        
        Investigator player = null;
        ArrayList<Suspect> suspectsList = null;
        Victim corpse = null;
        InvestElement weapon = null, scene = null;
        
        //découpe en bloc pour limiter les comparaisons (derniers éléments)
        int index = 0;
        String key, value;
        do {//parcourir tous les blocs
            switch (saveData.get(index++)) {//rentrer dans le bloc
                case "GLOBAL":
                    value = saveData.get(index).split(":")[1];
                    m_levelChoice = value.equals("SIMPLE")? Difficulty.SIMPLE : value.equals("MEDIUM")? Difficulty.MEDIUM: Difficulty.DIFFICULT;
                    break;
                case "SUSPECTS":
                    suspectsList = new ArrayList();
                    int indexPartner = 0, indexMurderer = 0;
                        
                    do {//s'occuper de tout le bloc SUSPECTS
                        String classSuspect = saveData.get(index++).split(":")[1];
                        
                        String name = "", look = "", aspect = "", motive = "";
                        int age = 0, stress = 0, coop = 0;
                        Sex gender = null;
                        boolean innocent = false;
                        String alibi = null, seen = null, heard = null;

                        do {
                            value = saveData.get(index).split(":")[1];
                            
                            switch(saveData.get(index).split(":")[0]) {
                                case "fullNameSuspect":
                                    name = value;
                                    break;
                                case "ageSuspect     ":
                                    age = Integer.parseInt(value);
                                    break;
                                case "sexSuspect     ":
                                    gender = value.toUpperCase().equals("HOMME")? Sex.HOMME : Sex.FEMME;
                                    break;
                                case "lookSuspect    ":
                                    look = value;
                                    break;
                                case "aspectSuspect  ":
                                    aspect = value;
                                    break;
                                case "stressSuspect     ":
                                    stress = Integer.parseInt(value);
                                    break;
                                case "cooperationSuspect":
                                    coop = Integer.parseInt(value);
                                    break;
                                case "innocentSuspect   ":
                                    innocent = Boolean.parseBoolean(value);
                                    break;
                                case "alibiSuspect":
                                    alibi = value.equals("null")? null : value;
                                    break;
                                case "seenSuspect ":
                                    seen = value.equals("null")? null : value;
                                    break;
                                case "heardSuspect":
                                    heard = value.equals("null")? null : value;;                                  
                                    break;
                                case "motiveMurderer":
                                    motive = value;
                                    break;
                            }
                        } while (!saveData.get(++index).split(":")[0].equals("classSuspect") && !saveData.get(index).equals("VICTIM"));
                        
                        switch (classSuspect) {
                            case "Murderer":
                                indexMurderer = suspectsList.size();
                                suspectsList.add(new Murderer(name, gender, age, stress, look, aspect, innocent, motive, alibi, heard, seen));
                                break;
                            case "CrimePartner":
                                indexPartner = suspectsList.size();
                                suspectsList.add(new CrimePartner(name, gender, age, stress, coop, look, aspect, innocent, alibi, heard, seen));
                                break;
                            case "Innocent":
                                suspectsList.add(new Innocent(name, gender, age, stress, coop, look, aspect, innocent, alibi, heard, seen));
                                break;
                        }
                    } while (!saveData.get(index).equals("VICTIM"));//atteint prochain bloc?
                    ((CrimePartner) suspectsList.get(indexPartner)).setMurdererName(suspectsList.get(indexMurderer).getFullName());
                    index--;//revient juste avant pour le swith de bloc
                    break;
                case "VICTIM":
                    String name = null, deathDate = null, deathCause = null;
                    Sex gender = null;
                    int age = 0;
                    do {
                        value = saveData.get(index).split(":")[1];
                        
                        switch(saveData.get(index).split(":")[0]) {
                            case "fullNameVictim":
                                name = value;
                                break;
                            case "sexVictim     ":
                                gender = value.toUpperCase().equals("HOMME")? Sex.HOMME : Sex.FEMME;
                                break;
                            case "ageVictim     ":
                                age = Integer.parseInt(value);
                                break;
                            case "deathDateVictim ":
                                deathDate = value;
                                break;
                            case "deathCauseVictim":
                                deathCause = value;
                                break;
                        }
                    } while (!saveData.get(++index).equals("VICTIMPROOF"));
                    
                    ArrayList<Proof> victimProofs = new ArrayList();
                    while (!saveData.get(++index).equals("WEAPON")) {
                        victimProofs.add(new Proof("victime", saveData.get(index).split(":")[1]));
                    }
                    index--;
                    
                    corpse = new Victim(name, gender, age, deathDate, deathCause, victimProofs);
                    break;
                case "WEAPON":
                    String typeWeapon = saveData.get(index++).split(":")[1];//incrémente pour bloc suivant
                    
                    ArrayList<Proof> weaponProofs = new ArrayList();
                    while (!saveData.get(++index).equals("SCENE")) {
                        weaponProofs.add(new Proof("arme du crime", saveData.get(index).split(":")[1]));
                    }
                    index--;
                    
                    weapon = new InvestElement(typeWeapon, weaponProofs);
                    break;
                case "SCENE":
                    String typeScene = saveData.get(index++).split(":")[1];//incrémente pour bloc suivant
                    
                    ArrayList<Proof> sceneProofs = new ArrayList();
                    while (!saveData.get(++index).equals("INVESTIGATOR")) {
                        sceneProofs.add(new Proof("arme du crime", saveData.get(index).split(":")[1]));
                    }
                    index--;
                    
                    scene = new InvestElement(typeScene, sceneProofs);
                    break;
                case "INVESTIGATOR":
                    name = null;
                    int manip = 0, intel = 0;
                    gender = null;
                    do {
                        key = saveData.get(index).split(":")[0];
                        value = saveData.get(index).split(":")[1];
                        
                        switch(key) {
                            case "nameInvestigator":
                                name = value;
                                break;
                            case "sexInvestigator":
                                gender = value.toUpperCase().equals("HOMME")? Sex.HOMME : Sex.FEMME;
                                break;
                            case "manipulationInvestigator":
                                manip = Integer.parseInt(value);
                                break;
                            case "intelligenceInvestigator":
                                intel = Integer.parseInt(value);
                                break;
                        }
                    } while (!saveData.get(++index).equals("CLUES"));
                    
                    ArrayList<Clue> clueList = new ArrayList();
                    while (++index < saveData.size()) {//ENDFILE
                        if (saveData.get(index).split(":")[1].equals("proof")) {
                            String origin   = saveData.get(++index).split(":")[1];
                            String content  = saveData.get(++index).split(":")[1];

                            clueList.add(new Proof(origin, content));
                        }
                        else {
                            boolean isLie = Boolean.parseBoolean(saveData.get(++index).split(":")[1]);
                            String category     = saveData.get(++index).split(":")[1];
                            String depositor    = saveData.get(++index).split(":")[1];
                            String content      = saveData.get(++index).split(":")[1];
                            
                            DepositionType type = category.equals("HEARD")? DepositionType.HEARD : 
                                                    category.equals("SEEN")? DepositionType.SEEN :
                                                    category.equals("ALIBI")? DepositionType.ALIBI :
                                                    DepositionType.ROLE;
                            clueList.add(new Deposition(depositor, content, type, isLie));
                        }
                    }
                    
                    player = new Investigator(name, gender, manip, intel, clueList);
                    break;
            }
        } while (index < saveData.size());//ENDFILE
        
        Collections.shuffle(suspectsList);//melanger tableau
        return new Investigation(player, suspectsList, corpse, weapon, scene);
    }
    
    public ArrayList <String> exportGame(Investigation currentInvestigation) {//sauvegarder
        ArrayList <String> gameData = new ArrayList();
        
        Investigator investigator       = currentInvestigation.getInvestigator();
        ArrayList<Suspect> suspectsList = currentInvestigation.getSuspectsList();
        Victim victim                   = currentInvestigation.getVictim();
        
        
        //GLOBAL
        gameData.add("GLOBAL");
        gameData.add("gameDifficulty:" + m_levelChoice.toString());
        //END GLOBAL
        
        
        //SUSPECTS
        gameData.add("SUSPECTS");
        for (Suspect currentSuspect : suspectsList) {
            //common to all
            gameData.add("classSuspect:" + currentSuspect.getClass().getSimpleName());
            gameData.add("fullNameSuspect:" + currentSuspect.getFullName());
            gameData.add("ageSuspect     :" + currentSuspect.getAge());
            gameData.add("sexSuspect     :" + currentSuspect.getSex().toString());
            gameData.add("lookSuspect    :" + currentSuspect.getLook());
            gameData.add("aspectSuspect  :" + currentSuspect.getAspect());
            gameData.add("stressSuspect     :" + currentSuspect.getStress());
            gameData.add("cooperationSuspect:" + currentSuspect.getCooperation());
            gameData.add("innocentSuspect   :" + currentSuspect.isConsideredInnocent());

            gameData.add("alibiSuspect:" + (currentSuspect.getAlibi() != null? currentSuspect.getAlibi().getContent() : "null"));
            gameData.add("seenSuspect :" + (currentSuspect.getSeen() != null? currentSuspect.getSeen().getContent().replace("Je me souviens avoir vu ", "") : "null"));
            gameData.add("heardSuspect:" + (currentSuspect.getHeard() != null? currentSuspect.getHeard().getContent().replace("Je me souviens avoir entendu ", "") : "null"));

            if (currentSuspect instanceof Murderer) {
                gameData.add("motiveMurderer:" + ((Murderer) currentSuspect).getMotive());
            }
        }
        //END SUSPECTS
        
        
        //VICTIM
        gameData.add("VICTIM");
        gameData.add("fullNameVictim:" + victim.getFullName());
        gameData.add("sexVictim     :" + victim.getSex());
        gameData.add("ageVictim     :" + victim.getAge());
        gameData.add("deathDateVictim :" + victim.getDeathDate());
        gameData.add("deathCauseVictim:" + victim.getDeathCause());
        
        gameData.add("VICTIMPROOF");
        for (Proof currentProof : victim.getProofList()) {
            gameData.add("contentProofVictim:" + currentProof.getContent());
        }
        //END VICTIM
        
        
        //WEAPON
        gameData.add("WEAPON");
        gameData.add("typeWeapon:" + currentInvestigation.getCrimeWeapon().getType());
        
        gameData.add("WEAPONPROOF");
        for (Proof currentProof : currentInvestigation.getCrimeWeapon().getProofList()) {
            gameData.add("contentProofWeapon:" + currentProof.getContent());
        }
        //END WEAPON
        
        
        //SCENE
        gameData.add("SCENE");
        gameData.add("typeScene:" + currentInvestigation.getCrimeScene().getType());
        
        gameData.add("SCENEPROOF");
        for (Proof currentProof : currentInvestigation.getCrimeScene().getProofList()) {
            gameData.add("contentProofScene:" + currentProof.getContent());
        }
        //END SCENE
        
        
        //INVESTIGATOR
        gameData.add("INVESTIGATOR");
        gameData.add("nameInvestigator:" + currentInvestigation.getInvestigator().getFullName());
        gameData.add("sexInvestigator:" + investigator.getSex());
        gameData.add("manipulationInvestigator:" + investigator.getManipulation());
        gameData.add("intelligenceInvestigator:" + investigator.getIntelligence());
        //END INVESTIGATOR
        
        
        //CLUES
        gameData.add("CLUES");
        for (Clue currentClue : investigator.getClueList()) {
            if (currentClue instanceof Proof) {
                gameData.add("classClue :proof");
                gameData.add("originClue:" + ((Proof) currentClue).getOrigin());
            }
            else {
                gameData.add("classClue :deposition");
                gameData.add("isLie     :" + ((Deposition) currentClue).isLie());
                gameData.add("category  :" + ((Deposition) currentClue).getCategory());
                gameData.add("depositor :" + ((Deposition) currentClue).getDepositor());
            }
            gameData.add("contentClue:" + currentClue.getContent());
        }
        //END CLUES
        
        
        
        return gameData;
    }
}
