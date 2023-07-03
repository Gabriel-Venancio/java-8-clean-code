package org.example;

import main.exception.SubscriptionsExpiredException;

import java.time.LocalDate;
import java.util.List;

public class Payment {

    private List<Product> products;
    private LocalDate purchaseDate;
    private Customer customer;
    private Subscription subscription;

    public Payment(List<Product> products, LocalDate purchaseDate, Customer cliente, Subscription subscription) {

        if(!subscription.isActive())
            throw new SubscriptionsExpiredException("It is not possible to make a purchase without a subscription.");

        this.products = products;
        this.purchaseDate = purchaseDate;
        this.customer = cliente;
        this.subscription = subscription;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "products=" + products +
                ", purchaseDate=" + purchaseDate +
                ", customer=" + customer +
                ", subscription=" + subscription +
                '}';
    }
}