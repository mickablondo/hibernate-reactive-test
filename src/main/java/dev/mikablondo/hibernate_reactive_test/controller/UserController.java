package dev.mikablondo.hibernate_reactive_test.controller;

import dev.mikablondo.hibernate_reactive_test.dto.User;
import dev.mikablondo.hibernate_reactive_test.dto.UserFilter;
import dev.mikablondo.hibernate_reactive_test.services.UserService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public Multi<User> getUsers(UserFilter filtre) {
        return userService.getUsers(filtre);
    }

    @PostMapping
    public Uni<ResponseEntity<Void>> createUser(@RequestBody User user) {
        return userService.createUser(user)
                .map(v -> ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @DeleteMapping("/{id}")
    public Uni<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return userService.deleteUser(uuid)
                    .map(deleted -> deleted
                            ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                            : ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (IllegalArgumentException e) {
            return Uni.createFrom().item(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
    }

    @GetMapping("/{id}")
    public Uni<ResponseEntity<User>> getUserById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return userService.getUserById(uuid)
                    .map(user -> user != null
                            ? ResponseEntity.ok(user)
                            : ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (IllegalArgumentException e) {
            return Uni.createFrom().item(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
    }
}
