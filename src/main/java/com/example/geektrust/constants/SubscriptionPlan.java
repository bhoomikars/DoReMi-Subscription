package com.example.geektrust.constants;

public enum SubscriptionPlan {
    FREE(1), PERSONAL(1), PREMIUM(3);

    private final int label;

    SubscriptionPlan(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
}
