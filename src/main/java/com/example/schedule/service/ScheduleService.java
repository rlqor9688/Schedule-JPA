package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        User findUser = userRepository.findByIdOrElseThrow(requestDto.getUserId());

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getTask());
        schedule.setUser(findUser);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        schedule.updateSchedule(requestDto);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(schedule);
    }
}
