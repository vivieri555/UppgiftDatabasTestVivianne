package com.eriksson.entity;

import com.eriksson.enums.RentalType;
import jakarta.persistence.*;

@Entity
@Table(name = "rental")
public class Rental {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long rentalObjectId;

    @Column(name = "rentalDays", nullable = false)
    private int rentalDays;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(nullable = false)
    private RentalType rentalType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_member")
    )
    private Member member;

protected Rental() {}

    public Rental(long rentalObjectId, int rentalDays, double cost, RentalType rentalType) {
    this.rentalObjectId = rentalObjectId;
    this.rentalDays = rentalDays;
    this.cost = cost;
    this.rentalType = rentalType;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public double getCost() {
        return cost;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public long getRentalObjectId() {
        return rentalObjectId;
    }
}
