package com.cu.sci.lambdaserver.utils.enums;

public enum GradeBounds {

    UPPER_BOUND_FAIL(59),
    LOWER_BOUND_POOR(60),
    UPPER_BOUND_POOR(64),
    UPPER_BOUND_GOOD(74),
    LOWER_BOUND_GOOD(65),
    UPPER_BOUND_VERY_GOOD(84),
    LOWER_BOUND_VERY_GOOD(75),
    UPPER_BOUND_EXCELLENT(100);

    private final int value;

    GradeBounds(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
