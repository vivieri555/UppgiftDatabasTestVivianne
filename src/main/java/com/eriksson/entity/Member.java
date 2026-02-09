package com.eriksson.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 15)
    private String status;

    @Column()
    private String history;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    protected Member() {}

    public Member (String firstName, String lastName, String email, String status, String history) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.history = history;
    }
    public Long getId() {
        return id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
public void setLastName(String lastName) {
        this.lastName = lastName;
}
public String getLastName() {
        return lastName;
}
public void setEmail(String email) {
        this.email = email;
}
public String getEmail() {
        return email;
}
public void setStatus(String status) {
        this.status = status;
}
public String getStatus() {
        return status;
}
public void setHistory(String history) {
        this.history = history;
}
public String getHistory() { return history; }

    @Override
    public String toString() {
        return "Member{" +
                "id:" + id +
                "\nfirstName:'" + firstName + '\'' +
                "\nlastName:'" + lastName + '\'' +
                "\nemail:'" + email + '\'' +
                "\nstatus:'" + status + '\'' +
                "\nhistory:'" + history + '\'' +
                '}';
    }
}
