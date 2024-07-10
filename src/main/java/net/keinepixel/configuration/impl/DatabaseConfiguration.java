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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        this.mongoUri = configuration.getString("mongodb.uri");
        this.mongoDatabase = configuration.getString("mongodb.database");
    }

    @Override
    public void save(File file) {
        configuration.set("mongodb.uri", this.mongoUri);
        configuration.setComments("mongodb.uri", List.of(
                "The MongoDB URI to connect to the database.",
                "Example: mongodb://localhost:27017",
                " ",
                "This is required to connect to the database.",
                "If not set, the plugin will not work."
        ));
        configuration.set("mongodb.database", this.mongoDatabase);
        configuration.setComments("mongodb.database", List.of(
                "The MongoDB database name the client should connect to.",
                "Example: keinepixel",
                " ",
                "This is required to connect to the mongodb server.",
                "If not set, the plugin will not work."
        ));
        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
