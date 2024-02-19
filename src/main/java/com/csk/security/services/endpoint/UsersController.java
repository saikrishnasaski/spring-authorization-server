package com.csk.security.services.endpoint;

import com.csk.security.services.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        var userRegistered = userService.registerUser(user);

        if (userRegistered) {

            return new ResponseEntity<>("User Registration Successful", HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public String getUser() {
        return "Hey Spring Security!!";
    }
}
