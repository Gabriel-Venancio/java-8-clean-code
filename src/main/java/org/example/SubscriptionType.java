package org.example;

import java.math.BigDecimal;

public enum SubscriptionType {
    ANNUAL("Annual", BigDecimal.ZERO),
    SEMI_ANNUAL("Semi-Annual", BigDecimal.valueOf(0.03)),
    QUARTERLY("Quarterly", BigDecimal.valueOf(0.05));

    private String type;
    private BigDecimal fee;

    SubscriptionType(String type, BigDecimal fee){
        this.type = type;
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
