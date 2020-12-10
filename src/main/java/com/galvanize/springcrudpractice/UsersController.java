package com.galvanize.springcrudpractice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return (List<User>) this.repository.findAll();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return this.repository.save(user);
    }
}
