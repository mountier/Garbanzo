package com.lostgrounds.garbanzo.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.lostgrounds.garbanzo.User;

public class UserManager {

    private HashMap<UUID, User> users;

    public UserManager() {
        users = new HashMap<UUID, User>();
    }

    public void createAllUsers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = new User(player);
            users.put(player.getUniqueId(), user);
        }
    }

    public User getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public User getUser(UUID id) {
        if (users.containsKey(id))
            return users.get(id);
        else
            return null;
    }

    public void addUser(User user) {
        users.put(user.getPlayer().getUniqueId(), user);
    }

    public void removeUser(User user) {
        users.remove(user.getPlayer().getUniqueId());
    }

}
