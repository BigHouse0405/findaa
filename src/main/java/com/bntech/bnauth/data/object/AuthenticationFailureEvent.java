package com.bntech.bnauth.data.object;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class AuthenticationFailureEvent {

	private String content;

	@Nullable
	private Instant timestamp;

	public AuthenticationFailureEvent() {
	}
}
