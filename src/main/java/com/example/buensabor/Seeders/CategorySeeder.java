// package com.example.buensabor.Seeders;


// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import com.example.buensabor.entity.Category;
// import com.example.buensabor.entity.Company;
// import com.example.buensabor.repository.CategoryRepository;
// import com.example.buensabor.repository.CompanyRepository;

// import java.util.ArrayList;
// import java.util.List;

// @Component
// @Order(4)
// public class CategorySeeder implements CommandLineRunner {

//     private final CategoryRepository categoryRepository;
//     private final CompanyRepository companyRepository;

//     public CategorySeeder(CategoryRepository categoryRepository,
//                           CompanyRepository companyRepository) {
//         this.categoryRepository = categoryRepository;
//         this.companyRepository = companyRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (categoryRepository.count() == 0) {

//             Company mostazaCompany = companyRepository.findByEmail("contacto@mostaza.com.ar").orElse(null);

//             if (mostazaCompany == null) {
//                 System.err.println("No se encontró la empresa Mostaza, crea la empresa primero.");
//                 return;
//             }

//             List<Category> categories = new ArrayList<>();

//             categories.add(createCategory("Hamburguesas", mostazaCompany, null));
//             categories.add(createCategory("Papas Fritas", mostazaCompany, null));
//             categories.add(createCategory("Bebidas", mostazaCompany, null));
//             categories.add(createCategory("Helados", mostazaCompany, null));
//             categories.add(createCategory("Postres", mostazaCompany, null));
//             categories.add(createCategory("Combos", mostazaCompany, null));
//             categories.add(createCategory("Aderezos", mostazaCompany, null));
//             categories.add(createCategory("Ensaladas", mostazaCompany, null));

//             categoryRepository.saveAll(categories);

//             System.out.println("Seeder: Categorías de productos para Mostaza creadas.");
//         }
//     }

//     private Category createCategory(String name, Company company, Category parent) {
//         Category category = new Category();
//         category.setName(name);
//         category.setCompany(company);
//         category.setParent(parent);
//         return category;
//     }
// }
