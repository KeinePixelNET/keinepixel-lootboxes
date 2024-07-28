package net.keinepixel.command.impl;

import net.keinepixel.LootboxesPlugin;
import net.keinepixel.command.KPCommand;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class VersionCommand extends KPCommand {

    LootboxesPlugin plugin;

    public VersionCommand(LootboxesPlugin plugin) {
        this.plugin = plugin;
        this.name = "version";
        this.description = "Show the plugin version.";
        this.usage = "/lootboxes version";
    }

    @Override
    public void execute(Player executor, String[] args) {
        executor.sendMessage("§aLootboxes Plugin v1.0.0");
        executor.sendMessage("§aDeveloped by KeinePixel");
        executor.sendMessage("§aWebsite: https://keinepixel.net");
        executor.sendMessage("§aGitHub: https://github.com/KeinePixelNET");
        executor.sendMessage("§aDiscord: https://discord.com/invite/DVj98QwMwQ");
    }

    @Override
    public void execute(ConsoleCommandSender executor, String[] args) {
        executor.sendMessage("§aLootboxes Plugin v1.0.0");
        executor.sendMessage("§aDeveloped by KeinePixel");
        executor.sendMessage("§aWebsite: https://keinepixel.net");
        executor.sendMessage("§aGitHub: https://github.com/KeinePixelNET");
        executor.sendMessage("§aDiscord: https://discord.com/invite/DVj98QwMwQ");
    }
}
