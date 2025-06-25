package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.entity.dto.CreateDTOs.EmployeeCreateDTO;
import com.example.buensabor.entity.dto.ResponseDTOs.EmployeeResponseDTO;
import com.example.buensabor.entity.dto.UpdateDTOs.EmployeeUpdateDTO;

@Mapper(componentModel = "spring", uses = {AddressMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends BaseMapper<Employee, EmployeeDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "role", target = "role")
    EmployeeDTO toDTO(Employee entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "role", target = "role")
    Employee toEntity(EmployeeDTO dto);

    @Mapping(source = "addressBasicDTO.cityId", target = "address.city.id")
    Employee toEntity(EmployeeCreateDTO dto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "born_date", target = "born_date")
    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "roleEmployee", target = "roleEmployee")
    @Mapping(source = "address", target = "addressBasicDTO")
    EmployeeResponseDTO toResponseDTO(Employee employee);

    @Mapping(target = "password", ignore = true)
    void updateEmployeeFromDTO(EmployeeUpdateDTO dto, @MappingTarget Employee entity);
    
    
}