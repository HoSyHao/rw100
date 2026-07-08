package com.vti.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentFormForUpdate {
    @NotBlank(message = "Department name must not be blank")
    @Length(max = 100, message = "Department name must have a maximum of 100 characters")
    private String name;
}
