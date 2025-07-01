package com.example.buensabor.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.Util.SecurityUtil;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.ProductPromotionDTO;
import com.example.buensabor.entity.dto.PromotionDTO;
import com.example.buensabor.entity.dto.CreateDTOs.PromotionCreateDTO;
import com.example.buensabor.entity.enums.PromotionBehavior;
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
    private final SecurityUtil securityUtil;

    private Company getAuthenticatedCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

    public List<PromotionDTO> getAll() {
        Company company = securityUtil.getAuthenticatedCompany();
        List<Promotion> promotions = promotionRepository.findByCompany(company);
        return promotions.stream()
                .map(promotionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PromotionDTO getById(Long id) {
        Company company = securityUtil.getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada o no autorizada"));
        return promotionMapper.toDTO(promotion);
    }

    @Transactional
    public PromotionDTO createPromotion(PromotionCreateDTO dto) { 
        Company company = securityUtil.getAuthenticatedCompany();

        PromotionType promotionType = promotionTypeRepository.findById(dto.getPromotionTypeId())
            .orElseThrow(() -> new RuntimeException("Tipo de promoción no encontrada"));

        Promotion promotion = promotionMapper.toEntity(dto);
        promotion.setCompany(company);
        promotion.setDayOfWeeks(dto.getDayOfWeeks());
        promotion.setPromotionType(promotionType);

        Promotion savedPromotion = promotionRepository.save(promotion);

        if (dto.getProductIds() != null) {
            for (Long productId : dto.getProductIds()) {
                Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));

                ProductPromotion productPromotion = new ProductPromotion();
                productPromotion.setProduct(product);
                productPromotion.setPromotion(savedPromotion);

                switch (promotionType.getBehavior()) {
                    case PRECIO_FIJO:
                    case DESCUENTO_PORCENTAJE:
                        if (dto.getProductValues() == null || !dto.getProductValues().containsKey(productId)) {
                            throw new RuntimeException("Se requiere 'value' para el producto: " + productId);
                        }
                        productPromotion.setValue(dto.getProductValues().get(productId));
                        break;

                    case X_POR_Y:
                        if (dto.getProductValues() == null || !dto.getProductValues().containsKey(productId)
                            || dto.getExtraValues() == null || !dto.getExtraValues().containsKey(productId)) {
                            throw new RuntimeException("Se requieren 'value' y 'extraValue' para X_POR_Y en el producto: " + productId);
                        }
                        productPromotion.setValue(dto.getProductValues().get(productId));
                        productPromotion.setExtraValue(dto.getExtraValues().get(productId));
                        break;
                }

                productPromotionRepository.save(productPromotion);
            }
        }

        return promotionMapper.toDTO(savedPromotion);
    }

    @Transactional
    public PromotionDTO update(Long id, PromotionDTO dto) {
        Company company = securityUtil.getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada o no autorizada"));

        promotion.setTitle(dto.getTitle());
        promotion.setDateFrom(dto.getDateFrom());
        promotion.setDateTo(dto.getDateTo());
        promotion.setTimeFrom(dto.getTimeFrom());
        promotion.setTimeTo(dto.getTimeTo());
        promotion.setDiscountDescription(dto.getDiscountDescription());

        if (dto.getPromotionTypeDTO() != null) {
            var promotionType = new PromotionType();
            promotionType.setId(dto.getPromotionTypeDTO().getId());
            promotion.setPromotionType(promotionType);
        }

        Promotion updated = promotionRepository.save(promotion);
        PromotionType fullPromotionType = promotionTypeRepository.findById(updated.getPromotionType().getId())
            .orElseThrow(() -> new RuntimeException("PromotionType no encontrado"));

        updated.setPromotionType(fullPromotionType);
        // Obtener el behavior actual de la promoción
        PromotionBehavior behavior = updated.getPromotionType().getBehavior();

        List<ProductPromotion> currentProductPromotions = productPromotionRepository.findByPromotion(updated);

        Map<Long, ProductPromotionDTO> dtoProductPromos = dto.getProductPromotions() == null
            ? Collections.emptyMap()
            : dto.getProductPromotions().stream()
                .filter(pp -> pp.getProductId() != null)
                .collect(Collectors.toMap(ProductPromotionDTO::getProductId, pp -> pp));

        // Actualizar o eliminar los actuales
        for (ProductPromotion pp : currentProductPromotions) {
            Long productId = pp.getProduct().getId();
            if (dtoProductPromos.containsKey(productId)) {
                ProductPromotionDTO dtoPP = dtoProductPromos.get(productId);

                // Validación según behavior
                switch (behavior) {
                    case PRECIO_FIJO:
                    case DESCUENTO_PORCENTAJE:
                        if (dtoPP.getValue() == null) {
                            throw new RuntimeException("Se requiere 'value' para el producto: " + productId);
                        }
                        pp.setValue(dtoPP.getValue());
                        pp.setExtraValue(null);
                        break;

                    case X_POR_Y:
                        if (dtoPP.getValue() == null || dtoPP.getExtraValue() == null) {
                            throw new RuntimeException("Se requieren 'value' y 'extraValue' para X_POR_Y en el producto: " + productId);
                        }
                        pp.setValue(dtoPP.getValue());
                        pp.setExtraValue(dtoPP.getExtraValue());
                        break;
                }

                productPromotionRepository.save(pp);
                dtoProductPromos.remove(productId);
            } else {
                productPromotionRepository.delete(pp);
            }
        }

        // Los que quedan son nuevos
        for (ProductPromotionDTO newPPDTO : dtoProductPromos.values()) {
            Product product = productRepository.findById(newPPDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + newPPDTO.getProductId()));

            ProductPromotion newPP = new ProductPromotion();
            newPP.setProduct(product);
            newPP.setPromotion(updated);

            // Validación según behavior
            switch (behavior) {
                case PRECIO_FIJO:
                case DESCUENTO_PORCENTAJE:
                    if (newPPDTO.getValue() == null) {
                        throw new RuntimeException("Se requiere 'value' para el producto: " + newPPDTO.getProductId());
                    }
                    newPP.setValue(newPPDTO.getValue());
                    break;

                case X_POR_Y:
                    if (newPPDTO.getValue() == null || newPPDTO.getExtraValue() == null) {
                        throw new RuntimeException("Se requieren 'value' y 'extraValue' para X_POR_Y en el producto: " + newPPDTO.getProductId());
                    }
                    newPP.setValue(newPPDTO.getValue());
                    newPP.setExtraValue(newPPDTO.getExtraValue());
                    break;
            }

            productPromotionRepository.save(newPP);
        }

        return promotionMapper.toDTO(updated);
    }

    public boolean delete(Long id) {
        Company company = getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
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
