package com.demo.hotel.controller;

import com.demo.hotel.entities.Hotel;
import com.demo.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
    }
    @PreAuthorize("hasAuthority('SCOPE_internal')|| hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<Hotel>> getALlHotel(){
        return ResponseEntity.ok(hotelService.getAllHotelList());
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')|| hasAuthority('admin')")
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getById(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(hotelId));
    }
}