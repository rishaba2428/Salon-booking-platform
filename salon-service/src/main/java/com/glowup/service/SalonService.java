package com.glowup.service;

import com.glowup.Model.Salon;
import com.glowup.Payload.dto.SalonDTO;
import com.glowup.Payload.dto.UserDTO;

import java.util.List;

public interface SalonService {
        Salon createSalon(SalonDTO salon, UserDTO user);

        Salon updateSalon(Long salonId, SalonDTO salon, UserDTO user) throws Exception;

        List<Salon> getAllSalons();



         Salon getSalonById(Long salonId) throws Exception;

        Salon getSalonByOwnerId(Long ownerId);

        List<Salon> searchSalonByCity(String city);



}
