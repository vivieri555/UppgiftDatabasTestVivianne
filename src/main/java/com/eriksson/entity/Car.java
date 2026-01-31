package com.eriksson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String brand;

    @Column()
    private String model;

    @Column()
    private String gearbox;

    @Column()
    private Boolean loanable;

    protected Car() {}

    public Car(String brand, String model, String gearbox, Boolean loanable) {
        this.brand = brand;
        this.model = model;
        this.gearbox = gearbox;
        this.loanable = loanable;
    }

    public Long getId() { return id; }

    public String getBrand() { return brand; }

    public String getModel() { return model; }

    public String getGearbox() { return gearbox; }

    public Boolean isLoanable() { return loanable; }

}
