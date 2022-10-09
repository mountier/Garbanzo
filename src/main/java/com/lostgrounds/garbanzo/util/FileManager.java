package com.lostgrounds.garbanzo.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class FileManager {

    private Plugin _plugin;

    public FileManager(Plugin plugin) {
        _plugin = plugin;
    }

    public File getFile(String fileName) {
        if (!_plugin.getDataFolder().exists())
            _plugin.getDataFolder().mkdirs();

        File file = new File(_plugin.getDataFolder() + File.separator + fileName + ".yml");

        if(file.exists()) {
            try {
                file.createNewFile();
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.save(file);
            } catch (IOException e) {
                _plugin.getLogger().log(Level.WARNING, "Could not create file " + fileName);
                e.printStackTrace();
            }
        }

        return file;
    }

    public YamlConfiguration getYamlConfiguration(File file) {
        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(file);
        return yamlConfig;
    }

    public boolean hasPath(String path, File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.isSet(path))
            return true;
        return false;
    }

    /**
     * SETTER
     */
    public void set(String path, Object object, File file) {
        YamlConfiguration yamlConfig = getYamlConfiguration(file);
        yamlConfig.set(path, object);
        try {
            yamlConfig.save(file);
        } catch (IOException e) {
            _plugin.getLogger().log(Level.WARNING, "Could not save file " + file.getName());
            e.printStackTrace();
        }
    }

    /**
     * GETTERS
     */
    public Object get(String path, File file) {
        YamlConfiguration yamlConfig = getYamlConfiguration(file);
        Object object = yamlConfig.get(path);
        return object;
    }

    public String getString(String path, File file) {
        YamlConfiguration yamlConfig = getYamlConfiguration(file);
        String string = yamlConfig.getString(path);
        return string;
    }

    public boolean getBoolean(String path, File file) {
        YamlConfiguration yamlConfig = getYamlConfiguration(file);
        boolean bool = yamlConfig.getBoolean(path);
        return bool;
    }

    public ItemStack getItemStack(String path, File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getItemStack(path);
    }

    public List<String> getStringList(String path, File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getStringList(path);
    }

}
