package org.mord.alexxsgames.mchat.chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.mord.alexxsgames.mchat.files.ConfigManager;
import org.mord.alexxsgames.mchat.MChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatM implements Listener, CommandExecutor {
    private Plugin plugin = MChat.getPlugin(MChat.class);
    private ArrayList<Player> p = new ArrayList<Player>();
    public String town;

    List<String> lastRandNames = plugin.getConfig().getStringList("lastnames" + ".names");


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String pName = player.getDisplayName();
        String msg = event.getMessage();
        String channel;
        event.getRecipients().clear();
        //event.setFormat(channel + pName + ":" + msg);//
        World world = player.getWorld();
        String group = MChat.getChat().getPrimaryGroup(player);
        String prefix = ChatColor.translateAlternateColorCodes('&', MChat.getChat().getGroupPrefix(world, group));
        String fn = ChatColor.translateAlternateColorCodes('&',ConfigManager.getPlayerCfg().getString(player.getUniqueId() + ".firstname"));
        String ln = ChatColor.translateAlternateColorCodes('&', ConfigManager.getPlayerCfg().getString(player.getUniqueId() + ".lastname"));

        if (msg.startsWith("@g")) {
            channel = ChatColor.GOLD + "[G] " + ChatColor.RESET;
            msg = event.getMessage().replaceFirst("@g", " ");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                p.add(pl);
                event.getRecipients().add(player);
                event.getRecipients().addAll(p);
                event.setFormat(channel + prefix + "" + fn + "" + ln + ":" + msg);
            }
        }
       else if (msg.startsWith("@rp")) {
            channel = ChatColor.LIGHT_PURPLE + "[RP] " + ChatColor.RESET;
            msg = event.getMessage().replaceFirst("@rp", " ");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                p.add(pl);
                event.getRecipients().add(player);
                event.getRecipients().addAll(p);
                event.setFormat(channel + prefix + "" + fn + "" + ln + ":" + msg);
            }
        }
        else if (msg.startsWith("@m")) {
            channel = ChatColor.DARK_BLUE + "[M] " + ChatColor.RESET;
            msg = event.getMessage().replaceFirst("@m", " ");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                p.add(pl);
                event.getRecipients().add(player);
                event.getRecipients().addAll(p);
                event.setFormat(channel + prefix + "" + fn + "" + ln + ":" + msg);
            }

        }
        else if (msg.startsWith("@r")) {
            channel = ChatColor.AQUA + "[R] " + ChatColor.RESET;
            msg = event.getMessage().replaceFirst("@r", " ");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                p.add(pl);
                event.getRecipients().add(player);
                event.getRecipients().addAll(p);
                event.setFormat(channel + prefix + "" + fn + "" + ln + ":" + msg);
            }
        }
        else if(msg.startsWith("@w")){
            channel = ChatColor.DARK_GRAY + "[W] " + ChatColor.RESET;
            msg = event.getMessage().replaceFirst("@w", " ");
            for (Entity ent : player.getNearbyEntities(5, 5, 5)) {
                if (ent instanceof Player) {
                    this.p.add((Player) ent);
                    event.getRecipients().addAll(p);
                }
            }
            event.getRecipients().add(player);
            event.setFormat(channel + prefix + " " + fn + " " + ln + ":" + msg);
        }
        else if(msg.startsWith("@lrp")){
            channel = ChatColor.LIGHT_PURPLE + "[LRP] " + ChatColor.RESET;
            msg = event.getMessage().replaceFirst("@lrp", " ");
            for (Entity ent : player.getNearbyEntities(25, 25, 25)) {
                if (ent instanceof Player) {
                    this.p.add((Player) ent);
                    event.getRecipients().addAll(p);
                }
            }
            event.getRecipients().add(player);
            event.setFormat(channel + prefix + " " + fn + " " + ln + ":" + msg);
        }

        else {
            channel = ChatColor.GRAY + "[L] " + ChatColor.RESET;
            for (Entity ent : player.getNearbyEntities(25, 25, 25)) {
                if (ent instanceof Player) {
                    this.p.add((Player) ent);
                    event.getRecipients().addAll(p);
                }
            }
            event.getRecipients().add(player);
            event.setFormat(channel + prefix + " " + fn + " " + ln + ":" + msg);
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String fn = ChatColor.translateAlternateColorCodes('&', ConfigManager.getPlayerCfg().getString(p.getUniqueId() + ".firstname"));
        String ln =  ChatColor.translateAlternateColorCodes('&', ConfigManager.getPlayerCfg().getString(p.getUniqueId() + ".lastname"));
        if (!p.hasPlayedBefore()) {
            int randNum = new Random().nextInt(lastRandNames.size());
            String lnl = lastRandNames.get(randNum);
            ConfigManager.getPlayerCfg().set(p.getUniqueId() + ".lastname", lnl);
            ConfigManager.getPlayerCfg().set(p.getUniqueId() + ".firstname", "Unnamed");
            ConfigManager.savePlayerCfg();
            event.setJoinMessage(p.getDisplayName() + ChatColor.RED + " Has joined for the first time!");
            p.setPlayerListName(fn + "<" + p.getDisplayName() + ">" + ln);
        } else {
            event.setJoinMessage(fn + " " + ln + ChatColor.GREEN + " has joined!");
            p.setPlayerListName(fn + "<" + p.getDisplayName() + ">" + ln);
        }
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("mchat")){
            if (args.length <= 0){
                sender.sendMessage(ChatColor.GOLD + "/mchat help");
            }
            else {
                if (args[0].equalsIgnoreCase("set")) {
                    if(sender.hasPermission("mchat.set")){
                        if(args.length >= 4){
                            Player targetPlayer = Bukkit.getPlayer(args[2]);
                            if(args[1].equalsIgnoreCase("name")){
                                if(targetPlayer != null){
                                 ConfigManager.getPlayerCfg().set(targetPlayer.getUniqueId() + ".firstname", args[3]);
                                 ConfigManager.getPlayerCfg().set(targetPlayer.getUniqueId() + ".lastname", args[4]);
                                 sender.sendMessage(ChatColor.GREEN + "Name set successfully to " + args[3] + " " + args[4]);
                                 ConfigManager.savePlayerCfg();
                                }
                                else{
                                    sender.sendMessage(ChatColor.RED + "Player not found!");
                                }
                            }
                            else if(args[1].equalsIgnoreCase("firstname")){
                                if(targetPlayer != null){
                                    ConfigManager.getPlayerCfg().set(targetPlayer.getUniqueId() + ".firstname", args[3]);
                                    sender.sendMessage(ChatColor.GREEN + "First name set successfully to " + args[3]);
                                    ConfigManager.savePlayerCfg();
                                }
                                else{
                                    sender.sendMessage(ChatColor.RED + "Player not found!");
                                }
                            }
                            else if(args[1].equalsIgnoreCase("lastname")){
                                if(targetPlayer != null){
                                    ConfigManager.getPlayerCfg().set(targetPlayer.getUniqueId() + ".lastname", args[3]);
                                    sender.sendMessage(ChatColor.GREEN + "Last name set successfully to " + args[3]);
                                    ConfigManager.savePlayerCfg();
                                }
                                else{
                                    sender.sendMessage(ChatColor.RED + "Player not found!");
                                }
                            }
                        }
                    }
                    else{
                        sender.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
                    }
                }
                else if (args[0].equalsIgnoreCase("help")) {
                    if(sender.hasPermission("mchat.help")) {
                        sender.sendMessage(ChatColor.GOLD + "Mchat command guide:");
                        sender.sendMessage(ChatColor.GOLD + "=+----------------+=");
                        sender.sendMessage(ChatColor.GOLD + "/mchat set :" + ChatColor.RESET + "Main plugin command");
                        sender.sendMessage(ChatColor.GOLD + "/mchat set name :" + ChatColor.RESET + "Sets the full name of a player");
                        sender.sendMessage(ChatColor.GOLD + "    usage: /mchat set name [username] [firstname] [lastname]");
                        sender.sendMessage(ChatColor.GOLD + "/mchat set firstname :" + ChatColor.RESET + " Sets the First name of a player");
                        sender.sendMessage(ChatColor.GOLD + "    usage: /mchat set firstname [usernane] [firstname]");
                        sender.sendMessage(ChatColor.GOLD + "/mchat set lastname: " + ChatColor.RESET + "Sets the First name of a player");
                        sender.sendMessage(ChatColor.GOLD + "    usage: /mchat set firstname [usernane] [lastname]");
                    }
                }
            }

        }
        return true;
        }
    }
















