package com.lostgrounds.garbanzo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class User {
    private Player _player;
    private boolean _inGame;
    private ChatColor _color;

    public User(Player player) {
        this._player = player;
        this._inGame = false;

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

    public boolean isAfk() {
        return _inGame;
    }

    public void setAfkStatus(boolean inGame) {
        this._inGame = inGame;
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