package com.bntech.qrekru.data.object;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull
    @Size(max = 255)
    private String login;
    @NotNull
    @Size(max = 255)
    private String password;
}
