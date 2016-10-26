package com.leontg77.lafs.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

import com.leontg77.lafs.Main;
import com.leontg77.lafs.listeners.ClickListener;

/**
 * LAFS command class.
 *
 * @author LeonTG77
 */
public class LAFSCommand implements CommandExecutor {
    private static final String PERMISSION = "graverobbers.manage";

    private final ClickListener listener;
    private final Main plugin;

    public LAFSCommand(Main plugin, ClickListener listener) {
        this.listener = listener;
        this.plugin = plugin;
    }

    private boolean enabled = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.PREFIX + "Usage: /lafs <enable|disable|teamsize|anonymous> [teamsize]");
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(Main.PREFIX + "Plugin creator: §aLeonTG77");
            sender.sendMessage(Main.PREFIX + "Version: §a" + plugin.getDescription().getVersion());
            sender.sendMessage(Main.PREFIX + "Description:");
            sender.sendMessage("§8» §f" + plugin.getDescription().getDescription());
            return true;
        }

        if (args[0].equalsIgnoreCase("enable")) {
            if (!sender.hasPermission(PERMISSION)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission.");
                return true;
            }

            if (enabled) {
                sender.sendMessage(Main.PREFIX + "LAFS is already enabled.");
                return true;
            }

            plugin.broadcast(Main.PREFIX + "LAFS has been enabled.");

            Bukkit.getPluginManager().registerEvents(listener, plugin);
            enabled = true;
            return true;
        }

        if (args[0].equalsIgnoreCase("disable")) {
            if (!sender.hasPermission(PERMISSION)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission.");
                return true;
            }

            if (!enabled) {
                sender.sendMessage(Main.PREFIX + "LAFS is not enabled.");
                return true;
            }

            plugin.broadcast(Main.PREFIX + "LAFS has been disabled.");

            HandlerList.unregisterAll(listener);
            enabled = false;
            return true;
        }

        if (args[0].equalsIgnoreCase("teamsize")) {
            if (!sender.hasPermission(PERMISSION)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission.");
                return true;
            }

            if (!enabled) {
                sender.sendMessage(Main.PREFIX + "LAFS is not enabled.");
                return true;
            }

            if (args.length == 1) {
                sender.sendMessage(Main.PREFIX + "Usage: /lafs teamsize <teamsize>");
                return true;
            }

            int size;

            try {
                size = Integer.parseInt(args[1]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "'" + args[1] + "' is not a valid teamsize.");
                return true;
            }

            if (size < 1) {
                sender.sendMessage(ChatColor.RED + "The combine teamsize can't be lower than 1.");
                return true;
            }

            plugin.broadcast(Main.PREFIX + "The teamsize is now a" + size + "7.");
            plugin.setSize(size);
            return true;
        }

        if (args[0].equalsIgnoreCase("anonymous")) {
            if (!sender.hasPermission(PERMISSION)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission.");
                return true;
            }

            if (!enabled) {
                sender.sendMessage(Main.PREFIX + "LAFS is not enabled.");
                return true;
            }

            if (plugin.isAnonymous()) {
                sender.sendMessage(Main.PREFIX + "Anonymous LAFS is now disabled.");
                plugin.setAnonymous(false);
            } else {
                sender.sendMessage(Main.PREFIX + "Anonymous LAFS is now enabled.");
                plugin.setAnonymous(true);
            }
            return true;
        }

        sender.sendMessage(Main.PREFIX + "Usage: /lafs <enable|disable|teamsize|anonymous> [teamsize]");
        return true;
    }
}