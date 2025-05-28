package com.example.buensabor.entity.dto;

import java.sql.Date;

import com.example.buensabor.entity.enums.GeneroEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO extends UserDTO {
    private String lastname;
    private Date born_date;
    private GeneroEnum genero;
}