package com.example.buensabor.Seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.buensabor.entity.*;
import com.example.buensabor.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@org.springframework.core.annotation.Order(6)
public class ProductSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    public ProductSeeder(ProductRepository productRepository,
                         CompanyRepository companyRepository,
                         CategoryRepository categoryRepository,
                         IngredientRepository ingredientRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {

            Company mostazaCompany = companyRepository.findByEmail("contacto@mostaza.com.ar").orElse(null);

            if (mostazaCompany == null) {
                System.err.println("No se encontró la empresa Mostaza.");
                return;
            }

            // Buscar categorías
            Category hamburguesas = categoryRepository.findByNameAndCompany("Hamburguesas", mostazaCompany).orElse(null);
            Category papas = categoryRepository.findByNameAndCompany("Papas Fritas", mostazaCompany).orElse(null);
            Category bebidas = categoryRepository.findByNameAndCompany("Bebidas", mostazaCompany).orElse(null);

            if (hamburguesas == null || papas == null || bebidas == null) {
                System.err.println("Faltan categorías para los productos.");
                return;
            }

            // Buscar ingredientes
            Ingredient carne = ingredientRepository.findByNameAndCompany("Carne vacuna molida", mostazaCompany).orElse(null);
            Ingredient pan = ingredientRepository.findByNameAndCompany("Pan de hamburguesa", mostazaCompany).orElse(null);
            Ingredient cheddar = ingredientRepository.findByNameAndCompany("Queso cheddar", mostazaCompany).orElse(null);
            Ingredient papasFritas = ingredientRepository.findByNameAndCompany("Papas fritas", mostazaCompany).orElse(null);
            Ingredient ketchup = ingredientRepository.findByNameAndCompany("Ketchup", mostazaCompany).orElse(null);
            Ingredient mostaza = ingredientRepository.findByNameAndCompany("Mostaza", mostazaCompany).orElse(null);

            if (carne == null || pan == null || cheddar == null || papasFritas == null || ketchup == null || mostaza == null) {
                System.err.println("Faltan ingredientes para armar los productos.");
                return;
            }

            // Lista de productos
            List<Product> products = new ArrayList<>();

            // Hamburguesa Clásica
            Product burgerClasica = new Product();
            burgerClasica.setCompany(mostazaCompany);
            burgerClasica.setCategory(hamburguesas);
            burgerClasica.setTitle("Hamburguesa Clásica");
            burgerClasica.setDescription("Clásica hamburguesa con cheddar, ketchup y mostaza.");
            burgerClasica.setEstimatedTime(15);
            burgerClasica.setPrice(3500);
            burgerClasica.setImage("hamburguesa_clasica.jpg");
            burgerClasica.setProfit_percentage(30);

            // Ingredientes de la hamburguesa
            List<ProductIngredient> burgerIngredients = new ArrayList<>();
            burgerIngredients.add(new ProductIngredient(burgerClasica, carne, 0.15));
            burgerIngredients.add(new ProductIngredient(burgerClasica, pan, 1));
            burgerIngredients.add(new ProductIngredient(burgerClasica, cheddar, 0.03));
            burgerIngredients.add(new ProductIngredient(burgerClasica, ketchup, 0.02));
            burgerIngredients.add(new ProductIngredient(burgerClasica, mostaza, 0.02));

            burgerClasica.setProductIngredients(burgerIngredients);
            products.add(burgerClasica);

            // Papas Fritas Medianas
            Product papasMedianas = new Product();
            papasMedianas.setCompany(mostazaCompany);
            papasMedianas.setCategory(papas);
            papasMedianas.setTitle("Papas Fritas Medianas");
            papasMedianas.setDescription("Papas fritas crocantes tamaño mediano.");
            papasMedianas.setEstimatedTime(8);
            papasMedianas.setPrice(1500);
            papasMedianas.setImage("papas_medianas.jpg");
            papasMedianas.setProfit_percentage(40);

            List<ProductIngredient> papasIngredients = new ArrayList<>();
            papasIngredients.add(new ProductIngredient(papasMedianas, papasFritas, 0.2));

            papasMedianas.setProductIngredients(papasIngredients);
            products.add(papasMedianas);

            // Gaseosa 500ml
            Product gaseosa = new Product();
            gaseosa.setCompany(mostazaCompany);
            gaseosa.setCategory(bebidas);
            gaseosa.setTitle("Gaseosa 500ml");
            gaseosa.setDescription("Botella de gaseosa fría 500ml.");
            gaseosa.setEstimatedTime(2);
            gaseosa.setPrice(1200);
            gaseosa.setImage("gaseosa_500ml.jpg");
            gaseosa.setProfit_percentage(50);
            gaseosa.setProductIngredients(new ArrayList<>());  // sin insumos

            products.add(gaseosa);

            productRepository.saveAll(products);

            System.out.println("Seeder: Productos de Mostaza cargados.");
        }
    }
}
