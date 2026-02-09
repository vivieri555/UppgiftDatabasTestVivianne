package com.eriksson.repo;

import com.eriksson.entity.Rental;
import com.eriksson.enums.RENTALTYPE;

import java.util.List;

public interface RentalRepositoryInterface {
    void save(Rental rental);
    List<Rental> getAllRentals();
    Rental getRentalById(Long id);
    void terminateRental(Long id);
    Boolean isVehicleRented(RENTALTYPE rentalType, long rentalObjectId);
}


