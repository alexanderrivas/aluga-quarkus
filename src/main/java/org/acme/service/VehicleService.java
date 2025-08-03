package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.VehicleEntity;
import org.acme.enums.VehicleStatus;

import java.util.List;

@ApplicationScoped
public class VehicleService {

    public VehicleEntity getVehicleById(Long id) {
        return VehicleEntity.findById(id);
    }

    public VehicleEntity createVehicle(VehicleEntity vehicle) {
        VehicleEntity.persist(vehicle);
        return vehicle;
    }

    public List<VehicleEntity> getAllVehicles() {
        return VehicleEntity.listAll();
    }

    public VehicleEntity updateVehicle(Long id, VehicleEntity vehicle) {
        VehicleEntity existingVehicle = VehicleEntity.findById(id);
        if (existingVehicle != null) {
            existingVehicle.setModel(vehicle.getModel() != null ? vehicle.getModel() : existingVehicle.getModel());
            existingVehicle.setBrand(vehicle.getBrand() != null ? vehicle.getBrand() : existingVehicle.getBrand());
            existingVehicle.setVehicleYear(vehicle.getVehicleYear() != 0 ? vehicle.getVehicleYear() : existingVehicle.getVehicleYear());
            existingVehicle.setEngine(vehicle.getEngine() != null ? vehicle.getEngine() : existingVehicle.getEngine());
            return existingVehicle;
        }
        return null;
    }

    public boolean canTransitionToStatus(VehicleEntity vehicle, String newStatus) {
        if (vehicle == null || newStatus == null) {
            return false;
        }

        return switch (newStatus.toUpperCase()) {
            case "RENTED" ->
                    vehicle.getStatus() == VehicleStatus.AVAILABLE;
            case "AVAILABLE" -> vehicle.getStatus() == VehicleStatus.RENTED ||
                    vehicle.getStatus() == VehicleStatus.UNDER_MAINTENANCE ;
            case "UNDER_MAINTENANCE" -> vehicle.getStatus() == VehicleStatus.AVAILABLE ||
                    vehicle.getStatus() == VehicleStatus.RENTED;
            default -> false;
        };
    }

    public VehicleEntity changeVehicleStatus(Long id, String newStatus) {
        VehicleEntity vehicle = VehicleEntity.findById(id);
        if (vehicle != null && canTransitionToStatus(vehicle, newStatus)) {
            vehicle.setStatus(VehicleStatus.valueOf(newStatus.toUpperCase()));
            return vehicle;
        }
        return null;
    }

    public void deleteVehicle(Long id) {
        VehicleEntity vehicle = VehicleEntity.findById(id);

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle with id " + id + " does not exist.");
        }

        if (vehicle.getStatus() != VehicleStatus.RENTED) {
            vehicle.delete();
        } else {
            throw new IllegalStateException("Vehicle cannot be deleted because it is currently rented.");
        }
    }

}
