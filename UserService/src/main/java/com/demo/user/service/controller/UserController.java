package com.demo.user.service.controller;

import com.demo.user.service.entities.User;
import com.demo.user.service.sevices.UserService;
import com.demo.user.service.sevices.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    int retryCount=1;
    @GetMapping("/{UserId}")
  //@CircuitBreaker(name="ratingHotelServiceBreaker", fallbackMethod = "ratingHotelServiceFallBack")
  //@Retry(name="ratingHotelServiceBreaker", fallbackMethod = "ratingHotelServiceFallBack")
    @RateLimiter(name="userRateLimiter", fallbackMethod = "ratingHotelServiceFallBack")
    public ResponseEntity<User> getUser(@PathVariable("UserId") String id) {
        logger.info("Retry Count: {}", retryCount);
        retryCount++;
        User userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);
    }

    //creating fallback method or circuit breaker

    public ResponseEntity<User> ratingHotelServiceFallBack(String userId ,Exception exception){

        exception.printStackTrace();
        logger.info("FallBack is executed because service is down",exception.getMessage());
        User user = User.builder()
                .email("somen123@gmail.com")
                .name("somen")
                .about("this dummy user is created because some services is down")
                .userId("1234")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }
}

