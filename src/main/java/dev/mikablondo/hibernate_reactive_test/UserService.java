package dev.mikablondo.hibernate_reactive_test;

import dev.mikablondo.hibernate_reactive_test.dto.User;
import io.smallrye.mutiny.Multi;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public Multi<User> getAllUsers() {
        return Multi.createFrom().items(new User("John", "Doe", "toto@toto.com"),
                new User("Jane", "Doe", "tata@toto.com"));
    }
}
