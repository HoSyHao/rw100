package com.vti.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class ArticlePositionNameConverter implements AttributeConverter<PositionName, String> {
    @Override
    public String convertToDatabaseColumn(PositionName positionName) {
        if (positionName == null) {
            return null;
        }
        return positionName.getValue();
    }

    @Override
    public PositionName convertToEntityAttribute(String s) {
        if (Objects.isNull(s)) {
            return null;
        }
        return PositionName.toEnum(s);
    }
}