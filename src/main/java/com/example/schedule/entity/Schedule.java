package com.example.schedule.entity;

import com.example.schedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="schedule")
@Getter
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String task;

    @Column(nullable = false, unique = true)
    private String username;

    public Schedule() {
    }

    public Schedule(String title, String task, String username) {
        this.title = title;
        this.task = task;
        this.username = username;
    }

    public void updateSchedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.task = requestDto.getTask();
        this.username = requestDto.getUsername();
    };

}
