package com.vti.dto;

import com.vti.enums.PositionName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionFormForCreate {
    @NotNull(message = "Position name must not be null")
    private PositionName name;
}
