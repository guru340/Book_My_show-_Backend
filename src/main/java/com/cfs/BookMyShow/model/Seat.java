package com.cfs.BookMyShow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatnumber;

    @Column(nullable = false)
    private String seatType;

    @Column(nullable = false)
    private Double baseprice;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;


}
