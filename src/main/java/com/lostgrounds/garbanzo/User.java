package com.lostgrounds.garbanzo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class User {
    private Player _player;
    private ChatColor _color;

    // AFK system
    private long _lastInteractionTime;
    private boolean _isAfk;

    public User(Player player) {
        this._player = player;
        this._isAfk = false;
        updateMovement();

        // Load the user's name color. If not set, or there is an error, default to white.
        File file = getFile();
        if(Main.fileManager().hasPath("color", file)) {
            String colorcode = Main.fileManager().getString("color", file);
            if(ChatColor.of(colorcode) == null)
                this._color = ChatColor.WHITE;
            else
                this._color = ChatColor.of(colorcode);
        } else {
            this._color = ChatColor.WHITE;
        }
    }

    public Player getPlayer() {
        return _player;
    }

    public long getLastInteraction() {
        return _lastInteractionTime;
    }

    public void updateMovement() {
        _lastInteractionTime=System.nanoTime();
        if(_isAfk)
            changeAfkStatus();
    }
    public boolean isAfk() {
        return _isAfk;
    }

    public void changeAfkStatus() {
        if(_isAfk) {
            _isAfk = false;
            Bukkit.broadcastMessage(ChatColor.GRAY +"" + ChatColor.ITALIC + "* " + _player.getName() + " is no longer AFK. *");
            _player.setPlayerListName(ChatColor.WHITE + _player.getName());
        } else {
            _isAfk = true;
            Bukkit.broadcastMessage(ChatColor.GRAY +"" + ChatColor.ITALIC + "* " + _player.getName() + " is now AFK. *");
            _player.setPlayerListName(ChatColor.GRAY + "" + ChatColor.ITALIC + _player.getName());
        }
    }

    public void setNameColor(String colorcode) {
        if(ChatColor.of(colorcode) != null) {
            this._color = ChatColor.of(colorcode);
            File file = getFile();
            Main.fileManager().set("color", colorcode, file);
        }
    }

    public ChatColor getNameColor() {
        return this._color;
    }

    public File getFile() {
        return Main.fileManager().getFile(_player.getUniqueId().toString());
    }

}