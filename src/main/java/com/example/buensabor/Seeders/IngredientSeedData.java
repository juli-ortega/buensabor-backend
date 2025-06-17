package com.example.buensabor.Seeders;

import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.enums.UnitMeasure;
import com.example.buensabor.repository.CategoryIngredientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class IngredientSeedData implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final CompanyRepository companyRepository;
    private final CategoryIngredientRepository categoryIngredientRepository;

    public IngredientSeedData(
        IngredientRepository ingredientRepository,
        CompanyRepository companyRepository,
        CategoryIngredientRepository categoryIngredientRepository
    ) {
        this.ingredientRepository = ingredientRepository;
        this.companyRepository = companyRepository;
        this.categoryIngredientRepository = categoryIngredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (ingredientRepository.findAll().size() > 0) {
            return; // No ejecutar si ya hay datos
        }

        // Leer el archivo JSON
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream jsonFile = getClass().getResourceAsStream("/data/ingredients.json");
        if (jsonFile == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo ingredients.json");
        }
        /* Trouble shooting haciendo un sout de todos los ingredientes antes de que se metan a la lista */
        // Leer y mostrar el contenido del JSON para troubleshooting
        try {
            byte[] jsonData = jsonFile.readAllBytes();
            String jsonContent = new String(jsonData, StandardCharsets.UTF_8);
            System.out.println("Contenido del archivo JSON:");
            System.out.println(jsonContent);
            jsonFile = new ByteArrayInputStream(jsonData);
        } catch (Exception e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lanzar la excepción para detener la ejecución
        }


        
        List<IngredientDTO> ingredientsJson = objectMapper.readValue(jsonFile, new TypeReference<List<IngredientDTO>>() {});
        
        
        // Procesar cada ingrediente del JSON
        for (IngredientDTO ingredientDTO : ingredientsJson) {
            // Verificar que la Company existe
            Company company = companyRepository.findById(ingredientDTO.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

            // Crear el Ingredient y mapear los datos
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientDTO.getName());
            ingredient.setPrice(ingredientDTO.getPrice());
            ingredient.setToPrepare(ingredientDTO.isToPrepare());
            ingredient.setUnitMeasure(ingredientDTO.getUnitMeasure());
            ingredient.setStatus(ingredientDTO.isStatus());
            ingredient.setMinStock(ingredientDTO.getMinStock());
            ingredient.setCurrentStock(ingredientDTO.getCurrentStock());
            ingredient.setMaxStock(ingredientDTO.getMaxStock());
            ingredient.setCompany(company);

            ingredientRepository.save(ingredient);
        }
    }
}
