package com.messinger.app;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class webSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chatMensajes")
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(org.springframework.http.server.ServerHttpRequest request,
                                                      org.springframework.web.socket.WebSocketHandler wsHandler,
                                                      Map<String, Object> attributes) {
                        // Si el interceptor puso userId en attributes, lo usamos
                        Object userId = attributes.get("userId");
                        if (userId != null) {
                            return new StompPrincipal(userId.toString());
                        }
                        return super.determineUser(request, wsHandler, attributes);
                    }
                })
                .addInterceptors(new UserHandshakeInterceptor())
                .withSockJS();
        registry.addEndpoint("/grupoMensajes")
            .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(org.springframework.http.server.ServerHttpRequest request,
                                                      org.springframework.web.socket.WebSocketHandler wsHandler,
                                                      Map<String, Object> attributes) {
                        // Si el interceptor puso userId en attributes, lo usamos
                        Object userId = attributes.get("userId");
                        if (userId != null) {
                            return new StompPrincipal(userId.toString());
                        }
                        return super.determineUser(request, wsHandler, attributes);
                    }
                })
                .addInterceptors(new UserHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}