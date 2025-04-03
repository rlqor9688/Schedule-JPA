package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String title;

    private final String task;

    private final String username;

    public ScheduleRequestDto(String title, String task, String username) {
        this.title = title;
        this.task = task;
        this.username = username;
    }

}
