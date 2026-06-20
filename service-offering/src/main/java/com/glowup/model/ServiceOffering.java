package com.glowup.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String duration;

    private Long salonId;

    @Column(nullable = false)
    private Long categoryId;

    private String image;



}
