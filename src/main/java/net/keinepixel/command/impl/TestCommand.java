package net.keinepixel.command.impl;

import net.keinepixel.command.KPCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends KPCommand {

    public TestCommand() {
        this.name = "test";
        this.description = "Test command";
        this.usage = "/test";
        this.playerOnly = true;
    }

    @Override
    public void execute(Player executor, String[] args) {
        executor.sendMessage("Test command executed.");
    }

    @Override
    public void execute(ConsoleCommandSender executor, String[] args) {
    }
}
