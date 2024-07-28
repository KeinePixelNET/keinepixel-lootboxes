package net.keinepixel.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ItemStackCodec implements Codec<ItemStack> {

    @Override
    public ItemStack decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String base64 = reader.readString("base64");
        reader.readEndDocument();
        return fromBase64(base64);
    }

    @Override
    public void encode(BsonWriter writer, ItemStack value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("base64", toBase64(value));
        writer.writeEndDocument();
    }

    @Override
    public Class<ItemStack> getEncoderClass() {
        return ItemStack.class;
    }

    private String toBase64(ItemStack itemStack) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(itemStack);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ItemStack fromBase64(String base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) dataInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
