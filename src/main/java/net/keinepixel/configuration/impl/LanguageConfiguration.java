package net.keinepixel.configuration.impl;

import com.google.common.io.Files;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.configuration.Configuration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LanguageConfiguration extends Configuration {

    //Messages
    Component prefix;
    Component noPermission;
    Component lootboxNotFound;
    Component lootboxAlreadyExists;
    Component lootboxDeleteConfirm;
    Component lootboxDeleteCancelled;
    Component lootboxDeleteSuccess;
    Component lootboxCreated;


    @Override
    public void load(File file) {
        if (!file.exists()) {
            try {
                Files.createParentDirs(file);
                file.createNewFile();
                this.configuration = YamlConfiguration.loadConfiguration(file);
                this.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.prefix = MiniMessage.miniMessage().deserialize(this.configuration.getString("prefix", "<gold>[<yellow>KeinePixel<gold>] <white>"));
        this.noPermission = MiniMessage.miniMessage().deserialize(this.configuration.getString("no-permission", "<red>You do not have permission to execute this command."));
        this.lootboxNotFound = MiniMessage.miniMessage().deserialize(this.configuration.getString("general.lootbox.not-found", "<red>Lootbox not found."));
        this.lootboxAlreadyExists = MiniMessage.miniMessage().deserialize(this.configuration.getString("general.lootbox.already-exists", "<red>Lootbox already exists."));
        this.lootboxDeleteConfirm = MiniMessage.miniMessage().deserialize(this.configuration.getString("command.delete.confirm", "<gray>Are you sure you want to delete this lootbox? Type <green>'yes'<gray> to confirm or <red>'cancel'<gray> to cancel."));
        this.lootboxDeleteCancelled = MiniMessage.miniMessage().deserialize(this.configuration.getString("command.delete.cancelled", "<gray>Lootbox deletion cancelled."));
        this.lootboxDeleteSuccess = MiniMessage.miniMessage().deserialize(this.configuration.getString("command.delete.success", "<green>Lootbox deleted."));
        this.lootboxCreated = MiniMessage.miniMessage().deserialize(this.configuration.getString("command.create.success", "<green>Lootbox created."));

    }

    @Override
    public void save(File file) {
        this.configuration.set("prefix", "<gold>[<yellow>KeinePixel<gold>] <white>");
        this.configuration.setComments("prefix", List.of(
                "The prefix for all messages. Only MiniMessage format accepted.",
                "Default: <gold>[<yellow>KeinePixel<gold>] <white>"
        ));
        this.configuration.set("no-permission", "<red>You do not have permission to execute this command.");
        this.configuration.setComments("no-permission", List.of(
                "The message that is sent when a player does not have permission to execute a command. Only MiniMessage format accepted.",
                "Default: <red>You do not have permission to execute this command.",
                "Prefix is automatically added."
        ));
        this.configuration.set("general.lootbox.not-found", "<red>Lootbox not found.");
        this.configuration.setComments("general.lootbox.not-found", List.of(
                "The message that is sent when a lootbox is not found. Only MiniMessage format accepted.",
                "Default: <red>Lootbox not found.",
                "Prefix is automatically added."
        ));
        this.configuration.set("general.lootbox.already-exists", "<red>Lootbox already exists.");
        this.configuration.setComments("general.lootbox.already-exists", List.of(
                "The message that is sent when a lootbox already exists. Only MiniMessage format accepted.",
                "Default: <red>Lootbox already exists.",
                "Prefix is automatically added."
        ));
        this.configuration.set("command.delete.confirm", "<gray>Are you sure you want to delete this lootbox? Type <green>'yes'<gray> to confirm or <red>'cancel'<gray> to cancel.");
        this.configuration.setComments("command.delete.confirm", List.of(
                "The message that is sent when a player wants to delete a lootbox. Only MiniMessage format accepted.",
                "Default: <gray>Are you sure you want to delete this lootbox? Type <green>'yes'<gray> to confirm or <red>'cancel'<gray> to cancel.",
                "Prefix is automatically added."
        ));
        this.configuration.set("command.delete.cancelled", "<gray>Lootbox deletion cancelled.");
        this.configuration.setComments("command.delete.cancelled", List.of(
                "The message that is sent when a player cancels the deletion of a lootbox. Only MiniMessage format accepted.",
                "Default: <gray>Lootbox deletion cancelled.",
                "Prefix is automatically added."
        ));
        this.configuration.set("command.delete.success", "<green>Lootbox deleted.");
        this.configuration.setComments("command.delete.success", List.of(
                "The message that is sent when a lootbox is successfully deleted. Only MiniMessage format accepted.",
                "Default: <green>Lootbox deleted.",
                "Prefix is automatically added."
        ));
        this.configuration.set("command.create.success", "<green>Lootbox created.");
        this.configuration.setComments("command.create.success", List.of(
                "The message that is sent when a lootbox is successfully created. Only MiniMessage format accepted.",
                "Default: <green>Lootbox created.",
                "Prefix is automatically added."
        ));
        try {
            this.configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
