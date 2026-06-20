package com.glowup.mapper;

import com.glowup.Model.Salon;
import com.glowup.Payload.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setName(salon.getName());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setImages(salon.getImages());
        salonDTO.setOpeningTime(salon.getOpeningTime());
        salonDTO.setClosingTime(salon.getClosingTime());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
        return salonDTO;







    }
}
