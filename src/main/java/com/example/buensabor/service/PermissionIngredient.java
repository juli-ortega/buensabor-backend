package com.example.buensabor.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.entity.Ingredient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionIngredient {

    private final IngredientRepository ingredientRepository;

    // Método que verifica si el usuario autenticado puede acceder/modificar/eliminar el recurso con id
    public boolean canAccessIngredient(Authentication authentication, Long resourceId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ingredientRepository.findById(resourceId)
            .map(ingredient -> ingredient.getCompany().getId().equals(userDetails.getId()))
            .orElse(false);
    }
}
