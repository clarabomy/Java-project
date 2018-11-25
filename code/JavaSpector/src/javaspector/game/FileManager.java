
package javaspector.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import static javaspector.game.Game.convertArrayList;
import static javaspector.game.Game.getConsole;
import static javaspector.game.Game.m_console;

/**
 *
 * Game save files manager
 * @author Clara BOMY
 */ 
public class FileManager {
    protected static String m_saveFolderPath = ".\\src\\javaspector\\savesGame\\";//path to the saves folder
    protected static String m_saveFileExtension = ".spt";//house extension to limit external modifications
    protected String m_currentFileName = null;
    
    /** 
     * Constructor of the class
     */ 
    public FileManager() {
        //creates folder for saves if it does not exist
        File directory = new File(m_saveFolderPath);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
    
    /** 
     * Getter of the class
     * @return currentFileName  access path
     */ 
    public String getCurrentFileName() {
        return m_saveFolderPath + m_currentFileName + m_saveFileExtension;
    }
    
    /** 
     * Getter of the class
     * @return savesName    list of saves or null
     */ 
    public String[] getSavesName() {
        ArrayList <String> saveList = new ArrayList();
        FileFilter saveFilesOnly = (File f) -> f.getName().endsWith(m_saveFileExtension); //equals to new FileFilter() { public boolean accept(File f) { return f.getName().endsWith(".txt") ; } };
        
        for (File fichier : new File(m_saveFolderPath).listFiles(saveFilesOnly)) {//"." = current repository => goes to backup directory and retrieves file names with .txt extension
            String nom = fichier.toString();
            saveList.add(nom.substring(nom.lastIndexOf("\\") + 1, nom.lastIndexOf(".")));//remove path and extension
        }
        if (!saveList.isEmpty()) {
            saveList.add("Annuler");
        }
        
        return convertArrayList(saveList);
    }
    
    /** 
     * Setter of the class
     * @param name  name of the current file
     */ 
    public void setCurrentFileName(String name) {
        m_currentFileName = name;
    }
    
    /** 
     * Determines which file to act on
     * @param action            text to display ("charger" or "supprimer")
     * @return selectedFile     file choosen or null
     */ 
    public String selectFile(String action) {
        String[] saveList = getSavesName();
        int select = m_console.display("Choississez la sauvegarde à " + action + " :", saveList).execChoice() - 1;
        
        return select < saveList.length? m_saveFolderPath + saveList[select] + m_saveFileExtension : null;//returns path to file to process
    }
    
    /** 
     * Recover the contents of a save file
     * @param fileName  file to read
     * @return saveData an arrayList width as many entries as lines in the file
     */ 
    public ArrayList <String> readSaveFile(String fileName) {
        ArrayList <String> saveData = new ArrayList();
        if (!fileName.equals(m_saveFolderPath + "Annuler" + m_saveFileExtension)) {//if it's not "annuler" option
            //reading (data load)
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = reader.readLine()) != null) {
                    saveData.add(line);
                }

                reader.close();
            }
            catch (IOException e) {
                getConsole().display("Fichier introuvable");
            }
            return saveData;
        }
        return null;
    }
    
    /** 
     * Write as a file the contents of the game data
     * @param fileName  file to read
     * @param gameData  an array width as many entries as lines to write in the file
     */ 
    public void writeSaveFile(String fileName, String[] gameData) {
        try {
            m_console.display("Sauvegarde en cours...");
            PrintWriter writer = new PrintWriter(new FileWriter(m_saveFolderPath + fileName + m_saveFileExtension));//get flux
            
            for (String content : gameData) {//write all
                writer.println(content);
            }
            
            writer.close();
        }
        catch (IOException e){
            m_console.display("Erreur dans l'enregistrement de la partie.").execContinue("Continuer");
        }
    }
    
    /** 
     * Deletes a save file
     * @param fileName          file to delete
     * @return isFileDeleted    true if done, else false
     */ 
    public boolean deleteSaveFile(String fileName) {
        if (!fileName.equals(m_saveFolderPath + "Annuler" + m_saveFileExtension)) {
            File delFile = new File(fileName);//get name of the file to delete from the save folder
            return delFile.delete();
        }
        return true;
    }
}
