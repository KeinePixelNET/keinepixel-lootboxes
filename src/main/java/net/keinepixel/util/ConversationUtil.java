package net.keinepixel.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import net.keinepixel.mongo.lootbox.model.Lootbox;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ConversationUtil {

    Cache<Player, String> conversationMap = CacheBuilder.newBuilder().expireAfterWrite(5, java.util.concurrent.TimeUnit.MINUTES).build();
    Cache<Player, Lootbox> lootboxMap = CacheBuilder.newBuilder().expireAfterWrite(5, java.util.concurrent.TimeUnit.MINUTES).build();

    public void startConversation(Player player, String conversationId, Lootbox lootbox) {
        conversationMap.put(player, conversationId);
        lootboxMap.put(player, lootbox);
    }

    public void endConversation(Player player) {
        conversationMap.invalidate(player);
        lootboxMap.invalidate(player);
    }

    public boolean isInConversation(Player player) {
        return conversationMap.asMap().containsKey(player) && lootboxMap.asMap().containsKey(player);
    }

    public boolean isInConversation(Player player, String conversationId) {
        return isInConversation(player) && conversationMap.getIfPresent(player).equals(conversationId);
    }

    @Nullable
    public String getConversation(Player player) {
        if (!isInConversation(player)) return null;
        return conversationMap.getIfPresent(player);
    }

    @Nullable
    public Lootbox getLootbox(Player player) {
        if (!isInConversation(player)) return null;
        return lootboxMap.getIfPresent(player);
    }
}
