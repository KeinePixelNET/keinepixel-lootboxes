package net.keinepixel.command.impl;

import net.keinepixel.LootboxesPlugin;
import net.keinepixel.command.KPCommand;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateCommand extends KPCommand {

    LootboxesPlugin plugin;

    public CreateCommand(LootboxesPlugin plugin) {
        this.plugin = plugin;
        this.name = "create";
        this.description = "Create a new lootbox.";
        this.usage = "/lootboxes create <name>";
        this.permission = "lootboxes.command.create";
        this.permissionMessage = "You do not have permission to create a lootbox.";
        this.playerOnly = true;
    }

    @Override
    public void execute(Player executor, String[] args) {
        if (args.length >= 1) {
            String lootboxName = args[0];
            executor.sendMessage("§aCreating lootbox with name §e" + lootboxName + "§a...");
            if (plugin.getLootboxManager().exists(lootboxName)) {
                executor.sendMessage("§cA lootbox with the name §e" + lootboxName + "§c already exists.");
                return;
            }
            plugin.getLootboxManager().create(lootboxName);
            executor.sendMessage("§aLootbox §e" + lootboxName + " §acreated successfully.");
            executor.sendMessage("§aYou can now edit the lootbox using §e/lootboxes edit " + lootboxName);
            return;
        }
        executor.sendMessage("§cUsage: §e/lootboxes create <name>");
    }

    @Override
    public void execute(ConsoleCommandSender executor, String[] args) {
    }
}
