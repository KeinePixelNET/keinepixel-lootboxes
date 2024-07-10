package net.keinepixel.mongo.lootbox.model;

import eu.koboo.en2do.repository.entity.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.mongo.lootbox.model.item.LootboxItem;
import net.keinepixel.mongo.lootbox.model.key.LootboxKey;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Lootbox {

    @Id
    String identifier;

    Component displayName;
    Block blockToClick;

    LootboxKey key;
    List<LootboxItem> items;

}
