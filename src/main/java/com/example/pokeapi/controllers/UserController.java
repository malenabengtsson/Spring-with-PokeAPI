package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.PokemonEntities.GameIndice;
import com.example.pokeapi.entities.User;
import com.example.pokeapi.services.UserServices.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get a list of users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Correct user role and info is being displayed",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameIndice.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden. Wrong role, info wont be displayed", content = @Content)})
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false) String username) {
        var users = userService.findAll(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @GetMapping("/username/{username}")
    @Secured("ROLE_ADMIN")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@Validated @PathVariable String id, @RequestBody User user) {
        userService.updateUser(id, user);

    }


    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);

    }


}
