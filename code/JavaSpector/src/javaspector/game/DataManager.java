
package javaspector.game; 
 
import java.util.ArrayList; 
import java.util.Collections; 
import static javaspector.game.Game.m_levelChoice; 
import javaspector.game.character.Investigator; 
import static javaspector.game.character.LiveCharacter.dropClueList; 
import javaspector.game.character.Sex; 
import javaspector.game.character.Victim; 
import javaspector.game.character.suspect.CrimePartner; 
import javaspector.game.character.suspect.Innocent; 
import javaspector.game.character.suspect.Murderer; 
import javaspector.game.character.suspect.Suspect; 
import javaspector.game.investigation.Clue; 
import javaspector.game.investigation.Deposition; 
import javaspector.game.investigation.DepositionType; 
import javaspector.game.investigation.InvestElement; 
import javaspector.game.investigation.Investigation; 
import javaspector.game.investigation.Proof; 

/**
 *
 * Data manager of game instances
 * @author Clara BOMY
 */ 
public class DataManager { 
    protected final int m_nbInnocentsByLevel = 3; 
    protected final int m_ageMin = 15; 
    protected final int m_ageMax = 80 - m_ageMin; 
    protected ArrayList <String> m_namesGenerated = new ArrayList();
     
     
    /** 
     * Generates a random deposition
     * @param type          the category of the deposition to create
     * @param murderer      use of the murderer informations
     * @return deposition   the text generated
     */ 
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
          
                text = activity[(int) (Math.random() * activity.length)] + " " 
                        + place[(int) (Math.random() * place.length)] + " " 
                        + company[(int) (Math.random() * company.length)]; 
                break; 
                 
            case SEEN: 
                String[] seen = {"une personne ayant un look", 
                                 "une personne qui était", 
                                 "une personne"};  
                String[] uselessSeen = {"un chat trop mignon", 
                                    "un mec trop beau", 
                                    "un camion de pompier"}; 
                 
                int indexSeen = (int) (Math.random() * seen.length); 
                 
                text += (Math.random() < 0.5? seen[indexSeen] : uselessSeen[indexSeen]) + " "; 
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
                 
                text += (Math.random() < 0.5? heard[indexHeard] : uselessHeard[indexHeard]) + " "; 
                if (indexHeard == 0) { text += murderer.getSex(); } 
                break; 
        } 
        return text + "."; 
    } 
    
    /** 
     * Generates a new random full name
     * @param genre         determines whether uses feminine or masculine first name
     * @return fullName     the full name generated
     */ 
    private String createFullName(Sex genre) {
        String[] m_manList = { 
            "Antoine", 
            "Thomas", 
            "Luc", 
            "Paul", 
            "Pierre", 
            "Guillaume", 
            "Alexandre" 
        }; 
        String[] m_whomanList = { 
            "Claire", 
            "Lucie", 
            "Alice", 
            "Morgane", 
            "Camille", 
            "Laure", 
            "Marine" 
        }; 
        String[] m_nameList = { 
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
        
        String fullName;
        do {
            String surname = (genre == Sex.HOMME? m_manList : m_whomanList)[(int) (Math.random() * (genre == Sex.HOMME? m_manList : m_whomanList).length)];
            fullName = surname + ' ' + m_nameList[(int) (Math.random() * m_nameList.length)];
        } while (m_namesGenerated.contains(fullName));//compare to other names
        m_namesGenerated.add(fullName);//add to other names
        return fullName;
    } 
    
    /** 
     * Randomly initializes an investigation according to difficulty
     * @param fullName          full name choosen by the player
     * @param gender            gender choosen by the player
     * @return investigation    instance of playable investigation
     */ 
    public Investigation createGame(String fullName, Sex gender) { 
        dropClueList();//regenerates the list
        
        
        String[] lookList = { 
            "classe", 
            "casual", 
            "négligé", 
            "gothique"                   
        }; 
        String[] aspectList = { 
            "grand", 
            "petit", 
            "mince", 
            "corpulent" 
        }; 
        String[] proofContentList = { 
            "tâches de sang", 
            "empreintes digitales", 
            "mégot de cigarette", 
            "ticket de métro" 
        }; 
        
        
        //common variables 
        Sex genre; 
        int age, stress, coop, manip, intel; 
        String look, aspect, motivation, deathDate, deathCause; 
        ArrayList<Suspect> suspectsList = new ArrayList(); 
        ArrayList <Proof> proofList; 
 
        //murderer 
        String[] mobileList = { 
            "par folie", 
            "pour l'argent", 
            "pour l'adrénaline", 
            "pour le pouvoir" 
        }; 
        genre   = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
        age     = (int) (m_ageMin + Math.random() * m_ageMax); 
        //balancing according to difficulty : base points + interval
        stress  = (int) (m_levelChoice == Difficulty.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20));
        look    = lookList[(int) (Math.random() * lookList.length)]; 
        aspect  = aspectList[(int) (Math.random() * aspectList.length)]; 
        motivation = mobileList[(int) (Math.random() * mobileList.length)]; 
        Murderer criminal = new Murderer(createFullName(genre), genre, age, stress, look, aspect, motivation); 
        suspectsList.add(criminal); 
 
        //crimePartner
        genre   = Math.random() < 0.5? Sex.HOMME : Sex.FEMME; 
        age     = (int) (m_ageMin + Math.random() * m_ageMax); 
        //balancing according to difficulty : base points + interval
        stress  = (int) (m_levelChoice == Difficulty.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20)); 
        coop    = (int) (m_levelChoice == Difficulty.SIMPLE? 20 + Math.random() * 40 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 60 + Math.random() * 20)); 
        look    = lookList[(int) (Math.random() * lookList.length)]; 
        aspect  = aspectList[(int) (Math.random() * aspectList.length)]; 
        suspectsList.add(new CrimePartner(createFullName(genre), genre, age, stress, coop, look, aspect, createDeposition(DepositionType.ALIBI, criminal), criminal.getFullName())); 
 
        //victim 
        String[] causeList = { 
            "d'hémorragie interne", 
            "de blessures profondes", 
            "d'arrêt cardiaque" 
        }; 
        genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME; 
        age = (int) (m_ageMin + Math.random() * m_ageMax); 
        deathDate = "il y a " + (int) (2 + Math.random() * 5) + " jours, à " + (int) (Math.random() * 23) + "h" + (int) (Math.random() * 59); 
        deathCause = causeList[(int) (Math.random() * causeList.length)]; 
        proofList = new ArrayList(); 
        for (int i = 0; i < (int) (Math.random() * proofContentList.length + 1); i++) {//nbProofVictim 
            proofList.add(new Proof("Victime", proofContentList[(int) (Math.random() * proofContentList.length)])); 
        } 
        Victim corpse = new Victim(createFullName(genre), genre, age, deathDate, deathCause, proofList); 
 
        //weapon
        String[] weaponList = { 
            "couteau", 
            "batte de baseball", 
            "revolver", 
            "pelle", 
            "pioche" 
        }; 
        proofList = new ArrayList(); 
        for (int i = 0; i < (int) (Math.random() * proofContentList.length + 1); i++) {//nbProofWeapon 
            proofList.add(new Proof("Arme du crime", proofContentList[(int) (Math.random() * proofContentList.length)])); 
        } 
        InvestElement weapon = new InvestElement(weaponList[(int) (Math.random() * weaponList.length)], proofList); 
 
        //scene 
        proofList = new ArrayList(); 
        for (int i = 0; i < (int) (Math.random() * proofContentList.length + 1); i++) {//nbProofScene 
            proofList.add(new Proof("Scène de crime", proofContentList[(int) (Math.random() * proofContentList.length)])); 
        } 
        InvestElement scene = new InvestElement("Scène de crime", proofList); 
 
        //innocents 
        int nbInnocents = m_nbInnocentsByLevel * (m_levelChoice == Difficulty.SIMPLE? 1 : (m_levelChoice == Difficulty.MEDIUM? 2 : 3)); 
        for (int i = 0; i < nbInnocents; i++) { 
            genre = Math.random() < 0.5? Sex.HOMME : Sex.FEMME;
            age = (int) (m_ageMin + Math.random() * m_ageMax); 
            //balancing according to difficulty : base points - interval
            stress  = (int) (m_levelChoice == Difficulty.SIMPLE? 80 - Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 70 - Math.random() * 30 : 60 - Math.random() * 30)); 
            coop    = (int) (m_levelChoice == Difficulty.SIMPLE? 80 - Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 70 - Math.random() * 30 : 60 - Math.random() * 30)); 
            look        = lookList[(int) (Math.random() * lookList.length)]; 
            aspect      = aspectList[(int) (Math.random() * aspectList.length)]; 
            suspectsList.add(new Innocent(createFullName(genre), genre, age, stress, coop, look, aspect, false, createDeposition(DepositionType.ALIBI, criminal), createDeposition(DepositionType.HEARD, criminal), createDeposition(DepositionType.SEEN, criminal))); 
        } 
 
        //investigator 
        //balancing according to difficulty : base points + interval
        manip   = (int) (m_levelChoice == Difficulty.SIMPLE? 60 + Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 20 + Math.random() * 50)); 
        intel   = (int) (m_levelChoice == Difficulty.SIMPLE? 60 + Math.random() * 20 : (m_levelChoice == Difficulty.MEDIUM? 40 + Math.random() * 30 : 20 + Math.random() * 50)); 
        Investigator player = new Investigator(fullName, gender, manip, intel, null); 
         
        //investigation 
        Collections.shuffle(suspectsList); 
        return new Investigation(player, suspectsList, corpse, weapon, scene); 
    } 
     
    /** 
     * Constitute a table of data from the instances of the game
     * @param currentInvestigation  instance of played investigation
     * @return gameData             String array that contains the data
     */ 
    public ArrayList <String> exportGame(Investigation currentInvestigation) { 
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
            gameData.add("sexSuspect     :" + currentSuspect.getSex()); 
            gameData.add("lookSuspect    :" + currentSuspect.getLook()); 
            gameData.add("aspectSuspect  :" + currentSuspect.getAspect()); 
            gameData.add("stressSuspect     :" + currentSuspect.getStress()); 
            gameData.add("cooperationSuspect:" + currentSuspect.getCooperation()); 
            gameData.add("innocentSuspect   :" + currentSuspect.isConsideredInnocent()); 
 
            //if doesn't go depositions : display null. else, cut the part who is add in the constructor
            gameData.add("alibiSuspect:" + (currentSuspect.getAlibi() != null? currentSuspect.getAlibi().getContent() : "null")); 
            gameData.add("seenSuspect :" + (currentSuspect.getSeen() != null? currentSuspect.getSeen().getContent().replace("Je me souviens avoir vu ", "") : "null")); 
            gameData.add("heardSuspect:" + (currentSuspect.getHeard() != null? currentSuspect.getHeard().getContent().replace("Je me souviens avoir entendu ", "") : "null")); 
 
            if (currentSuspect instanceof Murderer) { //specific string
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
            //different content according to the class
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
     
    /** 
     * Initialize an investigation from a string array
     * @param saveData          String array that contains the data
     * @return investigation    instance of playable investigation
     */ 
    public Investigation importGame(ArrayList <String> saveData) {
        dropClueList();//regenerates the list
         
        Investigator player = null; 
        ArrayList<Suspect> suspectsList = null; 
        Victim corpse = null; 
        InvestElement weapon = null, scene = null; 
         
        //bulk cutting to limit comparisons (especially the last elements)
        int index = 0; 
        String key, value; 
        do {//browse all the blocks
            switch (saveData.get(index++)) {//enter the block
                case "GLOBAL": 
                    value = saveData.get(index).split(":")[1]; 
                    m_levelChoice = value.equals("SIMPLE")? Difficulty.SIMPLE : value.equals("MEDIUM")? Difficulty.MEDIUM: Difficulty.DIFFICULT; 
                    break; 
                case "SUSPECTS": 
                    suspectsList = new ArrayList(); 
                    int indexPartner = 0, indexMurderer = 0; 
                         
                    do {//take care of the whole block SUSPECTS
                        String classSuspect = saveData.get(index++).split(":")[1];//value of the "class" key
                         
                        String name = "", look = "", aspect = "", motive = ""; 
                        int age = 0, stress = 0, coop = 0; 
                        Sex gender = null; 
                        boolean innocent = false; 
                        String alibi = null, seen = null, heard = null; 
 
                        do { 
                            value = saveData.get(index).split(":")[1]; //get the value
                             
                            switch(saveData.get(index).split(":")[0]) { //test the key
                                case "fullNameSuspect": 
                                    name = value; 
                                    break; 
                                case "ageSuspect     ": 
                                    age = Integer.parseInt(value); 
                                    break; 
                                case "sexSuspect     ": 
                                    gender = value.equals(Sex.HOMME)? Sex.HOMME : Sex.FEMME; 
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
                        } while (!saveData.get(++index).split(":")[0].equals("classSuspect") && !saveData.get(index).equals("VICTIM")); //while it's not the next block
                         
                        switch (classSuspect) { //value recovered at the begin of the do while (suspectList is mixed : random order)
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
                    } while (!saveData.get(index).equals("VICTIM"));//while it's not the next block
                    ((CrimePartner) suspectsList.get(indexPartner)).setMurdererName(suspectsList.get(indexMurderer).getFullName()); //complete crimePartner
                    index--;//returns just before VICTIM for the block switch
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
                                gender = value.equals(Sex.HOMME)? Sex.HOMME : Sex.FEMME; 
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
                    String typeWeapon = saveData.get(index++).split(":")[1];//increment for next block
                     
                    ArrayList<Proof> weaponProofs = new ArrayList(); 
                    while (!saveData.get(++index).equals("SCENE")) { 
                        weaponProofs.add(new Proof("arme du crime", saveData.get(index).split(":")[1])); 
                    } 
                    index--; 
                     
                    weapon = new InvestElement(typeWeapon, weaponProofs); 
                    break; 
                case "SCENE": 
                    String typeScene = saveData.get(index++).split(":")[1];//increment for next block
                     
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
                                gender = value.equals(Sex.HOMME)? Sex.HOMME : Sex.FEMME; 
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

        return new Investigation(player, suspectsList, corpse, weapon, scene); 
    } 
}
