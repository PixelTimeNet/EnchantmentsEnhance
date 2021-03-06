package org.pixeltime.enchantmentsenhance.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.pixeltime.enchantmentsenhance.manager.ItemManager;
import org.pixeltime.enchantmentsenhance.manager.SettingsManager;

/**
 * 
 * @author HealPot
 * @version Feb 9, 2018
 *
 */
public class Broadcast {
    /**
     * Sends a message to all online players.
     * 
     * @param player
     * @param item
     * @param enchantLevel
     * @param success
     */
    public static void broadcast(
        Player player,
        ItemStack item,
        int enchantLevel,
        boolean success) {
        if (success) {
            Bukkit.broadcastMessage(Util.toColor(SettingsManager.lang.getString(
                "Config.pluginTag") + (SettingsManager.lang.getString(
                    "Annoucer.success") + " " + player.getName() + " "
                    + SettingsManager.lang.getString("Annoucer.got") + " "
                    + ItemManager.getRenamedName(item)) + " "
                + getFriendlyName(item)));
        }
        else {
            Bukkit.broadcastMessage(Util.toColor(SettingsManager.lang.getString(
                "Config.pluginTag") + (SettingsManager.lang.getString(
                    "Annoucer.failed") + " " + player.getName() + " "
                    + SettingsManager.lang.getString("Annoucer.lost") + " "
                    + ItemManager.getRenamedName(item)) + " "
                + getFriendlyName(item)));
        }
    }


    /**
     * Removes any underscores and capitalizes first letter of each word in the
     * string.
     * 
     * @param s
     * @return
     */
    public static String format(String s) {
        if (!s.contains("_")) {
            return capitalize(s);
        }
        String[] j = s.split("_");

        String c = "";

        for (String f : j) {
            f = capitalize(f);
            c += c.equalsIgnoreCase("") ? f : " " + f;
        }
        return c;
    }


    /**
     * Capitalizes first letter of each word in the string.
     * 
     * @param text
     * @return
     */
    public static String capitalize(String text) {
        String firstLetter = text.substring(0, 1).toUpperCase();
        String next = text.substring(1).toLowerCase();
        String capitalized = firstLetter + next;
        return capitalized;
    }


    /**
     * This will return a formatted name of a item.
     * 
     * @param item
     * @return
     */
    public static String getFriendlyName(ItemStack item) {
        Material m = item.getType();
        String name = format(m.name());
        return name;
    }


    /**
     * This will return a localized name of a item.
     * 
     * @param item
     * @return
     */
    public static String getLocalizedName(ItemStack item) {
        return null;
    }
}
