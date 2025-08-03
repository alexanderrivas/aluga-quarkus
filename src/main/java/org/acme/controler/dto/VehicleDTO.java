package org.acme.controler.dto;

import io.quarkus.arc.All;
import lombok.*;
import org.acme.entity.VehicleEntity;
import org.acme.enums.VehicleStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {

    private long id;
    private String model;
    private String brand;
    private int vehicleYear;
    private String engine;
    private String status;
    private String carTitle;

    public VehicleDTO toVehicleResponseDTO(VehicleEntity vehicle) {
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .vehicleYear(vehicle.getVehicleYear())
                .engine(vehicle.getEngine())
                .status(vehicle.getStatus().name())
                .carTitle(vehicle.getBrand() + " " + vehicle.getModel() + " " + vehicle.getVehicleYear())
                .build();
    }

    public VehicleEntity toVehicleEntity() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(this.id);
        vehicle.setModel(this.model);
        vehicle.setBrand(this.brand);
        vehicle.setVehicleYear(this.vehicleYear);
        vehicle.setEngine(this.engine);
        vehicle.setStatus(VehicleStatus.valueOf(this.status));
        return vehicle;
    }
}
