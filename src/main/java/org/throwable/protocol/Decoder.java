package org.throwable.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.throwable.serializer.Serializer;
import org.throwable.serializer.impl.KryoSerializer;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 14:39
 */
public class Decoder extends LengthFieldBasedFrameDecoder{

    private final Serializer serializer = new KryoSerializer();

    public Decoder(int maxFrameLength) {
        super(maxFrameLength, 0, 4, 0, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx,in);
        if (null != decode){
            int byteLength = decode.readableBytes();
            byte[] byteHolder = new byte[byteLength];
            decode.readBytes(byteHolder);
            return serializer.deserialize(byteHolder);
        }
        return null;
    }
}
