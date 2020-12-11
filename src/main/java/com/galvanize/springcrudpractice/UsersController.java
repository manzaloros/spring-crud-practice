package com.galvanize.springcrudpractice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("/authenticate")
    public HashMap authenticateUser(@RequestBody User user)
            throws JsonProcessingException {
        User registeredUser =
                this.repository.findRegisteredUserByEmail(user.getEmail());
        HashMap response = new HashMap<>();

        if (Objects.isNull(registeredUser)
                || !user.getPassword().equals(registeredUser.getPassword())) {
            response.put("authenticated", false);
        } else {
            response.put("authenticated", true);
            response.put("User", registeredUser);
        }

        return response;
    }

    @PatchMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        return this.repository.save(user);
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> deleteUser(@PathVariable Long id) {
        this.repository.deleteById(id);
        return Collections.singletonMap("count", this.repository.count());
    }
}
