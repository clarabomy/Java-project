/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game;

import java.util.ArrayList;
import java.util.Collections;
import static project.game.Game.getConsole;
import static project.game.Game.m_levelChoice;
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
public class Initialize {
    protected final int m_nbInnocentsByLevel = 6;
    protected final int m_ageMin = 15;
    protected final int m_ageMax = 80 - m_ageMin;
    
    
    protected static ArrayList<String> m_victimList = new ArrayList();//victimes à l'initialisation
    protected static ArrayList<String> m_weaponList = new ArrayList();//armes à l'initialisation
    protected static ArrayList<String> m_mobileList = new ArrayList();//mobiles à l'initialisation
    
    
    //faire plus de 15 noms différents
    protected String[] man = {
        "Antoine",
        "Jean",
        "Luc",
        "Paul",
        "Pierre",
        "Guillaume",
        "Alexandre"
    };
    protected String[] whoman = {
        "Claire",
        "Jeanne",
        "Alice",
        "Mathilde",
        "Camille",
        "Laure",
        "Marion"
    };
    protected String[] name = {
        "Juzeau",
        "Bomy",
        "Deolivera",
        "Pasquier",
        "Fourmond",
        "Cordier",
        "Boore"
    };
    protected String[] motive = {
                "la folie",
                "l'argent",
                "l'adrénaline",
                "le pouvoir"
            };
    protected String[] apperance = {
        "look1",
        "look2"
    };
    protected String[] physicalAspect = {
        "aspect1",
        "aspect2"
    };
    protected String[] cause = {
        "strangulation",
        "noyade",
        "blessure à l'arme blanche",
        "blessure par balle",
        "empoisonnement",
        "lynchage",
        "défenestration"
    };
    protected String[] clue = {

    };
    
    public Initialize() {
        //initialiser victimList, weaponList et mobileList
        for (int i = 0; i < 9; i++) {
            m_victimList.add(createFullName(Math.random() < 0.5? Sex.HOMME : Sex.FEMME));
        }
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
    
    
    private String createFullName(Sex genre) {
        String surname = (genre == Sex.HOMME? man : whoman)[(int) (Math.random() * (genre == Sex.HOMME? man : whoman).length)];
        return surname + ' ' + name[(int) (Math.random() * name.length)];
    }
    
    
    public Investigation createGame(String fullName, Sex gender) {
        //variables
        Sex genre;
        int age, stress, coop, manip, intel;
        String look, aspect, motivation, deathDate, deathCause;
        ArrayList<Suspect> suspectsList = new ArrayList();
        ArrayList <Proof> proofList;


        //commencer par générer les noms pour les alibis




        //criminel
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age = (int) (m_ageMin + Math.random() * m_ageMax);
        stress = (int) (m_levelChoice == Difficulties.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulties.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        look        = apperance     [(int) (Math.random() * apperance.length)];
        aspect      = physicalAspect[(int) (Math.random() * physicalAspect.length)];
        motivation  = motive        [(int) (Math.random() * motive.length)];
        Murderer criminal = new Murderer(createFullName(genre), genre, age, stress, look, aspect, motivation);
        suspectsList.add(criminal);

        //partenaire de crime
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age = (int) (m_ageMin + Math.random() * m_ageMax);
        stress  = (int) (m_levelChoice == Difficulties.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulties.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        coop    = (int) (m_levelChoice == Difficulties.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulties.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        look        = apperance     [(int) (Math.random() * apperance.length)];
        aspect      = physicalAspect[(int) (Math.random() * physicalAspect.length)];
        suspectsList.add(new CrimePartner(createFullName(genre), genre, age, stress, coop, look, aspect, "alibi", criminal.getFullName()));

        //victime
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age = (int) (m_ageMin + Math.random() * m_ageMax);
        deathDate = "il y a " + (int) (2 + Math.random() * 5) + " jours, à " + (int) (Math.random() * 23) + "h" + (int) (Math.random() * 59);
        deathCause = cause[(int) (Math.random() * cause.length)];
        proofList = new ArrayList();
        for (int i = 0; i < (int) (Math.random() * 6); i++) {//nbProofVictim
            proofList.add(new Proof("victime", "contenu"));
        }
        Victim corpse = new Victim(createFullName(genre), genre, age, deathDate, deathCause, proofList);
        m_victimList.add(corpse.getFullName());

        //arme
        proofList = new ArrayList();
        for (int i = 0; i < (int) (Math.random() * 6); i++) {//nbProofWeapon
            proofList.add(new Proof("arme du crime", "contenu"));
        }
        InvestElement weapon = new InvestElement("type d'arme", proofList);

        //scene de crime
        proofList = new ArrayList();
        for (int i = 0; i < (int) (Math.random() * 6); i++) {//nbProofScene
            proofList.add(new Proof("scène de crime", "contenu"));
        }
        InvestElement scene = new InvestElement("type d'endroit", proofList);

        //innocents
        int nbInnocents = m_nbInnocentsByLevel * (m_levelChoice == Difficulties.SIMPLE? 1 : (m_levelChoice == Difficulties.MEDIUM? 2 : 3)) - 2;
        for (int i = 0; i < nbInnocents; i++) {
            genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
            age = (int) (m_ageMin + Math.random() * m_ageMax);
            stress  = (int) (m_levelChoice == Difficulties.SIMPLE? 80 - Math.random() * 20 : (m_levelChoice == Difficulties.MEDIUM? 70 - Math.random() * 30 : 60 - Math.random() * 30));
            coop    = (int) (m_levelChoice == Difficulties.SIMPLE? 80 - Math.random() * 20 : (m_levelChoice == Difficulties.MEDIUM? 70 - Math.random() * 30 : 60 - Math.random() * 30));
            look        = apperance     [(int) (Math.random() * apperance.length)];
            aspect      = physicalAspect[(int) (Math.random() * physicalAspect.length)];
            suspectsList.add(new Innocent(createFullName(genre), genre, age, stress, coop, look, aspect, false, "alibi", "entendu", "vu"));
        }

        //enquêteur
        manip   = (int) (m_levelChoice == Difficulties.SIMPLE? 60 + Math.random() * 20 : (m_levelChoice == Difficulties.MEDIUM? 40 + Math.random() * 30 : 20 + Math.random() * 50));
        intel   = (int) (m_levelChoice == Difficulties.SIMPLE? 60 + Math.random() * 20 : (m_levelChoice == Difficulties.MEDIUM? 40 + Math.random() * 30 : 20 + Math.random() * 50));
        Investigator player = new Investigator(fullName, gender, manip, intel, criminal.getFullName(), corpse.getFullName(), weapon.getType(), scene.getType());

        //enquête
        Collections.shuffle(suspectsList);//melanger tableau
        return new Investigation(player, suspectsList, corpse, weapon, scene);
    }
    
    public Investigation importGame(ArrayList <String> saveData) {//charger
        int index = 0;
        
        Investigator player = null;
        ArrayList<Suspect> suspectsList = null;
        Victim corpse = null;
        InvestElement weapon = null, scene = null;
        
        //découpe en bloc pour limiter les comparaisons (derniers éléments)
        String key, value;
        do {//parcourir tous les blocs
            switch (saveData.get(index++)) {//rentrer dans le bloc
                case "GLOBAL":
                    value = saveData.get(index).split(":")[1];
                    m_levelChoice = value.equals("SIMPLE")? Difficulties.SIMPLE : value.equals("MEDIUM")? Difficulties.MEDIUM: Difficulties.DIFFICULT;
                    break;
                case "SUSPECTS":
                    suspectsList = new ArrayList();
                    
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
                                    gender = value.equals("HOMME")? Sex.HOMME : Sex.FEMME;
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
                                    if (!value.equals("null")) {
                                        alibi = value;
                                    }
                                    break;
                                case "seenSuspect ":
                                    if (!value.equals("null")) {
                                        seen = value;
                                    }
                                    break;
                                case "heardSuspect":
                                    if (!value.equals("null")) {
                                        heard = value;
                                    }
                                    break;
                                case "motiveMurderer":
                                    motive = value;
                                    break;
                            }
                        } while (!saveData.get(++index).split(":")[0].equals("classSuspect") && !saveData.get(index).equals("VICTIM"));
                        
                        switch (classSuspect) {
                            case "Murderer":
                                suspectsList.add(new Murderer(name, gender, age, stress, look, aspect, innocent, motive, alibi, heard, seen));
                                break;
                            case "CrimePartner":
                                suspectsList.add(new CrimePartner(name, gender, age, stress, coop, look, aspect, innocent, alibi, suspectsList.get(0).getFullName(), heard, seen));
                                break;
                            case "Innocent":
                                suspectsList.add(new Innocent(name, gender, age, stress, coop, look, aspect, innocent, alibi, heard, seen));
                                break;
                        }
                    } while (!saveData.get(index).equals("VICTIM"));//atteint prochain bloc?
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
                                gender = value.equals("HOMME")? Sex.HOMME : Sex.FEMME;
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
                    m_victimList.add(corpse.getFullName());
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
                    String[] suppositions = new String[4];//m_supposedMurderer, m_supposedVictim, m_supposedWeapon, m_supposedMobile
                    do {
                        key = saveData.get(index).split(":")[0];
                        value = saveData.get(index).split(":")[1];
                        
                        switch(key) {
                            case "nameInvestigator":
                                name = value;
                                break;
                            case "sexInvestigator":
                                gender = value.equals("HOMME")? Sex.HOMME : Sex.FEMME;
                                break;
                            case "manipulationInvestigator":
                                manip = Integer.parseInt(value);
                                break;
                            case "intelligenceInvestigator":
                                intel = Integer.parseInt(value);
                                break;
                            case "supposedMurderer":
                                suppositions[0] = value;
                                break;
                            case "supposedVictim":
                                suppositions[1] = value;
                                break;
                            case "supposedWeapon":
                                suppositions[2] = value;
                                break;
                            case "supposedMobile":
                                suppositions[3] = value;
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
                            String depositor    = saveData.get(++index).split(":")[1];
                            String content      = saveData.get(++index).split(":")[1];
                            String category     = saveData.get(++index).split(":")[1];
                            DepositionType type = category.equals("HEARD")? DepositionType.HEARD : 
                                                    category.equals("SEEN")? DepositionType.SEEN :
                                                    category.equals("ALIBI")? DepositionType.ALIBI :
                                                    DepositionType.ROLE;

                            clueList.add(new Deposition(depositor, content, type, isLie));
                        }
                    }
                    
                    player = new Investigator(name, gender, manip, intel, suspectsList.get(0).getFullName(), corpse.getFullName(), weapon.getType(), scene.getType(), clueList, suppositions);
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

            gameData.add("alibiSuspect:" + (currentSuspect.getAlibi() != null? currentSuspect.getAlibi().getContent() + "-" + currentSuspect.getAlibi().isLie() : "null"));
            gameData.add("seenSuspect :" + (currentSuspect.getSeen() != null? currentSuspect.getSeen().getContent() + "-" + currentSuspect.getSeen().isLie() : "null"));
            gameData.add("heardSuspect:" + (currentSuspect.getHeard() != null? currentSuspect.getHeard().getContent() + "-" + currentSuspect.getHeard().isLie() : "null"));

            if (currentSuspect instanceof Murderer) {
                gameData.add("motiveMurderer:" + ((Murderer) currentSuspect).getMotive());
            }
        }
        //END SUSPECTS
        
        
        //VICTIM
        gameData.add("VICTIM");
        gameData.add("fullNameVictim:" + victim.getFullName());
        gameData.add("sexVictim     :" + victim.getSex().toString());
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
        gameData.add("SexInvestigator:" + investigator.getSex());
        gameData.add("manipulationInvestigator:" + investigator.getManipulation());
        gameData.add("intelligenceInvestigator:" + investigator.getIntelligence());
        gameData.add("supposedMurderer:" + investigator.getSupposedMurderer());
        gameData.add("supposedVictim  :" + investigator.getSupposedVictim());
        gameData.add("supposedWeapon  :" + investigator.getSupposedWeapon());
        gameData.add("supposedMobile  :" + investigator.getSupposedMobile());
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
