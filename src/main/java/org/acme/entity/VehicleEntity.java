package org.acme.entity;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.acme.enums.VehicleStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vehicles")
public class VehicleEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String model;

    private String brand;

    private int vehicleYear;

    private String engine;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    public VehicleEntity() {
        this.status = VehicleStatus.AVAILABLE;
    }

    public VehicleEntity (String model, String brand, int vehicleYear, String engine) {
        this.model = model;
        this.brand = brand;
        this.vehicleYear = vehicleYear;
        this.engine = engine;
        this.status = VehicleStatus.AVAILABLE;
    }

}
