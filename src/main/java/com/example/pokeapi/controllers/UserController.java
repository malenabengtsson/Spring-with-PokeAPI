package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.PokemonEntities.GameIndice;
import com.example.pokeapi.entities.User;
import com.example.pokeapi.services.UserServices.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden. Wrong role, info wont be displayed", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> findAllUsers(@Parameter(description = "Username to search by") @RequestParam(required = false) String username) {
        var users = userService.findAll(username);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get a single user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Info is being displayed",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden. Wrong role, info wont be displayed", content = @Content),
            @ApiResponse(responseCode = "404", description = "No user found with chosen id", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> findById(@Parameter(description = "Id to search by") @PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Get a user by username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Info is being displayed",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden. Wrong role, info wont be displayed", content = @Content),
            @ApiResponse(responseCode = "404", description = "No user found with chosen username", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/username/{username}")
    @Secured("ROLE_ADMIN")
    public User findByUsername(@Parameter(description = "Username to search by") @PathVariable String username) {
        return userService.findByUsername(username);
    }

    @Operation(summary = "Save a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User has been added",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden. Wrong role, info wont be displayed", content = @Content),
            @ApiResponse(responseCode = "418", description = "Password is empty/missing", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> saveUser(@Validated @Parameter(description = "New user to add") @RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Update has gone through",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "You can only edit your own information", content = @Content),
            @ApiResponse(responseCode = "418", description = "Password is empty/missing", content = @Content),
            @ApiResponse(responseCode = "400", description = "Required requestbody is missing", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@Validated @Parameter(description = "Id to update") @PathVariable String id, @Parameter(description = "User object to update") @RequestBody User user) {
        userService.updateUser(id, user);

    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Deleted User",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "No user found with chosen id", content = @Content),
            @ApiResponse(responseCode = "418", description = "Password is empty/missing", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Parameter(description = "Id from user thats being deleted") @PathVariable String id) {
        userService.deleteUser(id);

    }


}
