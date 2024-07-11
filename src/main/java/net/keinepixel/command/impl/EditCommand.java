package net.keinepixel.command.impl;

import net.keinepixel.LootboxesPlugin;
import net.keinepixel.command.KPCommand;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EditCommand extends KPCommand {

    LootboxesPlugin plugin;

    public EditCommand(LootboxesPlugin plugin) {
        this.name = "edit";
        this.description = "Edit a lootbox.";
        this.usage = "/lootboxes edit <name>";
        this.permission = "lootboxes.command.edit";
        this.permissionMessage = "You do not have permission to edit a lootbox.";
        this.playerOnly = true;
    }

    @Override
    public void execute(Player executor, String[] args) {
        if (args.length >= 1) {
            String lootboxName = args[0];
            if (!plugin.getLootboxManager().exists(lootboxName)) {
                executor.sendMessage("§cA lootbox with the name §e" + lootboxName + "§c doesn't exist.");
                return;
            }
            executor.sendMessage("§aOpening lootbox editor for §e" + lootboxName + "§a...");
            plugin.getLootboxManager().openEditor(executor, lootboxName);
            executor.sendMessage("§aLootbox editor opened successfully.");
            return;
        }
        executor.sendMessage("§cUsage: §e/lootboxes create <name>");
    }

    @Override
    public void execute(ConsoleCommandSender executor, String[] args) {
    }
}
