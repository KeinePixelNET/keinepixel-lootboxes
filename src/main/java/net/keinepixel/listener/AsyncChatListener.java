package net.keinepixel.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.keinepixel.LootboxesPlugin;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import net.keinepixel.util.ConversationUtil;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record AsyncChatListener(LootboxesPlugin plugin) implements Listener {

    @EventHandler
    public void handle(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (ConversationUtil.isInConversation(player)) {
            String conversationId = ConversationUtil.getConversation(player);
            if (conversationId != null) {
                event.setCancelled(true);
                if (conversationId.equalsIgnoreCase("delete_lootbox")) {
                    String message = ((TextComponent) event.message()).content();
                    Lootbox lootbox = ConversationUtil.getLootbox(player);
                    if (lootbox == null) {
                        player.sendMessage("§cAn error occurred.");
                        ConversationUtil.endConversation(player);
                        return;
                    }
                    if (message.equalsIgnoreCase("yes")) {
                        player.sendMessage(plugin.getLanguageConfiguration().getPrefix().append(plugin.getLanguageConfiguration().getLootboxDeleteSuccess()));
                        plugin.getLootboxManager().delete(lootbox.getIdentifier());
                        ConversationUtil.endConversation(player);
                        return;
                    } else if (message.equalsIgnoreCase("cancel")) {
                        player.sendMessage(plugin.getLanguageConfiguration().getPrefix().append(plugin.getLanguageConfiguration().getLootboxDeleteCancelled()));
                        ConversationUtil.endConversation(player);
                        plugin.getLootboxManager().openEditor(player, lootbox);
                        return;
                    }
                    player.sendMessage(plugin.getLanguageConfiguration().getPrefix().append(plugin.getLanguageConfiguration().getLootboxDeleteConfirm()));
                }
            }
        }
    }

}
