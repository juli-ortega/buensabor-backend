package com.example.buensabor.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.PromotionTypeDTO;
import com.example.buensabor.entity.dto.CreateDTOs.PromotionTypeCreateDTO;
import com.example.buensabor.entity.mappers.PromotionTypeMapper;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.PromotionTypeRepository;
import com.example.buensabor.service.interfaces.IPromotionType;

@Service
public class PromotionTypeService extends BaseServiceImplementation<PromotionTypeDTO, PromotionType, Long> implements IPromotionType {

    private final PromotionTypeRepository promotionTypeRepository;
    private final PromotionTypeMapper promotionTypeMapper;
    private final CompanyRepository companyRepository;

    public PromotionTypeService(PromotionTypeRepository promotionTypeRepository, PromotionTypeMapper promotionTypeMapper, CompanyRepository companyRepository) {
        super(promotionTypeRepository, promotionTypeMapper);
        this.promotionTypeRepository = promotionTypeRepository;
        this.promotionTypeMapper = promotionTypeMapper;
        this.companyRepository = companyRepository;
    }

    private Company getAuthenticatedCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Compania no encontrada"));
    }
    
    public List<PromotionTypeDTO> getByCompanyId() {
        Company company = getAuthenticatedCompany();

        List<PromotionType> promotionType = promotionTypeRepository.findByCompanyId(company.getId());

        return promotionTypeMapper.toDTOList(promotionType);
    }

    public PromotionTypeDTO getById(Long id) {
        Company company = getAuthenticatedCompany();

        PromotionType promotionType = promotionTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de promocion no encontrada"));

        if (!promotionType.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("No estas autorizado a tipo de promociones");
        }

        return promotionTypeMapper.toDTO(promotionType);
    }

    public PromotionTypeDTO save(PromotionTypeCreateDTO dto) {
        Company company = getAuthenticatedCompany();

        PromotionType entity = promotionTypeMapper.toEntity(dto);
        entity.setCompany(company);
        PromotionType saved = promotionTypeRepository.save(entity);
        return promotionTypeMapper.toDTO(saved);
    }

    public PromotionTypeDTO update(Long id, PromotionTypeDTO dto) {
        Company company = getAuthenticatedCompany();

        PromotionType promotionType = promotionTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de promocion no encontrada"));

        if (!promotionType.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("No estas autorizado a modificar este tipo de promocion");
        }

        promotionType.setName(dto.getName());
        PromotionType updated = promotionTypeRepository.save(promotionType);
        return promotionTypeMapper.toDTO(updated);
    }

    public boolean delete(Long id) {
        Company company = getAuthenticatedCompany();

        PromotionType promotionType = promotionTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de promocion no encontrada"));

        if (!promotionType.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("No estas autorizado a modificar este tipo de promocion");
        }

        promotionTypeRepository.delete(promotionType);
        return true;
    }
}