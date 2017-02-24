package org.throwable.repository;

import org.throwable.protocol.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 15:06
 */
public class ResponseMessageRepository {

    public static final ConcurrentMap<Long, BlockingQueue<Response>> responseRepository = new ConcurrentHashMap<>();
}
