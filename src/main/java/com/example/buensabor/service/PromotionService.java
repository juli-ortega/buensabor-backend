package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.PromotionDTO;
import com.example.buensabor.entity.dto.CreateDTOs.PromotionCreateDTO;
import com.example.buensabor.entity.mappers.PromotionMapper;
import com.example.buensabor.repository.CompanyRepository;
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

    private Company getAuthenticatedCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public List<PromotionDTO> getAll() {
        Company company = getAuthenticatedCompany();
        List<Promotion> promotions = promotionRepository.findByCompany(company);
        return promotions.stream().map(promotionMapper::toDTO).collect(Collectors.toList());
    }

    public PromotionDTO getById(Long id) {
        Company company = getAuthenticatedCompany();
        Promotion promotion = promotionRepository.findByIdAndCompany(id, company)
                .orElseThrow(() -> new RuntimeException("Promotion not found or not authorized"));
        return promotionMapper.toDTO(promotion);
    }

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
        Promotion saved = promotionRepository.save(promotion);
        return promotionMapper.toDTO(saved);
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
        promotion.setPromotionalPrice(dto.getPromotionalPrice());
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
}
