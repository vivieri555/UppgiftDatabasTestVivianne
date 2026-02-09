package com.eriksson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String brand;

    @Column(nullable = false, length = 40)
    private String model;

    @Column(length = 30)
    private String gearbox;

    protected Car() {}

    public Car(String brand, String model, String gearbox) {
        this.brand = brand;
        this.model = model;
        this.gearbox = gearbox;
    }

    public Long getId() { return id; }

    public String getBrand() { return brand; }

    public String getModel() { return model; }

    public String getGearbox() { return gearbox; }
}
