package net.keinepixel.mongo.lootbox;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.LootboxesPlugin;
import net.keinepixel.mongo.DatabaseManager;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import net.keinepixel.mongo.lootbox.repository.LootboxRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LootboxManager {

    protected LootboxesPlugin plugin;
    protected DatabaseManager databaseManager;

    LootboxRepository lootboxRepository;
    Map<String, Lootbox> loadedLootboxes;

    public LootboxManager(LootboxesPlugin plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.databaseManager = databaseManager;

        this.lootboxRepository = this.databaseManager.getLootboxRepository();
        this.loadedLootboxes = this.lootboxRepository.findAll().stream().collect(Collectors.toMap(Lootbox::getIdentifier, lootbox -> lootbox));

        this.plugin.getLogger().info("Loaded " + this.loadedLootboxes.size() + " lootboxes.");
    }


}
