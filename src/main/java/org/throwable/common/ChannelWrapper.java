package org.throwable.common;

import io.netty.channel.Channel;
import lombok.Data;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 15:41
 */
@Data
public class ChannelWrapper {

    private String host;
    private int port;
    private Channel channel;
    private ObjectPool<Channel> channelObjectPool;

    public ChannelWrapper(String host, int port) {
        this.host = host;
        this.port = port;
        this.channelObjectPool = new GenericObjectPool<>(new ConnectionObjectFactory(host,port));
    }

    public void close(){
        channelObjectPool.close();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChannelWrapper{");
        sb.append(", host='").append(host).append('\'');
        sb.append(", ip=").append(port);
        sb.append(", channel=").append(channel);
        sb.append(", channelObjectPool=").append(channelObjectPool);
        sb.append('}');
        return sb.toString();
    }
}
