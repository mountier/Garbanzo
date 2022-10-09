package com.lostgrounds.garbanzo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

public class AfkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.YELLOW + "Sorry! Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        User user = Main.userManager().getUser(player);

        if(!user.isAfk())
             player.sendMessage(ChatColor.YELLOW + "We'll be seeyin ya");
        user.changeAfkStatus();
        return true;
    }

}