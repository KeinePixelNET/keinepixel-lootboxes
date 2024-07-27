package net.keinepixel.inventory;

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.Pagination;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import io.github.rysefoxx.inventory.plugin.pagination.SlotIterator;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.LootboxesPlugin;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import net.keinepixel.mongo.lootbox.model.item.LootboxItem;
import net.keinepixel.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

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
        SlotIterator slotIterator = SlotIterator.builder()
                .override()
                .startPosition(0)
                .endPosition(44)
                .build();

        Pagination pagination = contents.pagination();
        pagination.iterator(slotIterator);

        List<IntelligentItem> items = new LinkedList<>();
        for (LootboxItem lootboxItem : this.lootbox.getItems()) {
            items.add(IntelligentItem.of(ItemBuilder.of(lootboxItem.getItemStack()).displayName("§a" + lootboxItem.getIdentifier()).loreOld(List.of(
                    "§7Chance: §a" + lootboxItem.getChance() + "%",
                    "",
                    "§7Broadcast: " + (lootboxItem.isBroadcast() ? "§aYes" : "§cNo"),
                    "§7Commands: §a" + lootboxItem.getCommands().size(),
                    "§7Messages: §a" + lootboxItem.getMessages().size(),
                    "",
                    "§aRightclick§7 to edit this item.",
                    "§cLeftclick§7 to delete this item."
            )).build(), event -> {
                //TODO
            }));
        }

        pagination.setItems(items);

        contents.fillArea(45, 53, IntelligentItem.empty(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build()));
        contents.set(5, 3, IntelligentItem.of(ItemBuilder.of(Material.BARRIER).displayName("§cBack").build(), event -> {
            plugin.getLootboxManager().save(lootbox);
            new LootboxEditMainMenu(plugin, lootbox).getRyseInventory().open(player);
        }));
        contents.set(5, 5, IntelligentItem.empty(ItemBuilder.of(Material.EMERALD).displayName("§aAdd item").loreOld(List.of(
                "§7To add a new item to the lootbox,",
                "§7simply click on an item in your inventory.",
                "§7The item will be added to the lootbox and",
                "§7you can edit it afterwards. The inventory will",
                "§7be updated automatically."
        )).build()));
        contents.set(5, 7, IntelligentItem.of(ItemBuilder.of(Material.ARROW).displayName("§7Next page").build(), event -> {
            if (!pagination.isLast()) {
                pagination.next();
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
        }));
        contents.set(5, 1, IntelligentItem.of(ItemBuilder.of(Material.ARROW).displayName("§7Previous page").build(), event -> {
            if (!pagination.isFirst()) {
                pagination.previous();
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
        }));

    }

    @Override
    public void close(Player player, RyseInventory inventory) {

    }
}
