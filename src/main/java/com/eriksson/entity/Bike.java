package com.eriksson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bike")

public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "loanable")
    private boolean loanable;

@Column()
    private String gears;

protected Bike() {}

    public Bike(String model, boolean loanable, String gears) {
    this.model = model;
    this.loanable = loanable;
    this.gears = gears;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public boolean isLoanable() {
        return loanable;
    }

    public String getGears() {
        return gears;
    }
}
