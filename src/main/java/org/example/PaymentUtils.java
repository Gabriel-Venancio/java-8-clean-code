package org.example;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class PaymentUtils {
    public List<Payment> sortPaymentsByDate(List<Payment> payments) {
        return payments.stream()
                .sorted(Comparator.comparing(Payment::getPurchaseDate))
                .collect(Collectors.toList());
    }

    public double calculateTotalPayment(Payment payment) {
        return payment.getProducts().stream()
                .mapToDouble(p -> p.getPrice().doubleValue())
                .sum();
    }

    public BigDecimal calculateTotalPayments(List<Payment> payments) {
        return payments.stream()
                .map(Payment::getProducts)
                .flatMap(Collection::stream)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Long> getQuantityPerProduct(List<Payment> payments) {
        return payments.stream()
                .map(Payment::getProducts)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Product::getName, Collectors.counting()));
    }

    public Map<Customer, List<Product>> getProductsPerCustomer(List<Payment> payments) {
        return payments.stream()
                .collect(Collectors.groupingBy(p -> p.getSubscription().getCustomer(), Collectors.mapping(Payment::getProducts, Collectors.toList())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .flatMap(List::stream)
                                .collect(Collectors.toList())
                ));
    }

    public Optional<Customer> extractCustomerWithHighestSpending(List<Payment> payments) {
        Map<Customer, List<Product>> productsPerCustomer = getProductsPerCustomer(payments);
        return productsPerCustomer.entrySet().stream()
                .sorted(Comparator.comparing(entry ->
                        entry.getValue().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::subtract))
                )
                .findFirst()
                .map(Map.Entry::getKey);
    }

    public Map<Month, BigDecimal> calculateValuesByMonth(List<Payment> payments) {
        return payments.stream()
                .collect(Collectors.groupingBy(p -> p.getPurchaseDate().getMonth()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(Payment::getProducts)
                                .flatMap(List::stream)
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));
    }
}
