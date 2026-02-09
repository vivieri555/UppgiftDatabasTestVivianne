package com.eriksson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bike")

public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

@Column(length = 20)
    private String gears;

protected Bike() {}

    public Bike(String model, String gears) {
    this.model = model;
    this.gears = gears;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getGears() {
        return gears;
    }
}
