package com.example.buensabor.Seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.enums.UnitMeasure;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.repository.CategoryIngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(5)
public class IngredientSeeder implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final CompanyRepository companyRepository;
    private final CategoryIngredientRepository categoryIngredientRepository;

    public IngredientSeeder(IngredientRepository ingredientRepository,
                            CompanyRepository companyRepository,
                            CategoryIngredientRepository categoryIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.companyRepository = companyRepository;
        this.categoryIngredientRepository = categoryIngredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (ingredientRepository.count() == 0) {

            // Buscar empresa Mostaza
            Company mostazaCompany = companyRepository.findByEmail("contacto@mostaza.com.ar").orElse(null);

            if (mostazaCompany == null) {
                System.err.println("No se encontró la empresa Mostaza, crea la empresa primero.");
                return;
            }

            // Buscar una categoría de ingredientes por defecto
            Optional<CategoryIngredient> defaultCategoryOpt = categoryIngredientRepository.findAll().stream().findFirst();

            if (defaultCategoryOpt.isEmpty()) {
                System.err.println("No se encontró categoría de ingredientes. Crea al menos una.");
                return;
            }

            CategoryIngredient defaultCategory = defaultCategoryOpt.get();

            // Lista de ingredientes
            List<Ingredient> ingredients = new ArrayList<>();

            ingredients.add(createIngredient(mostazaCompany, "Pan de hamburguesa", 400, false, UnitMeasure.UNIT, true, 50, 200, 500, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Carne vacuna molida", 1500, true, UnitMeasure.KILOGRAM, true, 10, 60, 90, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Queso cheddar", 800, false, UnitMeasure.KILOGRAM, true, 5, 50, 100, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Tomate", 300, false, UnitMeasure.KILOGRAM, true, 10, 50, 100, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Lechuga", 250, false, UnitMeasure.KILOGRAM, true, 10, 30, 70, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Papas fritas", 600, true, UnitMeasure.KILOGRAM, true, 20, 80, 150, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Mostaza", 350, false, UnitMeasure.LITER, true, 5, 40, 80, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Ketchup", 300, false, UnitMeasure.LITER, true, 5, 40, 80, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Mayonesa", 320, false, UnitMeasure.LITER, true, 5, 40, 80, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Aceite de girasol", 500, false, UnitMeasure.LITER, true, 10, 50, 100, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Sal fina", 120, false, UnitMeasure.KILOGRAM, true, 10, 30, 60, defaultCategory));
            ingredients.add(createIngredient(mostazaCompany, "Pimienta negra", 200, false, UnitMeasure.GRAM, true, 5, 10, 20, defaultCategory));

            // Guardar ingredientes
            ingredientRepository.saveAll(ingredients);

            System.out.println("Seeder: Ingredientes para Mostaza creados.");
        }
    }

    private Ingredient createIngredient(Company company, String name, double price, boolean isToPrepare,
                                        UnitMeasure unitMeasure, boolean status, double minStock,
                                        double currentStock, double maxStock, CategoryIngredient category) {
        Ingredient ingredient = new Ingredient();
        ingredient.setCompany(company);
        ingredient.setName(name);
        ingredient.setPrice(price);
        ingredient.setToPrepare(isToPrepare);
        ingredient.setUnitMeasure(unitMeasure);
        ingredient.setStatus(status);
        ingredient.setMinStock(minStock);
        ingredient.setCurrentStock(currentStock);
        ingredient.setMaxStock(maxStock);
        ingredient.setCategoryIngredient(category);
        return ingredient;
    }
}
