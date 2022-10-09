package com.lostgrounds.garbanzo;

import com.lostgrounds.garbanzo.util.FileManager;
import com.lostgrounds.garbanzo.util.UserManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main extends JavaPlugin {

    private static FileManager fileManager;
    private static UserManager userManager;

    private int afkTimeoutLength;
    private static List<String> messagesOfTheDay;
    private static ArrayList<String> unusedMOTD;
    private static ArrayList<String> usedMOTD;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        fileManager = new FileManager(this);
        userManager = new UserManager();
        userManager.createAllUsers();

        afkTimeoutLength = (int) this.getConfig().get("afk-timeout-length");
        messagesOfTheDay = (List<String>) this.getConfig().get("messages-of-the-day");

        unusedMOTD = new ArrayList<String>();
        unusedMOTD.addAll(messagesOfTheDay);
        usedMOTD = new ArrayList<String>();

        this.getCommand("afk").setExecutor(new AfkCommand());
        this.getCommand("color").setExecutor(new ColorCommand());
        this.getCommand("color").setAliases(Arrays.asList("colour"));
        this.getServer().getPluginManager().registerEvents(new ServerEvents(), this);

        loop();
    }

    @Override
    public void onDisable() {

    }

    public void loop() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            User user = userManager().getUser(p);
                            long elapsed = System.nanoTime() - user.getLastInteraction();
                            long elapsedMinutes = TimeUnit.MINUTES.convert(elapsed, TimeUnit.MINUTES);

                            if(elapsedMinutes >= (afkTimeoutLength + 0.0))
                                user.changeAfkStatus();
                        }
                    }
                }
        , 20L, 20L);
    }

    public static FileManager fileManager() {
        return fileManager;
    }

    public static UserManager userManager() {
        return userManager;
    }

    public static String getNextMOTD() {
        Random random = new Random();
        int r = random.nextInt(unusedMOTD.size());
        String output = unusedMOTD.get(r);

        unusedMOTD.remove(r);
        usedMOTD.add(output);

        if (unusedMOTD.size() == 0) {
            unusedMOTD.addAll(messagesOfTheDay);
            usedMOTD.clear();
        }
        return output;
    }

    public static final Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");

    public static String format(String string) {
        Matcher match = pattern.matcher(string);
        while (match.find()) {
            String color = string.substring(match.start(), match.end());
            string = string.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(string);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
