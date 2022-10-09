package com.lostgrounds.garbanzo;

import com.lostgrounds.garbanzo.util.FileManager;
import com.lostgrounds.garbanzo.util.UserManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main extends JavaPlugin {

    private static FileManager fileManager;
    private static UserManager userManager;

    @Override
    public void onEnable() {
        fileManager = new FileManager(this);
        userManager = new UserManager();
        userManager.createAllUsers();

        this.getCommand("color").setExecutor(new ColorCommand());
        this.getCommand("color").setAliases(Arrays.asList("colour"));
        this.getServer().getPluginManager().registerEvents(new ServerEvents(), this);
    }

    @Override
    public void onDisable() {

    }

    public static FileManager fileManager() {
        return fileManager;
    }

    public static UserManager userManager() {
        return userManager;
    }
    public static final Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");

    public static String format(String string) {
        Matcher match = pattern.matcher(string);
        while(match.find()) {
            String color = string.substring(match.start(), match.end());
            string = string.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(string);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
