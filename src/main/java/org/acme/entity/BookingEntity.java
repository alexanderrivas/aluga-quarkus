package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.acme.enums.BookingStatus;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    private String customerName;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public BookingEntity (){
        this.status = BookingStatus.CREATED;
    }

    public BookingEntity(VehicleEntity vehicle, String customerName, LocalDate startDate, LocalDate endDate) {
        this.vehicle = vehicle;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.CREATED;
    }

}
