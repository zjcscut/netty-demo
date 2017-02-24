package org.throwable.client;

import org.throwable.protocol.Response;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 15:09
 */
public interface Client {

    void start();
    void close();
    Response sendMessage(String content);
}
