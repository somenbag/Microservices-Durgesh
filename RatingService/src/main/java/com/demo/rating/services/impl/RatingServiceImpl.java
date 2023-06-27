package com.demo.rating.services.impl;

import com.demo.rating.entities.Rating;
import com.demo.rating.repository.RatingRepo;
import com.demo.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepo repo;

    @Override
    public Rating createRating(Rating rating) {
        String randomUUID  = UUID.randomUUID().toString();
        rating.setRatingId(randomUUID);
        return repo.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        return repo.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotel(String hotelId) {
        return repo.findByHotelId(hotelId);
    }
}

