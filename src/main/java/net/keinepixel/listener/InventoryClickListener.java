package net.keinepixel.listener;

import net.keinepixel.LootboxesPlugin;
import net.keinepixel.inventory.LootboxEditItemsMenu;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import net.keinepixel.mongo.lootbox.model.item.LootboxItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public record InventoryClickListener(LootboxesPlugin plugin) implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent event) {
        PlayerInventory playerInventory = event.getWhoClicked().getInventory();
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getOriginalTitle().startsWith("Edit Items: ")) {
            if (event.getClickedInventory() == playerInventory) {
                event.setCancelled(true);
                if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
                String randomString = getRandomString();
                LootboxItem lootboxItem = new LootboxItem();
                lootboxItem.setIdentifier(randomString);
                lootboxItem.setItemStack(event.getCurrentItem());
                lootboxItem.setChance(0);
                lootboxItem.setBroadcast(false);
                lootboxItem.setCommands(new LinkedList<>());
                lootboxItem.setMessages(new LinkedList<>());

                Lootbox lootbox = plugin.getLootboxManager().get(player.getOpenInventory().getOriginalTitle().replaceFirst("Edit Items: ", ""));
                lootbox.getItems().add(lootboxItem);
                plugin.getLootboxManager().getLoadedLootboxes().put(lootbox.getIdentifier(), lootbox);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                new LootboxEditItemsMenu(plugin, lootbox).getRyseInventory().open(player);
            }
        }
    }

    private String getRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            randomString.append(characters.charAt(ThreadLocalRandom.current().nextInt(characters.length())));
        }
        return randomString.toString();
    }

}
