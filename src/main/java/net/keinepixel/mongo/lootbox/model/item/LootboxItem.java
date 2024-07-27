package net.keinepixel.mongo.lootbox.model.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxItem {

    String identifier;
    ItemStack itemStack;

    String lootboxIdentifier;
    double chance;

    boolean broadcast = false;

    List<String> commands = new LinkedList<>();
    List<String> messages = new LinkedList<>();

}
