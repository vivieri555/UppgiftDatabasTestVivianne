package com.eriksson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "caravan")

//Husvagn
public class Caravan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String model;

    @Column()
    private Boolean loanable;

    protected Caravan() {}

    public Caravan(String model, Boolean loanable) {
        this.model = model;
        this.loanable = loanable;
    }
    public Long getId() { return id; }

    public String getModel() { return model; }

    public Boolean getLoanable() { return loanable; }
}
