package dev.mikablondo.hibernate_reactive_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String firstName;
    private String email;
}
