package org.acme.controler;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.controler.dto.BookingDTO;
import org.acme.entity.BookingEntity;
import org.acme.entity.VehicleEntity;
import org.acme.repository.BookingRepository;
import org.acme.service.BookingService;
import org.acme.service.VehicleService;

import java.util.List;
import java.util.Optional;

@Path("/booking")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingControler {

    private final BookingService bookingService;


    public BookingControler(VehicleService vehicleService , BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GET
    public Response getAllBookings() {
        return Response.ok(bookingService.getAllBookings()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookingById(@PathParam("id") Long id) {
        BookingEntity booking = bookingService.getBookingById(id);
        if (booking == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Booking not found with ID: " + id)
                    .build();
        }

        return Response.ok(booking).build();
    }

    @POST
    @Transactional
    public Response createBooking(@Valid BookingDTO booking) {
        BookingEntity createdBooking;

        try {
            createdBooking = bookingService.createBooking(booking);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
        return Response.ok(createdBooking).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateStatusBooking(@PathParam("id") Long id, BookingDTO bookingDTO) {
        BookingEntity updatedBooking = bookingService.setBookingStatus(id, String.valueOf(bookingDTO.getStatus()));
        return Response.ok(updatedBooking).build();
    }

}
