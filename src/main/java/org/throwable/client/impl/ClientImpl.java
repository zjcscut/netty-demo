package org.throwable.client.impl;

import io.netty.channel.Channel;
import org.throwable.client.Client;
import org.throwable.common.ChannelWrapper;
import org.throwable.protocol.Request;
import org.throwable.protocol.Response;
import org.throwable.repository.ResponseMessageRepository;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 15:18
 */
public class ClientImpl implements Client {

    private static AtomicLong atomicLong = new AtomicLong();

    private int requestTimeoutMillis = 10 * 1000;

    private String host;
    private int port;

    public ClientImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //持有连接
    private static final ConcurrentMap<String, ChannelWrapper> holder = new ConcurrentHashMap<>();

    @Override
    public void start() {

    }

    @Override
    public void close() {

    }

    @Override
    public Response sendMessage(String content) {
        Request request = new Request();
        request.setRequestId(atomicLong.incrementAndGet());
        request.setContent(content);
        request.setRequestTime(System.currentTimeMillis());
        String conn = host + ":" + port;
        ChannelWrapper channelWrapper = holder.get(conn);
        Channel channel = null;
        if (null == channelWrapper) {
            channelWrapper = new ChannelWrapper(host, port);
            holder.putIfAbsent(conn, channelWrapper);
            try {
                channel = channelWrapper.getChannelObjectPool().borrowObject();
            } catch (Exception e) {
                //ignore
            }
        }

        if (null == channel) {
            Response response = new Response();
            RuntimeException runtimeException = new RuntimeException("Channel is not available now");
            response.setThrowable(runtimeException);
            return response;
        }

        try {
            BlockingQueue<Response> blockingQueue = new ArrayBlockingQueue<>(1);
            ResponseMessageRepository.responseRepository.put(request.getRequestId(), blockingQueue);
            channel.writeAndFlush(request);
            return blockingQueue.poll(requestTimeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                channelWrapper.getChannelObjectPool().returnObject(channel);
            } catch (Exception e) {
                //ignore
            }
            ResponseMessageRepository.responseRepository.remove(request.getRequestId());
        }
    }
}
