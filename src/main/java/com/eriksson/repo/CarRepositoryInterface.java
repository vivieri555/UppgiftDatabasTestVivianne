package com.eriksson.repo;

import com.eriksson.entity.Car;

import java.util.Optional;

public interface CarRepositoryInterface {
    void save(Car car);
    Optional<Car> findById(Long id);
}
