package com.example.schedule.control;

import com.example.schedule.common.Const;
import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.signUp(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request) {
        /**
         * login 로직은 사용자가 있으면 responseDto 반환
         * 없으면 ResponseStatusException Throw
         */
        LoginResponseDto responseDto = userService.login(requestDto);

        // 로그인 성공시 로직
        // Session의 Default Value는 true이다.
        // Session이 request에 존재하면 기존의 Session을 반환하고,
        // Session이 request에 없을 경우에 새로 Session을 생성한다.
        HttpSession session = request.getSession();

        //회원 정보 조회
        UserResponseDto loginUser = userService.findUserById(responseDto.getId());

        // Session에 로그인 회원 정보를 저장
        session.setAttribute(Const.LOGIN_USER, loginUser);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        // 로그인하지 않으면 HttpSession이 null로 반환된다.
        HttpSession session = request.getSession(false);
        // 세션이 존재하면 -> 로그인이 된 경우
        if (session != null) {
            session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUser() {
        List<UserResponseDto> userResponseDtoList = userService.findAllUser();
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto,
            HttpServletRequest request) {
        /**
         * session에서 responseDto 갖고 오기
         */
        HttpSession session = request.getSession();
        UserResponseDto responseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        userService.validateUser(id, responseDto.getId());
        UserResponseDto updateResponseDto = userService.update(id, requestDto);
        return new ResponseEntity<>(updateResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            HttpServletRequest request) {
        /**
         * session에서 responseDto 갖고 오기
         */
        HttpSession session = request.getSession();
        UserResponseDto responseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        userService.validateUser(id, responseDto.getId());
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}