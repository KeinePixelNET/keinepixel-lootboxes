package net.keinepixel.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockCodec implements Codec<Block> {
    @Override
    public Block decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String worldName = reader.readString("world");
        double x = reader.readDouble("x");
        double y = reader.readDouble("y");
        double z = reader.readDouble("z");
        reader.readEndDocument();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }
        return new Location(Bukkit.getWorld(worldName), x, y, z).getBlock();
    }

    @Override
    public void encode(BsonWriter writer, Block value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("world", value.getWorld().getName());
        writer.writeDouble("x", value.getX());
        writer.writeDouble("y", value.getY());
        writer.writeDouble("z", value.getZ());
        writer.writeEndDocument();
    }

    @Override
    public Class<Block> getEncoderClass() {
        return Block.class;
    }
}
