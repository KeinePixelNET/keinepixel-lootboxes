package net.keinepixel.configuration.impl;

import com.google.common.io.Files;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DatabaseConfiguration extends Configuration {

    String mongoUri;
    String mongoDatabase;

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
        this.mongoUri = this.configuration.getString("mongodb.uri", "mongodb://localhost:27017");
        this.mongoDatabase = this.configuration.getString("mongodb.database", "keinepixel");
    }

    @Override
    public void save(File file) {
        this.configuration.set("mongodb.uri", this.mongoUri);
        this.configuration.setComments("mongodb.uri", List.of(
                "The MongoDB URI to connect to the database.",
                "Example: mongodb://localhost:27017",
                " ",
                "This is required to connect to the database.",
                "If not set, the plugin will not work."
        ));
        this.configuration.set("mongodb.database", this.mongoDatabase);
        this.configuration.setComments("mongodb.database", List.of(
                "The MongoDB database name the client should connect to.",
                "Example: keinepixel",
                " ",
                "This is required to connect to the mongodb server.",
                "If not set, the plugin will not work."
        ));
        try {
            this.configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
