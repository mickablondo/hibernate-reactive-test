package dev.mikablondo.hibernate_reactive_test.controller;

import dev.mikablondo.hibernate_reactive_test.services.UserService;
import dev.mikablondo.hibernate_reactive_test.dto.User;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a REST controller for managing User entities.
 * It provides an endpoint to retrieve all users from the database.
 * The controller is annotated with @RestController to indicate that it is a Spring MVC controller.
 */
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
