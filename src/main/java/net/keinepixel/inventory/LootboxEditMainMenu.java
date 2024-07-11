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

import java.util.List;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LootboxEditMainMenu implements InventoryProvider {

    LootboxesPlugin plugin;
    Lootbox lootbox;
    RyseInventory ryseInventory;

    public LootboxEditMainMenu(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.ryseInventory = RyseInventory.builder()
                .provider(this)
                .title("Edit Lootbox: " + lootbox.getIdentifier())
                .rows(3)
                .disableUpdateTask()
                .build(plugin);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(IntelligentItem.empty(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build()));
        contents.set(1, 5, IntelligentItem.of(ItemBuilder.of(Material.GRASS_BLOCK).displayName("§aChange block").loreOld(List.of(
                "§7Change the block that represents this lootbox.",
                "§r",
                "§7Current block: §a" + (lootbox.getBlockToClick() != null ? lootbox.getBlockToClick().getType().name() + " @ " + lootbox.getBlockToClick().getLocation().getBlockX() + ", " + lootbox.getBlockToClick().getLocation().getBlockY() + ", " + lootbox.getBlockToClick().getLocation().getBlockZ() : "§cNone")
        )).build(), event -> {
            //TODO
        }));
        contents.set(1, 3, IntelligentItem.of(ItemBuilder.of(Material.CLOCK).displayName("§aSet available date").loreOld(List.of(
                "§7Set the date when this lootbox will be available.",
                "§7May be used for seasonal lootboxes."
        )).build(), event -> {
            //TODO
        }));
        contents.set(1, 1, IntelligentItem.of(ItemBuilder.of(Material.BARRIER).displayName("§cDelete lootbox").loreOld(List.of(
                "§7Delete this lootbox.",
                "§r",
                "§4§l!§c This action cannot be undone."
        )).build(), event -> {
            //TODO
        }));
        contents.set(1, 7, IntelligentItem.of(ItemBuilder.of(Material.CHEST).displayName("§aManage items").loreOld(List.of(
                "§7Manage the items that can be obtained from this lootbox.",
                "§r",
                "§7Current items: §a" + lootbox.getItems().size()
        )).build(), event -> new LootboxEditItemsMenu(plugin, lootbox).getRyseInventory().open(player)));
    }

    @Override
    public void close(Player player, RyseInventory inventory) {

    }
}
