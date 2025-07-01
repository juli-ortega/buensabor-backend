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

    // Mapea EmployeeCreateDTO a Employee (address y company se setean manualmente en el servicio)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", ignore = true)
    Employee toEntity(EmployeeCreateDTO dto);

    // Mapea Employee a EmployeeResponseDTO, address -> addressBasicDTO
    @Mapping(source = "address", target = "addressBasicDTO")
    EmployeeResponseDTO toResponseDTO(Employee employee);

    // Actualiza los campos editables de Employee desde EmployeeUpdateDTO, ignora password
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "address", ignore = true) // La address se actualiza manualmente en el servicio
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEmployeeFromDTO(EmployeeUpdateDTO dto, @MappingTarget Employee entity);

}