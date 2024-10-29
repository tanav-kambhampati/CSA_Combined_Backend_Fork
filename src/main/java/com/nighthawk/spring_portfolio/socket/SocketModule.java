package com.nighthawk.spring_portfolio.socket;

import org.springframework.stereotype.Component;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class SocketModule {

    private final SocketIOServer server;

    public SocketModule(SocketIOServer server)
    {
        this.server = server;
        this.server.addConnectListener(onConnect());
        this.server.addEventListener("joinroom", String.class, joinRoom());
    }

    private ConnectListener onConnect()
    {
        return (client) ->
        {
            System.out.println(client.getSessionId());
        };
    }

    private DataListener<String> joinRoom()
    {
        return (senderClient, data, ackSender) -> {
            senderClient.joinRoom(data);
            System.out.println(senderClient.getSessionId());
        };
    }
}
