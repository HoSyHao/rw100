package com.vti.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFormForUpdate {
    @NotBlank(message = "Email must not be blank")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$", message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Username must not be blank")
    @Length(min = 6, max = 50, message = "Username must be between 6 and 50 characters")
    private String username;

    @NotBlank(message = "Full name must not be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must only contain letters and spaces")
    @Length(max = 50, message = "Full name must have a maximum of 50 characters")
    private String fullName;

    private Integer departmentId;
    private Integer positionId;
}
