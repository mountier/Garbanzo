package com.lostgrounds.garbanzo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

public class ColorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.YELLOW + "Sorry! Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        User user = Main.userManager().getUser(player);

        if(args.length >= 1) {
            String code = args[0];
            Matcher match = Main.pattern.matcher(code);
            if (match.find()) {
                String colorcode = code.substring(match.start(), match.end());
                ChatColor color = ChatColor.of(colorcode);
                player.sendMessage("Your username has now been changed to " + color + "this color.");
                user.setNameColor(colorcode);
                return true;
            }
        }

        player.sendMessage(ChatColor.YELLOW + "You didn't enter a valid hexadecimal color you absolute buffoon.");
        player.sendMessage(ChatColor.YELLOW + "HINT: /color #FFFFFF");
        return true;
    }

}
