package net.keinepixel;

import com.destroystokyo.paper.exception.ServerPluginException;
import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import net.keinepixel.command.CommandManager;
import net.keinepixel.configuration.impl.DatabaseConfiguration;
import net.keinepixel.configuration.impl.LanguageConfiguration;
import net.keinepixel.listener.AsyncChatListener;
import net.keinepixel.listener.InventoryClickListener;
import net.keinepixel.mongo.DatabaseManager;
import net.keinepixel.mongo.codec.BlockCodec;
import net.keinepixel.mongo.codec.ItemStackCodec;
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
    LanguageConfiguration languageConfiguration;

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
            try {
                throw new ServerPluginException(new Throwable("MongoDB URI or database name is not set in the configuration file."), this);
            } catch (ServerPluginException e) {
                throw new RuntimeException(e);
            }
        }
        this.mongoManager = new MongoManager(Credentials.of(this.databaseConfiguration.getMongoUri(), this.databaseConfiguration.getMongoDatabase()))
                .registerCodec(new BlockCodec())
                .registerCodec(new ItemStackCodec())
                .registerCodec(new LocationCodec())
                .registerCodec(new TextComponentCodec());

        this.languageConfiguration = new LanguageConfiguration();
        this.languageConfiguration.load(new File(getDataFolder(), "language.yml"));

        this.databaseManager = new DatabaseManager(this.mongoManager);
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.invoke();

        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(this), this);

        this.lootboxManager = new LootboxManager(this, this.databaseManager);
        CommandMap commandMap = Bukkit.getServer().getCommandMap();
        commandMap.register("lootboxes", new CommandManager(this));
    }
}
