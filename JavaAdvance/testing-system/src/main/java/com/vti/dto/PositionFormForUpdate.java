package com.vti.dto;

import com.vti.enums.PositionName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionFormForUpdate {
    private PositionName name;
}
