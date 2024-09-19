package com.wora.models.enums;

public enum ProjectStatus {
    IN_PROGRESS, COMPLETED, CANCELLED;

    public static ProjectStatus fromNumber(int number) {
        return switch (number) {
            case 1 -> ProjectStatus.IN_PROGRESS;
            case 2 -> ProjectStatus.COMPLETED;
            case 3 -> ProjectStatus.CANCELLED;
            default -> ProjectStatus.IN_PROGRESS;
        };
    }
}
