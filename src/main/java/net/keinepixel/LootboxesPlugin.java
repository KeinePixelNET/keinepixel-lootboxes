package net.keinepixel;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import net.keinepixel.command.CommandManager;
import net.keinepixel.configuration.impl.DatabaseConfiguration;
import net.keinepixel.mongo.DatabaseManager;
import net.keinepixel.mongo.codec.BlockCodec;
import net.keinepixel.mongo.codec.LocationCodec;
import net.keinepixel.mongo.codec.TextComponentCodec;
import net.keinepixel.mongo.lootbox.LootboxManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class LootboxesPlugin extends JavaPlugin {

    MongoManager mongoManager;

    DatabaseConfiguration databaseConfiguration;
    DatabaseManager databaseManager;
    InventoryManager inventoryManager;

    LootboxManager lootboxManager;

    @Override
    public void onEnable() {
        this.databaseConfiguration = new DatabaseConfiguration();
        this.databaseConfiguration.load(new File(getDataFolder(), "database.yml"));
        if (this.databaseConfiguration.getMongoUri() == null || this.databaseConfiguration.getMongoDatabase() == null) {
            getLogger().severe("MongoDB URI or database name is not set in the configuration file.");
            getLogger().severe("The plugin will not work without this information.");
            getLogger().severe("Please set the MongoDB URI and database name in the configuration file.");
            getLogger().severe("Then restart the server to apply the changes.");
            getServer().getPluginManager().disablePlugin(this);
            throw new RuntimeException("MongoDB URI or database name is not set in the configuration file.");
        }
        this.mongoManager = new MongoManager(Credentials.of(this.databaseConfiguration.getMongoUri(), this.databaseConfiguration.getMongoDatabase()))
                .registerCodec(new BlockCodec())
                .registerCodec(new LocationCodec())
                .registerCodec(new TextComponentCodec());

        this.databaseManager = new DatabaseManager(this.mongoManager);
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.invoke();

        this.lootboxManager = new LootboxManager(this, this.databaseManager);
        //Register commands and listeners here
        CommandMap commandMap = Bukkit.getServer().getCommandMap();
        commandMap.register("lootboxes", new CommandManager(this));

    }

    @Override
    public void onDisable() {

    }
}
