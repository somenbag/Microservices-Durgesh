package com.demo.hotel.services.impl;

import com.demo.hotel.entities.Hotel;
import com.demo.hotel.exception.ResourceNotFoundException;
import com.demo.hotel.repository.HotelRepo;
import com.demo.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepo hotelRepo;

    @Override
    public Hotel createHotel(Hotel hotel) {
        String id = UUID.randomUUID().toString();
        hotel.setId(id);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotelList() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(
                () -> new ResourceNotFoundException("Hotel with given id is not found !!")
        );
        return hotel;
    }
}

