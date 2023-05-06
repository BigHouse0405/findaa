package com.bntech.bnauth.data.object;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@JsonAutoDetect
@Data
@AllArgsConstructor
public class UserDto {
    @Nullable
    private Long id;
    @Nullable
    private String pwd;
    @Nullable
    private String fullName;
    @Nullable
    private Instant createdAt;
    @Nullable
    private Instant updatedAt;
    @Nullable
    private List<String> roles;
}
