package com.EaseTravel.service.impl;

import com.EaseTravel.travel_management_system.model.entity.Booking;
import com.EaseTravel.travel_management_system.repository.BookingRepository;
import com.EaseTravel.travel_management_system.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Optional<Booking> existing = bookingRepository.findById(id);
        if (existing.isPresent()) {
            Booking b = existing.get();
            b.setBookingDate(booking.getBookingDate());
            b.setStatus(booking.getStatus());
            return bookingRepository.save(b);
        }
        return null;
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
