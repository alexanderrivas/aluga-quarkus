package org.acme;

import jakarta.inject.Inject;
import org.acme.controler.dto.BookingDTO;
import org.acme.entity.BookingEntity;
import org.acme.entity.VehicleEntity;
import org.acme.repository.BookingRepository;
import org.acme.service.BookingService;
import org.acme.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    @Inject
    VehicleService vehicleService;

    @Inject
    BookingService bookingService;

    @Inject
    BookingRepository bookingRepository;

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
