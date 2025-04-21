package cl.sentra.controller;

import cl.sentra.dto.UserRequest;
import cl.sentra.entity.User;
import cl.sentra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the User API");
    }
     

    @RequestMapping("/create")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        System.out.println("Received request: " + request);
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/modify")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modifyUser(@RequestBody UserRequest request) {
        System.out.println("Received request to modify user: " + request);
        User user = userService.modifyUser(request);
        return ResponseEntity.ok(user);
    }
}
