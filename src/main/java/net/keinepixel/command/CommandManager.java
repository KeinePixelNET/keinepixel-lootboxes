package net.keinepixel.command;

import net.keinepixel.LootboxesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager extends Command {

    LootboxesPlugin plugin;
    Map<String, KPCommand> subCommands;

    public CommandManager(LootboxesPlugin plugin) {
        super("lootboxes");
        this.plugin = plugin;
        this.subCommands = new ConcurrentHashMap<>();
        Reflections reflections = new Reflections("net.keinepixel.command.impl");
        reflections.getSubTypesOf(KPCommand.class).forEach(command -> {
            try {
                KPCommand instance = command.getDeclaredConstructor(LootboxesPlugin.class).newInstance(plugin);
                this.subCommands.put(instance.name, instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        plugin.getLogger().info("Loaded " + this.subCommands.size() + " sub commands. Type /lootboxes help for more information.");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length == 0) {
            for (KPCommand subCommand : this.subCommands.values()) {
                if (subCommand.permission != null && !sender.hasPermission(subCommand.permission)) {
                    continue;
                }
                if (subCommand.consoleOnly && sender instanceof org.bukkit.entity.Player) {
                    continue;
                }
                if (subCommand.playerOnly && !(sender instanceof org.bukkit.entity.Player)) {
                    continue;
                }
                sender.sendMessage("§c/lootboxes " + subCommand.name + " §7- §e" + subCommand.description);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            for (KPCommand subCommand : this.subCommands.values()) {
                if (subCommand.permission != null && !sender.hasPermission(subCommand.permission)) {
                    continue;
                }
                if (subCommand.consoleOnly && sender instanceof org.bukkit.entity.Player) {
                    continue;
                }
                if (subCommand.playerOnly && !(sender instanceof org.bukkit.entity.Player)) {
                    continue;
                }
                sender.sendMessage("§c/lootboxes " + subCommand.name + " §7- §e" + subCommand.description);
            }
            return true;
        }
        KPCommand subCommand = this.subCommands.get(args[0]);
        if (subCommand == null) {
            sender.sendMessage("§cSub command not found. Type /lootboxes help for more information.");
            return false;
        }
        if (subCommand.permission != null && !sender.hasPermission(subCommand.permission)) {
            sender.sendMessage(subCommand.permissionMessage);
            return false;
        }
        if (subCommand.consoleOnly && sender instanceof org.bukkit.entity.Player) {
            sender.sendMessage("§cThis command can only be executed by the console.");
            return false;
        }
        if (subCommand.playerOnly && !(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("§cThis command can only be executed by a player.");
            return false;
        }
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        subCommand.execute(sender, newArgs);
        return true;
    }
}
