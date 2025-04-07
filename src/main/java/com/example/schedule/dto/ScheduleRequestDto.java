package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String title;

    private final String task;

    public ScheduleRequestDto(String title, String task) {
        this.title = title;
        this.task = task;
    }

}
