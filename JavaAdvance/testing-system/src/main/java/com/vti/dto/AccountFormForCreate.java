package com.vti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFormForCreate {
    private String email;
    private String username;
    private String fullName;
    private Integer departmentId;
    private Integer positionId;
}
