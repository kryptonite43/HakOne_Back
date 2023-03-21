package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.UserService;
import com.example.hakone.HakOne.dto.DeleteUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<DeleteUserResDto> deleteUser(@PathVariable Long user_id) {
        return ResponseEntity.ok(userService.deleteUser(user_id));
    }
}
