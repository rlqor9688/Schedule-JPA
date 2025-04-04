package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String title;

    private final String task;

    private final Long userId;

    public ScheduleRequestDto(String title, String task, Long userId) {
        this.title = title;
        this.task = task;
        this.userId = userId;
    }

}
