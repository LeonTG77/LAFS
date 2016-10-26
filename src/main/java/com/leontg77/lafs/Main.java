package com.leontg77.lafs;

import com.leontg77.lafs.commands.LAFSCommand;
import com.leontg77.lafs.listeners.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin.
 *
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
    public static final String PREFIX = "§dLAFS §8» §7";

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("teamsizeToCombine", 1);
        getConfig().addDefault("anonymous", false);
        saveConfig();

        ClickListener listener = new ClickListener(this);
        LAFSCommand command = new LAFSCommand(this, listener);

        getCommand("lafs").setExecutor(command);
    }

    /**
     * Broadcasts a message to everyone online.
     *
     * @param message the message.
     */
    public void broadcast(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(message);
        }
    }

    /**
     * Set the size of the teams to combine.
     *
     * @param size The new size.
     */
    public void setSize(int size) {
        getConfig().set("teamsizeToCombine", size);
        saveConfig();
    }

    /**
     * Get the size of the teams to combine.
     *
     * @return The size.
     */
    public int getSize() {
        return getConfig().getInt("teamsizeToCombine", 1);
    }

    /**
     * Set the new state whether to announce whos on a team or not.
     *
     * @param newState The new state.
     */
    public void setAnonymous(boolean newState) {
        getConfig().set("anonymous", newState);
        saveConfig();
    }

    /**
     * Check if it should say who found each other.
     *
     * @return True if it is, false otherwise.
     */
    public boolean isAnonymous() {
        return getConfig().getBoolean("anonymous", false);
    }
}