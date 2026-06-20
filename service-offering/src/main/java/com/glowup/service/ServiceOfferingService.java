package com.glowup.service;

import com.glowup.dto.CategoryDTO;
import com.glowup.dto.SalonDTO;
import com.glowup.dto.ServiceDTO;
import com.glowup.model.ServiceOffering;

import java.util.Set;


public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId,ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId,Long categoryId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

     ServiceOffering getServicesById(Long id) throws Exception;
}
