package net.keinepixel.mongo.lootbox.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import net.keinepixel.mongo.lootbox.model.Lootbox;

@Collection("lootboxes")
public interface LootboxRepository extends Repository<Lootbox, String> {

}
