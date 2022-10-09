package com.lostgrounds.garbanzo;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = new User(player);
        Main.userManager().addUser(user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = Main.userManager().getUser(player);
        Main.userManager().removeUser(user);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = Main.userManager().getUser(player);

        event.setFormat(user.getNameColor() + player.getName() + ChatColor.WHITE + ": " + event.getMessage());
    }
}
