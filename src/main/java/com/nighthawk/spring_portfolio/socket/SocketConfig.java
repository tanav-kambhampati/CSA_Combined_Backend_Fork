package com.nighthawk.spring_portfolio.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;


@Configuration
public class SocketConfig {

    private String hostname = "localhost";
    private int port = 8080;

    @Bean
    public SocketIOServer socketServer()
    {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        return new SocketIOServer(config);
        //it's him that u need
        //looks js like us
        //us without me :pensive:
    }
}
