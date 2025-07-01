// package com.example.buensabor.Seeders;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import com.example.buensabor.entity.Company;
// import com.example.buensabor.entity.PromotionType;
// import com.example.buensabor.entity.enums.PromotionBehavior;
// import com.example.buensabor.repository.CompanyRepository;
// import com.example.buensabor.repository.PromotionTypeRepository;

// import java.util.ArrayList;
// import java.util.List;

// @Component
// @Order(7) // ajustá según el orden que uses
// public class PromotionTypeSeeder implements CommandLineRunner {

//     private final PromotionTypeRepository promotionTypeRepository;
//     private final CompanyRepository companyRepository;

//     public PromotionTypeSeeder(PromotionTypeRepository promotionTypeRepository,
//                                CompanyRepository companyRepository) {
//         this.promotionTypeRepository = promotionTypeRepository;
//         this.companyRepository = companyRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (promotionTypeRepository.count() == 0) {

//             Company mostazaCompany = companyRepository.findByEmail("contacto@mostaza.com.ar").orElse(null);

//             if (mostazaCompany == null) {
//                 System.err.println("No se encontró la empresa Mostaza, crea la empresa primero.");
//                 return;
//             }

//             List<PromotionType> promotionTypes = new ArrayList<>();

//             promotionTypes.add(new PromotionType("Combo Precio Fijo", PromotionBehavior.PRECIO_FIJO, mostazaCompany));
//             promotionTypes.add(new PromotionType("Descuento Porcentaje", PromotionBehavior.DESCUENTO_PORCENTAJE, mostazaCompany));
//             promotionTypes.add(new PromotionType("2x1", PromotionBehavior.X_POR_Y, mostazaCompany));

//             promotionTypeRepository.saveAll(promotionTypes);

//             System.out.println("Seeder: Tipos de promociones para Mostaza creados.");
//         }
//     }
// }
