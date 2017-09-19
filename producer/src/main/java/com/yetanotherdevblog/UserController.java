package com.yetanotherdevblog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public void saveUser() {
        User newUser = new User();
        newUser.setUsername(UUID.randomUUID().toString());
        userRepository.save(newUser);
    }

}
