package org.acme.controler.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.acme.entity.BookingEntity;
import org.acme.entity.VehicleEntity;
import org.acme.enums.BookingStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    private Long id;
    private Long vehicleId;
    private VehicleEntity vehicle;

    @NotNull
    private String customerName;

    @NotNull
    @FutureOrPresent(message = "Start date must be in the future or present")
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDate endDate;

    private BookingStatus status;

    public BookingDTO toBookingDTO (BookingEntity bookingEntity){
        return BookingDTO.builder()
                .id(bookingEntity.getId())
                .vehicleId(bookingEntity.getVehicle().getId())
                .vehicle(bookingEntity.getVehicle())
                .customerName(bookingEntity.getCustomerName())
                .startDate(bookingEntity.getStartDate())
                .endDate(bookingEntity.getEndDate())
                .status(bookingEntity.getStatus())
                .build();
    }
}
