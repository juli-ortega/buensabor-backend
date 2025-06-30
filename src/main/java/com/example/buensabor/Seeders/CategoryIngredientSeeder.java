// package com.example.buensabor.Seeders;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import com.example.buensabor.entity.CategoryIngredient;
// import com.example.buensabor.entity.Company;
// import com.example.buensabor.repository.CategoryIngredientRepository;
// import com.example.buensabor.repository.CompanyRepository;

// import java.util.ArrayList;
// import java.util.List;

// @Component
// @Order(3)
// public class CategoryIngredientSeeder implements CommandLineRunner {

//     private final CategoryIngredientRepository categoryIngredientRepository;
//     private final CompanyRepository companyRepository;

//     public CategoryIngredientSeeder(CategoryIngredientRepository categoryIngredientRepository,
//                                     CompanyRepository companyRepository) {
//         this.categoryIngredientRepository = categoryIngredientRepository;
//         this.companyRepository = companyRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (categoryIngredientRepository.count() == 0) {

//             Company mostazaCompany = companyRepository.findByEmail("contacto@mostaza.com.ar").orElse(null);

//             if (mostazaCompany == null) {
//                 System.err.println("No se encontró la empresa Mostaza, crea la empresa primero.");
//                 return;
//             }

//             // Crear lista mutable de categorías
//             List<CategoryIngredient> categories = new ArrayList<>();

//             CategoryIngredient c1 = new CategoryIngredient();
//             c1.setName("Semillas");
//             c1.setCompany(mostazaCompany);
//             c1.setParent(null);
//             categories.add(c1);

//             CategoryIngredient c2 = new CategoryIngredient();
//             c2.setName("Vinagres");
//             c2.setCompany(mostazaCompany);
//             c2.setParent(null);
//             categories.add(c2);

//             CategoryIngredient c3 = new CategoryIngredient();
//             c3.setName("Especias Secas");
//             c3.setCompany(mostazaCompany);
//             c3.setParent(null);
//             categories.add(c3);

//             // Podés seguir agregando más categorías igual…

//             categoryIngredientRepository.saveAll(categories);

//             System.out.println("Seeder: Categorías de ingredientes para Mostaza creadas.");
//         }
//     }
// }
