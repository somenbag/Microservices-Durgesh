package com.demo.user.service.sevices.impl;

import com.demo.user.service.entities.Hotel;
import com.demo.user.service.entities.Rating;
import com.demo.user.service.entities.User;
import com.demo.user.service.exception.ResourceNotFoundException;
import com.demo.user.service.external.services.HotelService;
import com.demo.user.service.repositories.UserRepository;
import com.demo.user.service.sevices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {
        String randomUUID  = UUID.randomUUID().toString();
        user.setUserId(randomUUID);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with given id is not found ")
        );
        //http://localhost:8083/ratings/users/9d4ed53a-304e-46c5-a916-bfa5bf8224a4

        Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingOfUser);

        List<Rating> ratings = Arrays.asList(ratingOfUser);

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel.
            //set the hotel to rating.
            //return the rating.
     //http://localhost:8082/hotels/cb8f55ec-56d1-48cc-adfe-157587e63c68
            // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
             Hotel hotel = hotelService.getHotel(rating.getHotelId());
            // Hotel hotel = forEntity.getBody();
             rating.setHotel(hotel);
             return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }
}
