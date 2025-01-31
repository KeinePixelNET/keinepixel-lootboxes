package net.keinepixel.mongo.lootbox;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.LootboxesPlugin;
import net.keinepixel.inventory.LootboxEditMainMenu;
import net.keinepixel.mongo.DatabaseManager;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import net.keinepixel.mongo.lootbox.repository.LootboxRepository;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LootboxManager implements Listener {

    protected LootboxesPlugin plugin;
    protected DatabaseManager databaseManager;

    LootboxRepository lootboxRepository;
    Map<String, Lootbox> loadedLootboxes;

    public LootboxManager(LootboxesPlugin plugin, DatabaseManager databaseManager) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.databaseManager = databaseManager;

        this.lootboxRepository = this.databaseManager.getLootboxRepository();
        this.loadedLootboxes = this.lootboxRepository.findAll().stream().collect(Collectors.toMap(Lootbox::getIdentifier, lootbox -> lootbox));

        this.plugin.getLogger().info("Loaded " + this.loadedLootboxes.size() + " lootboxes.");
    }

    @EventHandler
    public void onDisable(final PluginDisableEvent event) {
        if (event.getPlugin() != this.plugin) return;
        this.loadedLootboxes.values().forEach(this.lootboxRepository::save);
        plugin.getLogger().info("Saved " + this.loadedLootboxes.size() + " lootboxes.");
    }

    public boolean exists(String identifier) {
        return this.loadedLootboxes.containsKey(identifier);
    }

    public void save(Lootbox lootbox) {
        this.lootboxRepository.save(lootbox);
        this.loadedLootboxes.put(lootbox.getIdentifier(), lootbox);
    }

    public Lootbox get(String identifier) {
        return this.loadedLootboxes.get(identifier);
    }

    public Lootbox create(String lootboxName) {
        Lootbox lootbox = new Lootbox();
        lootbox.setIdentifier(lootboxName);
        lootbox.setDisplayName(Component.text(lootboxName));
        lootbox.setItems(new ArrayList<>());
        lootbox.setBlockToClick(null);
        this.loadedLootboxes.put(lootboxName, lootbox);
        return lootbox;
    }

    public void delete(String identifier) {
        this.lootboxRepository.delete(this.loadedLootboxes.remove(identifier));
    }

    public void openEditor(Player executor, String lootboxName) {
        new LootboxEditMainMenu(plugin, get(lootboxName)).getRyseInventory().open(executor);
    }

    public void openEditor(Player executor, Lootbox lootbox) {
        new LootboxEditMainMenu(plugin, lootbox).getRyseInventory().open(executor);
    }

}
