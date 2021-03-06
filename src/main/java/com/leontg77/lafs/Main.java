/*
 * Project: LAFS
 * Class: com.leontg77.lafs.Main
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Leon Vaktskjold <leontg77@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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