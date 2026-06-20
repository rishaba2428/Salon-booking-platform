package com.glowup.service;

import com.glowup.dto.SalonDTO;
import com.glowup.model.Category;

import java.util.Set;


public interface CategoryService {

    Category saveCategory (Category category, SalonDTO salonDTO);
    Set<Category> getAllcategoriesBySalon(Long id);
    Category getCategoryById(Long id) throws Exception;
    void deleteCategoryById(Long id,Long salonId) throws Exception;
}
