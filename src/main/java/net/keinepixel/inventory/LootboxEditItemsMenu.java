package net.keinepixel.inventory;

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.LootboxesPlugin;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import net.keinepixel.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LootboxEditItemsMenu implements InventoryProvider {

    LootboxesPlugin plugin;
    RyseInventory ryseInventory;
    Lootbox lootbox;

    public LootboxEditItemsMenu(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.ryseInventory = RyseInventory.builder()
                .provider(this)
                .title("Edit Items: " + lootbox.getIdentifier())
                .rows(6)
                .disableUpdateTask()
                .build(plugin);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(IntelligentItem.empty(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build()));
    }

    @Override
    public void close(Player player, RyseInventory inventory) {

    }
}
