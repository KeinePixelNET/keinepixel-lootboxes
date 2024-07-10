package net.keinepixel.mongo;

import eu.koboo.en2do.MongoManager;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.mongo.lootbox.repository.LootboxRepository;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseManager {

    protected MongoManager mongoManager;

    LootboxRepository lootboxRepository;

    public DatabaseManager(MongoManager mongoManager) {
        this.mongoManager = mongoManager;

        this.lootboxRepository = this.mongoManager.create(LootboxRepository.class);
    }
}
