package org.throwable;

import org.throwable.client.Client;
import org.throwable.client.impl.ClientImpl;
import org.throwable.server.Server;
import org.throwable.server.impl.ServerImpl;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 17:12
 */
public class Main {

    public static void main(String[] args) throws Exception{
        String host = "127.0.0.1";
        int serverPort = 9099;
        int clientPort = 9098;
        Server server = new ServerImpl(host,serverPort);
        Client client = new ClientImpl(host,clientPort);
        server.start();

        client.sendMessage("hello world!");
    }
}
