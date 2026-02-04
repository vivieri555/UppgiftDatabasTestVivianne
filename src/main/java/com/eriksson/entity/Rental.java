package com.eriksson.entity;

import com.eriksson.enums.RentalType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental")
public class Rental {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long rentalObjectId;

    @Column(name = "rentalDate", nullable = false)
    private LocalDate rentalDate;

    @Column(nullable = false)
    private LocalDate returnDate;

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

    public Rental(long rentalObjectId, LocalDate rentalDate, LocalDate returnDate, double cost, RentalType rentalType) {
    this.rentalObjectId = rentalObjectId;
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
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

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public LocalDate getReturnDate() { return returnDate;}

    public long getRentalObjectId() {
        return rentalObjectId;
    }
}
