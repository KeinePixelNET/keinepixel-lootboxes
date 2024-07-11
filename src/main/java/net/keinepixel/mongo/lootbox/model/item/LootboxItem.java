package net.keinepixel.mongo.lootbox.model.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxItem {

    String identifier;

    String lootboxIdentifier;
    double chance;

    boolean broadcast;

    List<String> commands;
    List<String> messages;

}
