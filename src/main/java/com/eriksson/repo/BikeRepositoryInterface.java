package com.eriksson.repo;

import com.eriksson.entity.Bike;

import java.util.Optional;

public interface BikeRepositoryInterface {
    void save(Bike bike);
        Optional<Bike> findById (Long id);
}

