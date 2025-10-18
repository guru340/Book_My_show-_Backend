package com.cfs.BookMyShow.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="threaters")
@Data
@NoArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private  String city;
    private String TotalScreen;

    @OneToMany(mappedBy = "theater",cascade = CascadeType.ALL)
    private List<Screen> screens;


}
