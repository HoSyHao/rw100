package com.vti.enums;

public enum PositionName {
    DEV("D"),TEST("T"),SCRUM_MASTER("SM"),PM("P");

    private String value;

    PositionName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // tìm enum dựa trên gtri đưa vào
    public static PositionName toEnum(String value) {
        PositionName[] arrs = PositionName.values();
        for (PositionName positionName: arrs) {
            if (value.equals(positionName.getValue())) {
                return positionName;
            }
        }
        return null;
    }
}