package com.example.schedule.dto;

import com.example.schedule.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long id;
    private final String username;

    public LoginResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public LoginResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

}
