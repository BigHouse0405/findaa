package com.bntech.bnauth.api.controller;

import com.bntech.bnauth.data.object.AuthenticationFailureEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

import static com.bntech.bnauth.config.Const.ws_ERRORS_TOPIC;

@Controller
public class WebsocketController {

    @MessageMapping("/newError")
    @SendTo(ws_ERRORS_TOPIC)
    public AuthenticationFailureEvent authenticationFailureEvent(AuthenticationFailureEvent message) throws Exception {
        return new AuthenticationFailureEvent(message.getContent(), Instant.now());
    }
}
