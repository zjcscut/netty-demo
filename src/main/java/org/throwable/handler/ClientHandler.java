package org.throwable.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.throwable.protocol.Response;
import org.throwable.repository.ResponseMessageRepository;

import java.util.concurrent.BlockingQueue;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 15:01
 */
public class ClientHandler extends SimpleChannelInboundHandler<Response> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        logger.info("receive message from server :" + response);
        BlockingQueue<Response> blockingQueue = ResponseMessageRepository.responseRepository.get(response.getRequestId());
        if (null != blockingQueue) {
            blockingQueue.put(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Exception caught on {}, ", ctx.channel(), cause);
        ctx.channel().close();
    }
}
