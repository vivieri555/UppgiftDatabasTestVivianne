package com.eriksson.entity;

import com.eriksson.enums.RENTALTYPE;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "rental")
public class Rental {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rentalObjectId;

    @Column(name = "rentalDate", nullable = false)
    private LocalDate rentalDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private RENTALTYPE rentalType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_member")
    )
    private Member member;


protected Rental() {}

    public Rental(Long rentalObjectId, LocalDate rentalDate, LocalDate returnDate, BigDecimal cost, RENTALTYPE rentalType, Member member) {
    this.rentalObjectId = rentalObjectId;
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
    this.cost = cost;
    this.rentalType = rentalType;
    this.member = member;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public RENTALTYPE getRentalType() {
        return rentalType;
    }

    public BigDecimal getCost() {
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
