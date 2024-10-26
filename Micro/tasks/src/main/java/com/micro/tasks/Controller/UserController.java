package com.micro.tasks.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.micro.tasks.Config.AutoIncrementUtil;
import com.micro.tasks.Entities.User;
import com.micro.tasks.Repositories.UserRepository;
import com.micro.tasks.Services.IProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private IProjectService iProjectService;
    @Autowired
    private AutoIncrementUtil autoIncrementUtil;
    @Autowired
    UserRepository userRepository;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = iProjectService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = iProjectService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create user
    @PostMapping("/users")
    public User addChambre(@RequestBody User user) {
        int id = autoIncrementUtil.getNextSequence("votre_sequence");
        user.setId(id);
        return iProjectService.createUser(user);
    }

    // Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateTask(@PathVariable("id") int id, @RequestBody User updatedUser) {
        User userExist = userRepository.findById(id).orElse(null);
        if (userExist != null) {
            updatedUser.setId(id);
            User savedUser = userRepository.save(updatedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        iProjectService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
