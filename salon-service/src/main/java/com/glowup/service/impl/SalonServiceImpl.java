package com.glowup.service.impl;

import com.glowup.Model.Salon;
import com.glowup.Payload.dto.SalonDTO;
import com.glowup.Payload.dto.UserDTO;
import com.glowup.repository.SalonRepository;
import com.glowup.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpeningTime(req.getOpeningTime());
        salon.setClosingTime(req.getClosingTime());
        salon.setPhoneNumber(req.getPhoneNumber());

        return salonRepository.save(salon);

    }

    @Override
    public Salon updateSalon(Long salonId, SalonDTO salon, UserDTO user) throws Exception {

        Salon existingSalon = salonRepository.findById(salonId).orElse(null);

        if(existingSalon == null)
        {
            throw new Exception("salon not exist");
        }

        if(!existingSalon.getOwnerId().equals( user.getId()))
        {
            throw new Exception("You are not authorized to update this salon");
        }



        existingSalon.setCity(salon.getCity());
        existingSalon.setName(salon.getName());
        existingSalon.setAddress(salon.getAddress());
        existingSalon.setEmail(salon.getEmail());
        existingSalon.setImages(salon.getImages());
        existingSalon.setOpeningTime(salon.getOpeningTime());
        existingSalon.setClosingTime(salon.getClosingTime());
        existingSalon.setOwnerId(user.getId());
        existingSalon.setPhoneNumber(salon.getPhoneNumber());

        return salonRepository.save(existingSalon);
        }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if(salon == null)
        {
            throw new Exception("salon not exist");
        }
        return salon;
    }


    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
