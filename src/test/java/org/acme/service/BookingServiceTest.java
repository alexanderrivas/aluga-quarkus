package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.controler.dto.BookingDTO;
import org.acme.entity.BookingEntity;
import org.acme.entity.VehicleEntity;
import org.acme.enums.VehicleStatus;
import org.acme.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class BookingServiceTest {

    @Mock
    VehicleService vehicleService;

    @InjectMocks
    BookingService bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    VehicleEntity vehicleEntity;

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
        bookingDTO.setVehicleId(1L);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(1L);
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicle.setBrand("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setVehicleYear(2020);
        vehicle.setEngine("1.8L");

        when(vehicleService.getVehicleById(anyLong())).thenReturn(vehicle);

        BookingEntity result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
        verify(bookingRepository).persist(result);
    }
}
