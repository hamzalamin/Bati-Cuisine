package com.wora.models.enums;

public enum ComponentType {
    WORKER, MATERIAL;
    public static ComponentType fromNumber(int number) {
        return switch (number) {
            case 1 -> ComponentType.WORKER;
            case 2 -> ComponentType.MATERIAL;
            default -> ComponentType.WORKER;
        };
    }
}
