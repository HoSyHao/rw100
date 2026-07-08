package com.vti.dto;

import com.vti.enums.PositionName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionFormForCreate {
    private PositionName name;
}
