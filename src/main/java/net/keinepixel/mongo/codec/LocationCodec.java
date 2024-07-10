package net.keinepixel.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationCodec implements Codec<Location> {
    @Override
    public Location decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String worldName = reader.readString("world");
        double x = reader.readDouble("x");
        double y = reader.readDouble("y");
        double z = reader.readDouble("z");
        float yaw = (float) reader.readDouble("yaw");
        float pitch = (float) reader.readDouble("pitch");
        reader.readEndDocument();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public void encode(BsonWriter writer, Location value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("world", value.getWorld().getName());
        writer.writeDouble("x", value.getX());
        writer.writeDouble("y", value.getY());
        writer.writeDouble("z", value.getZ());
        writer.writeDouble("yaw", value.getYaw());
        writer.writeDouble("pitch", value.getPitch());
        writer.writeEndDocument();
    }

    @Override
    public Class<Location> getEncoderClass() {
        return Location.class;
    }
}
