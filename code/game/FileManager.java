/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import static project.game.Game.convertArrayList;
import static project.game.Game.m_console;

/**
 *
 * @author ISEN
 */
public class FileManager {
    protected static String m_saveFolderPath = ".\\src\\project\\savesGame\\";//chemin d'accès au dossier de sauvegardes
    protected static String m_saveFileExtension = ".spt";//extension bateau pour limiter les bidouillages
    protected String m_currentFileName = null;
    
    public FileManager() {
        //crée dossier pour sauvegardes s'il n'existe pas
        File directory = new File(m_saveFolderPath);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
    
    public String getCurrentFileName() {
        return m_saveFolderPath + m_currentFileName + m_saveFileExtension;
    }
    
    public void setCurrentFileName(String name) {
        m_currentFileName = name;
    }
    
    
    public String[] getSavesName() {
        ArrayList <String> saveList = new ArrayList();
        FileFilter saveFilesOnly = (File f) -> f.getName().endsWith(m_saveFileExtension); //équivaut à new FileFilter() { public boolean accept(File f) { return f.getName().endsWith(".txt") ; } };
        
        for (File fichier : new File(m_saveFolderPath).listFiles(saveFilesOnly)) {//"." = répertoire courant => va dans répertoire de sauvegardes et récupère noms des fichiers avec extension .txt
            String nom = fichier.toString();
            saveList.add(nom.substring(nom.lastIndexOf("\\") + 1, nom.lastIndexOf(".")));//retire chemin et extension
        }
        if (!saveList.isEmpty()) {
            saveList.add("Annuler");
        }
        
        return convertArrayList(saveList);
    }
    
    public String selectFile(String action) {//charger / supprimer
        String[] saveList = getSavesName();
        int select = m_console.display("Choississez la sauvegarde à " + action + " :", saveList).execChoice() - 1;
        
        return select < saveList.length? m_saveFolderPath + saveList[select] + m_saveFileExtension : null;//retourne chemin vers fichier à traiter
    }
    
    public ArrayList <String> readSaveFile(String fileName) {
        ArrayList <String> saveData = new ArrayList();
        if (!fileName.equals(m_saveFolderPath + "Annuler" + m_saveFileExtension)) {
            //lecture (charge données)
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = reader.readLine()) != null) {
                    saveData.add(line);
                }

                reader.close();
            }
            catch (IOException e) {
                System.out.println("File not found");
            }
            return saveData;
        }
        return null;
    }
    
    public void writeSaveFile(String fileName, String[] gameData) {
        try {
            m_console.display("Sauvegarde en cours...");
            PrintWriter writer = new PrintWriter(new FileWriter(m_saveFolderPath + fileName + m_saveFileExtension));
            
            for (String content : gameData) {
                writer.println(content);
            }
            
            writer.close();
        }
        catch (IOException e){
            m_console.display("Erreur dans l'enregistrement de la partie.").execContinue(null);
        }
    }
    
    public boolean deleteSaveFile(String fileName) {
        if (!fileName.equals(m_saveFolderPath + "Annuler" + m_saveFileExtension)) {
            File delFile = new File(fileName);//récupère nom du fichier à supprimer du dossier de sauvegardes
            return delFile.delete();
        }
        return true;
    }
}
