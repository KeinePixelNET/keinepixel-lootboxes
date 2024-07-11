package net.keinepixel.mongo.lootbox.model.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxKey {

    String identifier;
    String lootboxIdentifier;

    Material material;

    TextComponent displayName;
    List<TextComponent> lore;

}
