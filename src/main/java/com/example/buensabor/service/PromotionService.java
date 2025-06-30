package com.example.buensabor.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.PromotionDTO;
import com.example.buensabor.entity.dto.CreateDTOs.PromotionCreateDTO;
import com.example.buensabor.entity.mappers.PromotionMapper;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.ProductPromotionRepository;
import com.example.buensabor.repository.ProductRepository;
import com.example.buensabor.repository.PromotionRepository;
import com.example.buensabor.repository.PromotionTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromotionService extends BaseServiceImplementation<PromotionDTO, Promotion, Long> {

    private final PromotionRepository promotionRepository;
    private final CompanyRepository companyRepository;
    private final PromotionMapper promotionMapper;
    private final PromotionTypeRepository promotionTypeRepository;
    private final ProductRepository productRepository;
    private final ProductPromotionRepository productPromotionRepository;

    private Company getAuthenticatedCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public List<PromotionDTO> getAll() {
        Company company = getAuthenticatedCompany();
        List<Promotion> promotions = promotionRepository.findByCompany(company);
        return promotions.stream()
                .map(promotionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PromotionDTO getById(Long id) {
        Company company = getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promotion not found or not authorized"));
        return promotionMapper.toDTO(promotion);
    }

    @Transactional
    public PromotionDTO createPromotion(PromotionCreateDTO dto) {
        Company company = getAuthenticatedCompany();

        PromotionType promotionType = null;

        if (dto.getPromotionTypeId() != null) {
            promotionType = promotionTypeRepository.findById(dto.getPromotionTypeId())
                .orElseThrow(() -> new RuntimeException("PromotionType not found"));
        }

        Promotion promotion = promotionMapper.toEntity(dto);
        promotion.setCompany(company);
        promotion.setDayOfWeeks(dto.getDayOfWeeks());
        promotion.setPromotionType(promotionType);
        Promotion savedPromotion = promotionRepository.save(promotion);

        // Asignar productos a la promoción
        if (dto.getProductIds() != null) {
            for (Long productId : dto.getProductIds()) {
                Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                ProductPromotion productPromotion = new ProductPromotion();
                productPromotion.setProduct(product);
                productPromotion.setPromotion(savedPromotion);

                // Si hay precios promocionales por producto
                if (dto.getProductValues() != null && dto.getProductValues().containsKey(productId)) {
                    productPromotion.setValue(dto.getProductValues().get(productId));
                }

                // Si hay valores extra (para X_FOR_Y, por ejemplo)
                if (dto.getExtraValues() != null && dto.getExtraValues().containsKey(productId)) {
                    productPromotion.setExtraValue(dto.getExtraValues().get(productId));
                }

                productPromotionRepository.save(productPromotion);
            }
        }

        return promotionMapper.toDTO(savedPromotion);
    }


    public PromotionDTO update(Long id, PromotionDTO dto) {
        Company company = getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promotion not found or not authorized"));

        promotion.setTitle(dto.getTitle());
        promotion.setDateFrom(dto.getDateFrom());
        promotion.setDateTo(dto.getDateTo());
        promotion.setTimeFrom(dto.getTimeFrom());
        promotion.setTimeTo(dto.getTimeTo());
        promotion.setDiscountDescription(dto.getDiscountDescription());
        // set PromotionType if dto has it
        if(dto.getPromotionTypeDTO() != null) {
            var promotionType = new com.example.buensabor.entity.PromotionType();
            promotionType.setId(dto.getPromotionTypeDTO().getId());
            promotion.setPromotionType(promotionType);
        }

        Promotion updated = promotionRepository.save(promotion);
        return promotionMapper.toDTO(updated);
    }

    public boolean delete(Long id) {
        Company company = getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promotion not found or not authorized"));
        promotionRepository.delete(promotion);
        return true;
    }

    public Optional<Promotion> getApplicablePromotion(Product product, Company company) {
        return promotionRepository.findApplicablePromotionsForProduct(
                product.getId(),
                company.getId(),
                LocalDate.now(),
                LocalDate.now().getDayOfWeek(),
                LocalTime.now()
        ).stream().findFirst();
    }

}
