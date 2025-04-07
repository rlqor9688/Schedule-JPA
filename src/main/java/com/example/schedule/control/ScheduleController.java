package com.example.schedule.control;

import com.example.schedule.common.Const;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.service.ScheduleService;
import com.example.schedule.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto requestDto,
            HttpServletRequest request) {

        // 세션에서 로그인한 사용자 꺼내기
        HttpSession session = request.getSession();
        UserResponseDto loginUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        if (loginUser==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요.");
        }

        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto, loginUser.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto,
            HttpServletRequest request) {

        /**
         * session에서 loginUser(UserResponseDto)를 꺼내
         * schedule id로 찾은 유저(작성자)의 유저id(PathVariable로 주어짐)와 현재 loginUser의 유저 id가 다르면
         * FORBIDDEN Exception을 throw. 성공 시 update 로직 수행
         */
        HttpSession session = request.getSession();
        UserResponseDto loginUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        scheduleService.validateOwner(id ,loginUser.getId());

        ScheduleResponseDto scheduleResponseDto = scheduleService.updateSchedule(id, requestDto);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            HttpServletRequest request) {
        /**
         * session에서 loginUser(UserResponseDto)를 꺼내
         * schedule id로 찾은 유저(작성자)의 유저id(PathVariable로 주어짐)와 현재 loginUser의 유저 id가 다르면
         * FORBIDDEN Exception을 throw. 성공 시 delete 로직 수행
         */
        HttpSession session = request.getSession();
        UserResponseDto loginUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        scheduleService.validateOwner(id ,loginUser.getId());

        scheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}