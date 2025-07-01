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
public class ClientInfoDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Date born_date;
    private GeneroEnum genero;
    private Boolean isActive;
}
