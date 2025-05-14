package dev.mikablondo.hibernate_reactive_test;

import dev.mikablondo.hibernate_reactive_test.dto.User;
import dev.mikablondo.hibernate_reactive_test.repository.UserRepository;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Multi<User> getAllUsers() {
        return Multi.createFrom().items(new User("John", "Doe", "toto@toto.com"),
                new User("Jane", "Doe", "tata@toto.com"));
    }
}
