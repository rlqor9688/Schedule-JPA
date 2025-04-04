package com.example.schedule.repository;


import com.example.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);


    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    Optional<User> findUserByUsername(String username);

    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + username));
    }

    Optional<User> findUserByEmailAndPassword(String email, String password);

    default User findUserByEmailAndPasswordOrElseThrow(String email, String password) {
            return findUserByEmailAndPassword(email, password).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));
    }
}
