package org.throwable.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.throwable.serializer.Serializer;
import org.throwable.serializer.impl.KryoSerializer;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 14:49
 */
public class Encoder extends MessageToByteEncoder<Object> {

    private final Serializer serializer = new KryoSerializer();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] bytes = serializer.serialize(o);
        int length = bytes.length;
        byteBuf.writeInt(length);
        byteBuf.writeBytes(bytes);
    }
}
