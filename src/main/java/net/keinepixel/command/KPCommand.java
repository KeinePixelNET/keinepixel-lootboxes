package net.keinepixel.command;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class KPCommand {

    String name;
    String description = "";
    String usage = "";
    String permission = null;
    String permissionMessage = null;
    boolean playerOnly = false;
    boolean consoleOnly = false;

    public void execute(CommandSender executor, String[] args) {
        if (executor instanceof Player) {
            this.execute((Player) executor, args);
        } else if (executor instanceof ConsoleCommandSender) {
            this.execute((ConsoleCommandSender) executor, args);
        }
    }

    public abstract void execute(Player executor, String[] args);
    public abstract void execute(ConsoleCommandSender executor, String[] args);

}
