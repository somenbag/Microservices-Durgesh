package com.demo.rating.services;

import com.demo.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAllRating();

    //get rating by  userId
    List<Rating> getRatingByUserId(String userId);

    //get rating by hotel
    List<Rating> getRatingByHotel(String hotelId);
}
