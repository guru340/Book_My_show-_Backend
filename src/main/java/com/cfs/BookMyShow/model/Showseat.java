package com.cfs.BookMyShow.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="show_seats")
@Data
@NoArgsConstructor
public class Showseat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    @Column(nullable = false)
    private Double price;

}
