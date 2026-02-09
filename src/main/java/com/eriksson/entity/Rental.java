package com.eriksson.entity;

import com.eriksson.enums.RENTALTYPE;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
public class Rental {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rentalObjectId;

    @Column(nullable = false)
    private LocalDate rentalDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(nullable = false)
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

    public Rental(Long rentalObjectId, LocalDate rentalDate, LocalDate returnDate,
                  BigDecimal cost, RENTALTYPE rentalType, Member member) {
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

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", rentalObjectId=" + rentalObjectId +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", cost=" + cost +
                ", rentalType=" + rentalType +
                ", member=" + member +
                '}';
    }
}
