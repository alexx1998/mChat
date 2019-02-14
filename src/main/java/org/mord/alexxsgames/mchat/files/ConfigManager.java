package org.mord.alexxsgames.mchat.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager{
    private static File pFile, tFile;
    private static FileConfiguration pFileConfig, tFileConfig;

    //players.yml
    public static void setUpPlayer(){
        //Find/Generate custom file
        pFile = new File(Bukkit.getServer().getPluginManager().getPlugin("MChat").getDataFolder(), "players.yml");
        if(!pFile.exists()){
            try {
                pFile.createNewFile();

            } catch (IOException e) {

            }
        }
        pFileConfig = YamlConfiguration.loadConfiguration(pFile);
    }
    public static FileConfiguration getPlayerCfg(){
        return pFileConfig;
    }
    public static void savePlayerCfg(){
        try {
            pFileConfig.save(pFile);
        } catch (IOException e) {

        }
    }
    public static void reloadPlayerCfg() {
        pFileConfig = YamlConfiguration.loadConfiguration(pFile);
    }

    //towns.yml
    public static void setUpTownCfg(){
        //Find/Generate custom file
        tFile = new File(Bukkit.getServer().getPluginManager().getPlugin("MChat").getDataFolder(), "towns.yml");
        if(!tFile.exists()){
            try {
                tFile.createNewFile();

            } catch (IOException e) {

            }
        }
        tFileConfig = YamlConfiguration.loadConfiguration(tFile);
    }

    public static FileConfiguration getTownCfg(){
        return tFileConfig;
    }
    public static void saveTownCfg(){
        try {
            tFileConfig.save(tFile);
        } catch (IOException e) {

        }
    }
    public static void reloadTownCfg() {
        tFileConfig = YamlConfiguration.loadConfiguration(tFile);
    }
}