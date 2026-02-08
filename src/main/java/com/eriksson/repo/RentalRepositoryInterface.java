package com.eriksson.repo;

import com.eriksson.entity.Rental;

import java.util.List;

public interface RentalRepositoryInterface {
    void save(Rental rental);
    List<String> getAllRentals();
    Rental getRentalById(Long id);
    void terminateRental(Long id);
}


