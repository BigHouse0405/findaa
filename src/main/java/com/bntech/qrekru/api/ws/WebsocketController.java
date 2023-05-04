package com.bntech.qrekru.api.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;

@Controller
public class WebsocketController {

	@MessageMapping("/newError")
	@SendTo("/topic/errors")
	public AuthenticationFailureEvent greeting(AuthenticationFailureEvent message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new AuthenticationFailureEvent(message.getContent(), Instant.now());
	}
}
