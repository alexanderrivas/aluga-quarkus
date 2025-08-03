package org.acme.controler;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.controler.dto.VehicleDTO;
import org.acme.entity.VehicleEntity;
import org.acme.service.VehicleService;

@Path("/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleControler {

    private final VehicleService vehicleService;

    public VehicleControler(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GET
    public Response getAllVehicles() {
        return Response.ok(vehicleService.getAllVehicles().stream()
                .map(vehicle -> new VehicleDTO().toVehicleResponseDTO(vehicle)))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getVehicleById(@PathParam("id") Long id) {
        VehicleEntity vehicle = vehicleService.getVehicleById(id) ;

        if (vehicle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        VehicleDTO vehicleDTO = new VehicleDTO();
        return Response.ok(vehicleDTO.toVehicleResponseDTO(vehicle)).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateVehicle(@PathParam("id") Long id, VehicleDTO vehicleDTO) {
        VehicleEntity vehicle = vehicleDTO.toVehicleEntity();
        VehicleEntity updatedVehicle = vehicleService.updateVehicle(id, vehicle);

        if (updatedVehicle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new VehicleDTO().toVehicleResponseDTO(updatedVehicle)).build();
    }

    @PATCH
    @Path("/{id}/status")
    @Transactional
    public Response changeVehicleStatus(@PathParam("id") Long id, VehicleDTO vehicleDTO) {

        if (vehicleDTO.getStatus() == null || vehicleDTO.getStatus().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Status must be provided")
                    .build();
        }

        VehicleEntity vehicle = vehicleService.changeVehicleStatus(id, vehicleDTO.getStatus());

        if (vehicle == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok(new VehicleDTO().toVehicleResponseDTO(vehicle)).build();
    }

    @POST
    @Transactional
    public Response createVehicle(VehicleEntity vehicle) {
        return Response
                .status(Response.Status.CREATED)
                .entity(vehicleService.createVehicle(vehicle))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteVehicle(@PathParam("id") Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
