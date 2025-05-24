package com.example.buensabor.Ingredient;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.Ingredient.Interfaces.IIngredientController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/ingredients")
public class IngredientController extends BaseControllerImplementation<Ingredient, IngredientService> implements IIngredientController {
    
}