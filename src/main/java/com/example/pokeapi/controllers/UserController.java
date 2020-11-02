package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.User;
import com.example.pokeapi.services.UserServices.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false) String username){
        var users = userService.findAll(username);
        //return new ResponseEntity<>(users, HttpStatus.OK); //200 -
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        var answer = userService.saveUser(user);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@Validated @PathVariable String id, @RequestBody User user){
        userService.updateUser(id, user);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);

    }



}
