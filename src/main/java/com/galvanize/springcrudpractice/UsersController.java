package com.galvanize.springcrudpractice;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return this.repository.findById(id);
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return this.repository.save(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        return this.repository.save(user);
    }
}
