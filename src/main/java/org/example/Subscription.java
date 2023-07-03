package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class Subscription {

    private BigDecimal monthlyFee;
    private LocalDate begin;
    private Optional<LocalDate> end;
    private Customer customer;
    private SubscriptionType subscriptionType;
    private LocalDate validityDate;

    public Subscription(BigDecimal monthlyFee, LocalDate begin, Customer customer, SubscriptionType subscriptionType, LocalDate validityDate) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.empty();
        this.customer = customer;
        this.subscriptionType = subscriptionType;
        this.validityDate = validityDate;
    }

    public Subscription(BigDecimal monthlyFee, LocalDate begin, LocalDate end, Customer customer, SubscriptionType subscriptionType, LocalDate validityDate) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.of(end);
        this.customer = customer;
        this.subscriptionType = subscriptionType;
        this.validityDate = validityDate;
    }

    public void closeSubscription(LocalDate closingDate) {
        this.end = Optional.of(closingDate);
        this.validityDate = closingDate;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public Optional<LocalDate> getEnd() {
        return end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public boolean isActive() {
        return !LocalDate.now().atStartOfDay().isAfter(validityDate.atStartOfDay());
    }

    public long getSubscriptionMonths() {
        return Period.between(this.begin, this.end.orElse(LocalDate.now())).toTotalMonths();
    }
    public BigDecimal calculateAccumulatedValue() {
        long subscriptionMonths = getSubscriptionMonths();
        return monthlyFee.multiply(BigDecimal.valueOf(subscriptionMonths));
    }

    public BigDecimal calculateFee(){
        LocalDate today = LocalDate.now();
        if (this.end.orElse(today.plusDays(1)).isAfter(today)){
            return this.subscriptionType.getFee().multiply(calculateAccumulatedValue()).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "monthlyFee=" + monthlyFee +
                ", begin=" + begin +
                ", end=" + end +
                ", customer=" + customer +
                ", subscriptionType=" + subscriptionType +
                ", validityDate=" + validityDate +
                '}';
    }
}