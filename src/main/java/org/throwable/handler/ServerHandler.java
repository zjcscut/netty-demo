package org.throwable.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.throwable.protocol.Request;
import org.throwable.protocol.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 15:01
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        LOGGER.info("receive request from client :" + request);
        long requestId = request.getRequestId();
        Object content = request.getContent();
        Response response = new Response();
        response.setRequestId(requestId);
        response.setResponse(content + "-" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        channelHandlerContext.pipeline().writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("Exception caught on {}, ", ctx.channel(), cause);
        ctx.channel().close();
    }
}
