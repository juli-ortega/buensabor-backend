package com.example.buensabor.Seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.buensabor.entity.*;
import com.example.buensabor.entity.enums.PromotionBehavior;
import com.example.buensabor.repository.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
@Order(7)
public class PromotionSeeder implements CommandLineRunner {

    private final PromotionRepository promotionRepository;
    private final CompanyRepository companyRepository;
    private final PromotionTypeRepository promotionTypeRepository;
    private final ProductRepository productRepository;

    public PromotionSeeder(PromotionRepository promotionRepository,
                           CompanyRepository companyRepository,
                           PromotionTypeRepository promotionTypeRepository,
                           ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.companyRepository = companyRepository;
        this.promotionTypeRepository = promotionTypeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (promotionRepository.count() == 0) {

            Company mostazaCompany = companyRepository.findByEmail("contacto@mostaza.com.ar").orElse(null);

            if (mostazaCompany == null) {
                System.err.println("No se encontró la empresa Mostaza.");
                return;
            }

            PromotionType precioFijoType = promotionTypeRepository.findByNameAndCompany("Combo Precio Fijo", mostazaCompany).orElse(null);

            if (precioFijoType == null) {
                System.err.println("No se encontró PromotionType 'Combo Precio Fijo' para Mostaza.");
                return;
            }

            // Buscar productos
            Product burgerClasica = productRepository.findByTitleAndCompany("Hamburguesa Clásica", mostazaCompany).orElse(null);
            Product papasMedianas = productRepository.findByTitleAndCompany("Papas Fritas Medianas", mostazaCompany).orElse(null);

            if (burgerClasica == null || papasMedianas == null) {
                System.err.println("Faltan productos para asociar a la promoción.");
                return;
            }

            // Crear promoción
            Promotion promoCombo = new Promotion();
            promoCombo.setCompany(mostazaCompany);
            promoCombo.setTitle("Combo Clásico");
            promoCombo.setDateFrom(LocalDate.now());
            promoCombo.setDateTo(LocalDate.now().plusMonths(3));
            promoCombo.setTimeFrom(LocalTime.of(0, 0));
            promoCombo.setTimeTo(LocalTime.of(23, 59));
            promoCombo.setDayOfWeeks(EnumSet.allOf(DayOfWeek.class));  // todos los días
            promoCombo.setDiscountDescription("Combo Hamburguesa Clásica + Papas a precio promocional.");
            promoCombo.setPromotionType(precioFijoType);

            // Asignar productos a la promoción
            List<ProductPromotion> productPromotions = new ArrayList<>();

            productPromotions.add(new ProductPromotion(burgerClasica, promoCombo, 3000.0, null)); // precio fijo para burger
            productPromotions.add(new ProductPromotion(papasMedianas, promoCombo, 1200.0, null)); // precio fijo para papas

            promoCombo.setProductPromotions(productPromotions);

            // Guardar promoción
            promotionRepository.save(promoCombo);

            System.out.println("Seeder: Promoción Combo Clásico para Mostaza creada.");
        }
    }
}
