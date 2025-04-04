package com.example.schedule.service;

import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto save(UserRequestDto requestDto) {
        User user = new User(requestDto);
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();
    }

    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto requestDto) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        findUser.update(requestDto);
        return new UserResponseDto(findUser);
    }

    public void deleteUser(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(findUser);
    }


}
