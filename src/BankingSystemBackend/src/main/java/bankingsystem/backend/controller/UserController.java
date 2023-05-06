package bankingsystem.backend.controller;

import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.Response;
import bankingsystem.backend.entity.User;
import bankingsystem.backend.exception.BadRequestException;
import bankingsystem.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody User user) {
        return ResponseEntity.ok(new Response(Constants.SUCCESS, userService.createUser(user)));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserFromToken(HttpServletResponse response, HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserFromToken(request.getHeader("token")));
    }

    @PutMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(new Response(Constants.SUCCESS, userService.updateUser(user)));
    }
}
