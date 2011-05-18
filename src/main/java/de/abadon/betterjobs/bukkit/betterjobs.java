/*
 * PermissionsEx - Permissions plugin for Bukkit
 * Copyright (C) 2011 t3hk0d3 http://www.tehkode.ru
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.abadon.betterjobs.bukkit ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import de.abadon.betterjobs.config.properties;

/**
 *
 * @author code
 */
public class betterjobs extends JavaPlugin {

    protected static final String configFile = "config.yml";
    protected static final Logger logger = Logger.getLogger("Minecraft");
    protected static  properties conf;

    public betterjobs() {
        logger.log(Level.INFO, "[BetterJobs] BetterJobs plugin was Initialized.");
    }

    @Override
    public void onLoad() {
            conf = new properties(getDataFolder());
            conf.loadConfig();
    }

    @Override
    public void onEnable() {
        logger.log(Level.INFO, "[BetterJobs] loaded");
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "[BetterJobs]disabled successfully.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(commandLabel.equalsIgnoreCase("bjobs"))
        {
           Player player = (Player)sender;
            String help = "Usage of BetterJobs:\n/bjobs - Display help\n/bjobs info - Display jobs\n/bjobs info <job> - Display job info\n/bjobs join <job> - Join a job\n/bjobs stats - Display job stats\n/bjobs stats <player> - Display job stats of player\n/bjobs del <player> - Delete the players job\n/bjobs set <job> <player> - Set the players job\n/bjobs reload - Reload Better Jobs";
                if (args.length != 0){
                    if(args[0].equalsIgnoreCase("info") && args.length == 1){
                        player.sendMessage("command for job list");
                    }
                    else if(args[0].equalsIgnoreCase("info") && args.length == 2){
                        player.sendMessage("command for job info " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("join") && args.length == 2){
                         player.sendMessage("command for join job " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("stats") && args.length == 1){
                        player.sendMessage("command for your stats ");
                    }
                    else if(args[0].equalsIgnoreCase("stats") && args.length == 2){
                        player.sendMessage("command for stats of " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("del") && args.length == 2){
                        player.sendMessage("command for deleting " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("set") && args.length == 3){
                        player.sendMessage("command for setting job " + args[1] + " for " + args[2]);
                    }
                    else if(args[0].equalsIgnoreCase("config") && args.length == 1){
                        HashMap Config = conf.getSection("PLUGIN");
                        Set<String> Nodes = Config.keySet();
                        player.sendMessage("Plugin Configuration:");
                        for (String Node : Nodes) {
                            player.sendMessage(Node + ": " + Config.get(Node));
                        }
                    }
                    else if(args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("set") && args.length == 4){
                        if (conf.setProperty("PLUGIN", args[2], args[3])){
                            player.sendMessage("Configuration node set");
                        }
                        else{
                            player.sendMessage("No valid configuration node or file-writing-error... see console...");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("backend") && args.length == 1){
                        HashMap Config = conf.getSection("DATABASE");
                        Set<String> Nodes = Config.keySet();
                        player.sendMessage("Plugin Configuration:");
                        for (String Node : Nodes) {
                            player.sendMessage(Node + ": " + Config.get(Node));
                        }
                    }
                    else if(args[0].equalsIgnoreCase("backend") && args[1].equalsIgnoreCase("set") && args.length == 4){
                        if (conf.setProperty("DATABASE", args[2], args[3])){
                            player.sendMessage("Configuration node set");
                        }
                        else{
                            player.sendMessage("No valid configuration node or file-writing-error... see console...");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("reload") && args.length == 1){
                        conf.loadConfig();
                        player.sendMessage("BetterJobs reloaded");
                    }
                    else{
                        messageSender(help,player);
                     }
                }
                else{
                        String testString = conf.getProperty("DATABASE", "database");
                        player.sendMessage(testString);
                        testString = conf.getProperty("PLUGIN", "switchFee");
                        player.sendMessage(testString);
                }
            }
            logger.log(Level.INFO, "[BetterJobs] version [{0}] get the command.", this.getDescription().getVersion());
            return true;
    }
    
    private void messageSender(String message, Player player){
        for (String msg: message.split("\\n")) {
            player.sendMessage(msg);
        }
    }
}

   