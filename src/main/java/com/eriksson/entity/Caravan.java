package com.eriksson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "caravan")

//Husvagn
public class Caravan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String model;

    @Column()
    private Boolean loanable;

    @Column(length = 30)
    private String axles;

    protected Caravan() {}

    public Caravan(String model, Boolean loanable, String axles) {
        this.model = model;
        this.loanable = loanable;
        this.axles = axles;
    }
    public Long getId() { return id; }

    public String getModel() { return model; }

    public Boolean getLoanable() { return loanable; }

    public String getAxles() { return axles;}
}
