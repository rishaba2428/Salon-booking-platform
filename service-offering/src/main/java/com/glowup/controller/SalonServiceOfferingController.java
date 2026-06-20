package com.glowup.controller;

import com.glowup.dto.CategoryDTO;
import com.glowup.dto.SalonDTO;
import com.glowup.dto.ServiceDTO;
import com.glowup.model.ServiceOffering;
import com.glowup.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDTO serviceDTO)
    {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        ServiceOffering serviceOfferings = serviceOfferingService .createService(salonDTO,serviceDTO,categoryDTO);
        return ResponseEntity.ok(serviceOfferings);

    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable long id,@RequestBody ServiceOffering serviceOffering) throws Exception {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        ServiceOffering serviceOfferings = serviceOfferingService .updateService(id,serviceOffering);
        return ResponseEntity.ok(serviceOfferings);

    }

}
