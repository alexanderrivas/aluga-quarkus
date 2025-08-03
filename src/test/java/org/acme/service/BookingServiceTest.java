package org.acme.service;

import org.acme.controler.dto.BookingDTO;
import org.acme.entity.BookingEntity;
import org.acme.entity.VehicleEntity;
import org.acme.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    @Mock
    VehicleService vehicleService;

    @InjectMocks
    BookingService bookingService;

    @Mock
    BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBookingWithValidData() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(1L);
        bookingDTO.setCustomerName("John Doe");
        bookingDTO.setStartDate(LocalDate.now());
        bookingDTO.setEndDate(LocalDate.now().plusDays(5));

        VehicleEntity vehicle = new VehicleEntity();
        when(vehicleService.getVehicleById(1L)).thenReturn(vehicle);

        BookingEntity result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
        assertEquals(vehicle, result.getVehicle());
        verify(bookingRepository).persist(result);
    }
}
