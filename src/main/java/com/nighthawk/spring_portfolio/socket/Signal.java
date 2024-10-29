package com.nighthawk.spring_portfolio.socket;

import org.springframework.stereotype.Controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

@Controller
public class Signal {

    private final SocketIOServer server;

    public Signal(SocketIOServer server)
    {
        this.server = server;
        server.addConnectListener(client -> {
            System.out.println(client.getSessionId());
        });
        server.addEventListener("joinroom", String.class, joinRoom());

        this.server.start();
    }

    private DataListener<String> joinRoom()
    {
        return (senderClient, data, ackSender) -> {
            senderClient.joinRoom(data);
            System.out.println(senderClient.getSessionId());
        };
    }
}
