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

    @Column (columnDefinition = "longtext")
    private String task;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Schedule() {
    }

    public Schedule(String title, String task) {
        this.title = title;
        this.task = task;
    }

    public void updateSchedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.task = requestDto.getTask();
    }

    public void setUser(User user) {
        this.user = user;
    }

}
