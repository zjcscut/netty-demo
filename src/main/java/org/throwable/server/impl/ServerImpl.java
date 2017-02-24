package org.throwable.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.throwable.handler.ServerHandler;
import org.throwable.protocol.Decoder;
import org.throwable.protocol.Encoder;
import org.throwable.server.Server;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 16:59
 */
public class ServerImpl implements Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);
    private String host;
    private int port;
    private Channel channel;
    private boolean started;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    public ServerImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void start() {
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new Decoder(10 * 1024 * 1024))
                                .addLast(new Encoder())
                                .addLast(new ServerHandler());
                    }
                });

        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            LOGGER.info("Server Started At {}", port);
            channel = future.channel();
            started = true;
        } catch (Exception e) {
            e.printStackTrace();
            //ignore
        }
    }

    @Override
    public void shutdown() {
        if (started) {
            if (null != channel) {
                channel.close();
            }
        }
        if (null != bossGroup) {
            bossGroup.shutdownGracefully();
        }
        if (null != workerGroup) {
            workerGroup.shutdownGracefully();
        }
    }


}
