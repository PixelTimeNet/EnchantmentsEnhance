package org.pixeltime.enchantmentsenhance.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.pixeltime.enchantmentsenhance.Main;
import org.pixeltime.enchantmentsenhance.manager.language.Language_zh_cn;
import org.pixeltime.enchantmentsenhance.manager.language.Language_en_us;
import java.io.File;
import java.io.IOException;

public class SettingsManager {

    public SettingsManager() {
    }

    public static FileConfiguration config;
    public static File cfile;

    public static FileConfiguration data;
    public static File dfile;

    public static FileConfiguration lang;
    public static File langfile;


    public static void setup() {
        cfile = new File(Main.getMain().getDataFolder(), "config.yml");
        config = Main.getMain().getConfig();
        if (!Main.getMain().getDataFolder().exists()) {
            Main.getMain().getDataFolder().mkdir();
        }

        dfile = new File(Main.getMain().getDataFolder(), "data.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED
                    + "Could not create data.yml!");
            }
        }
        data = YamlConfiguration.loadConfiguration(dfile);

        langfile = new File(Main.getMain().getDataFolder(), "lang.yml");
        if (!langfile.exists()) {
            try {
                langfile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED
                    + "Could not create lang.yml!");
            }
        }
        lang = YamlConfiguration.loadConfiguration(langfile);
        Language_en_us.addLang();
        if (config.getString("language").equalsIgnoreCase("zh_cn")) {
            Language_zh_cn.addLang();
        }
    }


    public static void saveData() {
        try {
            data.save(dfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED
                + "Could not save data.yml!");
        }
    }


    public static void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
    }


    public static void saveConfig() {
        try {
            config.save(cfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED
                + "Could not save config.yml!");
        }
    }


    public static void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }


    public static void saveLang() {
        try {
            lang.save(langfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED
                + "Could not save lang.yml!");
        }
    }


    public static void reloadLang() {
        lang = YamlConfiguration.loadConfiguration(langfile);
    }
}
