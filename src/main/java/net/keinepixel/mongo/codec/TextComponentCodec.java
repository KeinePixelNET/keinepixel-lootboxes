package net.keinepixel.mongo.codec;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class TextComponentCodec implements Codec<TextComponent> {
    @Override
    public TextComponent decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String text = reader.readString("text");
        reader.readEndDocument();
        return Component.text(text);
    }

    @Override
    public void encode(BsonWriter writer, TextComponent value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("text", value.content());
        writer.writeEndDocument();
    }

    @Override
    public Class<TextComponent> getEncoderClass() {
        return TextComponent.class;
    }
}
