package com.nighthawk.spring_portfolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;


@Configuration
public class socketIOConfig {

    @Value("${socket-server.host}")
    private String hostname;

    @Value("${socket-server.port}")
    private int port;

    @Bean
    public SocketIOServer socketIOServer()
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
