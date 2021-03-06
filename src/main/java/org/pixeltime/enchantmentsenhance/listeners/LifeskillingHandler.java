package org.pixeltime.enchantmentsenhance.listeners;

import java.util.List;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.pixeltime.enchantmentsenhance.manager.SettingsManager;
import org.pixeltime.enchantmentsenhance.util.AnimalBreeding;
import org.pixeltime.enchantmentsenhance.util.Util;

public class LifeskillingHandler implements Listener {
    private final List<String> mining = SettingsManager.config.getStringList(
        "lifeskill.mining");
    private final List<String> chopping = SettingsManager.config.getStringList(
        "lifeskill.chopping");
    @SuppressWarnings("unused")
    private final List<String> fishing = SettingsManager.config.getStringList(
        "lifeskill.fishing");
    @SuppressWarnings("unused")
    private final List<String> killing = SettingsManager.config.getStringList(
        "lifeskill.killing");
    @SuppressWarnings("unused")
    private final List<String> breeding = SettingsManager.config.getStringList(
        "lifeskill.breeding");
    @SuppressWarnings("unused")
    private final List<String> smelting = SettingsManager.config.getStringList(
        "lifeskill.smelting");

    private final int miningChance = SettingsManager.config.getInt(
        "reward.mining.chance");
    private final int choppingChance = SettingsManager.config.getInt(
        "reward.chopping.chance");
    private final int fishingChance = SettingsManager.config.getInt(
        "reward.fishing.chance");
    private final int killingChance = SettingsManager.config.getInt(
        "reward.killing.chance");
    private final int breedingChance = SettingsManager.config.getInt(
        "reward.breeding.chance");
    private final int smeltingChance = SettingsManager.config.getInt(
        "reward.smelting.chance");

    private final List<Integer> miningLootTable = SettingsManager.config
        .getIntegerList("reward.mining.drops");
    private final List<Integer> choppingLootTable = SettingsManager.config
        .getIntegerList("reward.chopping.drops");
    private final List<Integer> fishingLootTable = SettingsManager.config
        .getIntegerList("reward.fishing.drops");
    private final List<Integer> killingLootTable = SettingsManager.config
        .getIntegerList("reward.killing.drops");
    private final List<Integer> breedingLootTable = SettingsManager.config
        .getIntegerList("reward.breeding.drops");
    private final List<Integer> smeltingLootTable = SettingsManager.config
        .getIntegerList("reward.breeding.drops");

    private final Random random = new Random();


    /**
     * Mining gives enhancement stone.
     * 
     * @param e
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onMining(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if (mining.contains(e.getBlock().getType().toString()))
            if (miningChance > random.nextDouble()) {
                randomDrop(player, miningLootTable);
            }
    }


    private void randomDrop(Player player, List<Integer> miningLootTable2) {
        int stoneType = miningLootTable.get(random.nextInt(miningLootTable
            .size()));
        org.pixeltime.enchantmentsenhance.events.inventory.Inventory.addLevel(
            player, stoneType, 1);
        Util.sendMessage(SettingsManager.lang.getString("Item.get")
            + SettingsManager.lang.getString("Item." + stoneType), player);
    }


    /**
     * Mining gives enhancement stone.
     * 
     * @param e
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onChopping(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if (chopping.contains(e.getBlock().getType().toString()))
            if (choppingChance > random.nextDouble()) {
                randomDrop(player, choppingLootTable);
            }
    }


    /**
     * Fishing gives enhancement stone.
     * 
     * @param e
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onFishing(PlayerFishEvent e) {
        // If the fishing is successful
        if (e.getState().equals(State.CAUGHT_FISH)) {
            Player player = e.getPlayer();
            if (fishingChance > random.nextDouble()) {
                randomDrop(player, fishingLootTable);
            }
        }
    }


    /**
     * Killing mobs gives enhancement stone.
     * 
     * @param e
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onKilling(EntityDeathEvent e) {
        // If the killed entity is a monster
        if (e.getEntity() instanceof Monster) {
            if (e.getEntity().getKiller() instanceof Player) {
                Player player = e.getEntity().getKiller();
                if (killingChance > random.nextDouble()) {
                    randomDrop(player, killingLootTable);
                }
            }
        }
    }


    /**
     * Breeding animals gives enhancement stone.
     * 
     * @param e
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBreeding(PlayerInteractEntityEvent e) {
        if (AnimalBreeding.breeadableFood.contains(e.getPlayer().getItemInHand()
            .getType())) {
            if (AnimalBreeding.breeadableAnimals.contains(e.getRightClicked()
                .getType())) {
                Animals a = (Animals)e.getRightClicked();
                if (a.canBreed()) {
                    if (breedingChance > random.nextDouble()) {
                        randomDrop(e.getPlayer(), breedingLootTable);
                    }
                    a.setBreed(false);
                }
            }
        }
    }


    /**
     * Smelting items gives enhancement stone.
     * 
     * @param e
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onSmelting(FurnaceExtractEvent e) {
        for (int i = 0; i < e.getItemAmount(); i++) {
            if (smeltingChance > random.nextDouble()) {
                randomDrop(e.getPlayer(), smeltingLootTable);
            }
        }

    }


    /**
     * Fix a bukkit bug where shift click doesn't register.
     * 
     * @param m
     * @param e
     * @return
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onSmelting2(InventoryClickEvent e) {
        Inventory clickedInventory = null;
        if (e.getSlot() < 0) {
            clickedInventory = null;
        }
        else if (e.getView().getTopInventory() != null && e.getSlot() < e
            .getView().getTopInventory().getSize()) {
            clickedInventory = e.getView().getTopInventory();
        }
        else {
            clickedInventory = e.getView().getBottomInventory();
        }
        if (clickedInventory == null) {
            return;
        }
        if (!clickedInventory.getType().equals(InventoryType.FURNACE)) {
            return;
        }
        FurnaceInventory fi = (FurnaceInventory)clickedInventory;
        boolean click = e.getClick().isShiftClick() || e.getClick()
            .isLeftClick() && e.getRawSlot() == 2;
        boolean item = fi.getResult() != null;
        if (click && item) {
            for (int i = 0; i < fi.getResult().getAmount(); i++) {
                if (smeltingChance > random.nextDouble()) {
                    randomDrop((Player)e.getWhoClicked(), smeltingLootTable);
                }
            }
        }
    }
}
