package dev.mikablondo.hibernate_reactive_test.controller;

import dev.mikablondo.hibernate_reactive_test.UserService;
import dev.mikablondo.hibernate_reactive_test.dto.User;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Multi<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
