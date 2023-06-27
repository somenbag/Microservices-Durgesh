package com.demo.hotel.services;

import com.demo.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel createHotel(Hotel hotel);

    List<Hotel> getAllHotelList();

    Hotel getHotelById(String hotelId);
}
