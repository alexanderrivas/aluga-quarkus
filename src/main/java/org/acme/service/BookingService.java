package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.acme.controler.dto.BookingDTO;
import org.acme.entity.BookingEntity;
import org.acme.entity.VehicleEntity;
import org.acme.enums.BookingStatus;
import org.acme.repository.BookingRepository;

import java.util.List;

@ApplicationScoped
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleService vehicleService;

    public BookingService(BookingRepository bookingRepository, VehicleService vehicleService) {
        this.bookingRepository = bookingRepository;
        this.vehicleService = vehicleService;
    }

    public List<BookingEntity> getAllBookings (){
        return bookingRepository.listAll();
    }

    public BookingEntity getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public BookingEntity createBooking(BookingDTO bookingDTO) {

        VehicleEntity vehicle = vehicleService.getVehicleById(bookingDTO.getVehicleId());

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found with ID: " + bookingDTO.getVehicleId());
        }

        if (bookingDTO.getStartDate().isAfter(bookingDTO.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setCustomerName(bookingDTO.getCustomerName());
        bookingEntity.setStartDate(bookingDTO.getStartDate());
        bookingEntity.setEndDate(bookingDTO.getEndDate());
        bookingEntity.setVehicle(vehicle);

        bookingRepository.persist(bookingEntity);
        return bookingEntity;
    }

    public BookingEntity setBookingStatus (Long id, String status){
        BookingEntity booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found with ID: " + id);
        }

        try {
            booking.setStatus(BookingStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        bookingRepository.persist(booking);
        return booking;

    }

}
