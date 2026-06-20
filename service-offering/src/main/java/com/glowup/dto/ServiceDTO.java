package com.glowup.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ServiceDTO {

    private Long id;


    private String name;


    private String description;


    private String price;


    private String duration;

    private Long salonId;


    private Long category;

    private String image;



}
