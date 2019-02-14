package org.mord.alexxsgames.mchat;


import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mord.alexxsgames.mchat.chat.ChatM;
import org.mord.alexxsgames.mchat.files.ConfigManager;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class MChat extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    public ArrayList<String> lnames = new ArrayList<>();

    @Override
    public void onEnable() {
        loadPlayersCfg();
        loadTownsCfg();
        this.loadConfig();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new ChatM(), this);
        this.getCommand("mchat").setExecutor(new ChatM());
        setupPermissions();
        setupChat();
        this.getConfig().set("lastnames" + ".names", lnames);
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }

    public void loadPlayersCfg() {
        ConfigManager.setUpPlayer();
        ConfigManager.savePlayerCfg();
    }
    public void loadTownsCfg(){
        ConfigManager.setUpTownCfg();
        ConfigManager.saveTownCfg();
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}


