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
        return userRepository.findAll()
                .onItem().transform(userEntity -> User.builder()
                        .nom(userEntity.getNom())
                        .prenom(userEntity.getPrenom())
                        .age(userEntity.getAge())
                        .metier(userEntity.getMetier())
                        .build());
    }
}
