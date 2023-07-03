package main;

import org.example.*;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        LOG.info("########### Exercise 1 ###########");

        Product musicaStargazing = new Product("Música", Paths.get("musicas/travis-scoot/stargazing.mp3"), BigDecimal.valueOf(1.99));
        Product musicaAstroThunder = new Product("Música", Paths.get("musicas/travis-scoot/astrothunder.mp3"), BigDecimal.valueOf(1.99));
        Product videoShowTravisScoot = new Product("Vídeo", Paths.get("videos/travis-scoot/show-travis-scoot.mp4"), BigDecimal.valueOf(3.99));
        Product imagemCapaAlbumAstroworld = new Product("Imagem", Paths.get("imagens/travis-scoot/astroworld.png"), BigDecimal.valueOf(0.99));

        Customer paulo = new Customer("Paulo");
        Customer ygor = new Customer("Ygor");
        Customer gabriel = new Customer("Gabriel");
        Customer laiza = new Customer("Laiza");

        Subscription subscriptionPaulo = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2023, Month.JANUARY, 1), paulo, SubscriptionType.QUARTERLY, LocalDate.now().plusMonths(3));
        Subscription subscriptionPauloClose = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2021, Month.OCTOBER, 1), paulo, SubscriptionType.ANNUAL, LocalDate.now().plusMonths(1));
        subscriptionPauloClose.closeSubscription(LocalDate.now().minusMonths(2));
        Subscription subscriptionYgor = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2022, Month.DECEMBER, 1), ygor, SubscriptionType.SEMI_ANNUAL, LocalDate.now().plusMonths(2));
        Subscription subscriptionGabriel = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2022, Month.OCTOBER, 1), gabriel, SubscriptionType.ANNUAL, LocalDate.now().plusMonths(1));

        List<Subscription> subscriptions = Arrays.asList(subscriptionPaulo, subscriptionPauloClose, subscriptionYgor, subscriptionGabriel);
        subscriptions.forEach(a -> {
            SubscriptionType type = a.getSubscriptionType();
            int fee = a.getSubscriptionType().getFee().multiply(BigDecimal.valueOf(100)).intValue();
            String message = String.format("Type: %s Fee: %d%% %n",
                    type, fee);
            LOG.info(message);
            LOG.info(String.valueOf(a.calculateFee()));
        });

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate lastMonth = today.minusMonths(1);

        List<Payment> payments = new ArrayList<>();

        try {
            Payment paymentYgor = new Payment(Arrays.asList(musicaStargazing, musicaAstroThunder), yesterday, ygor, subscriptionYgor);
            Payment paymentGabriel = new Payment(Arrays.asList(imagemCapaAlbumAstroworld, videoShowTravisScoot), today, gabriel, subscriptionGabriel);
            Payment paymentPaulo = new Payment(Arrays.asList(musicaStargazing, videoShowTravisScoot, musicaAstroThunder), lastMonth, paulo, subscriptionPaulo);
            payments = Arrays.asList(paymentPaulo, paymentYgor, paymentGabriel);
            new Payment(Arrays.asList(musicaStargazing, videoShowTravisScoot, musicaAstroThunder), today, paulo, subscriptionPauloClose);

        } catch (RuntimeException e) {
            LOG.error("expired subscription");
            LOG.error(e.getMessage());
        }

        payments.forEach(c -> LOG.info(c.toString()));

        PaymentUtils paymentUtils = new PaymentUtils();

        LOG.info("########### Exercise 2 ###########");

        paymentUtils.sortPaymentsByDate(payments).forEach(payment ->  LOG.info(payment.toString()));

        LOG.info("########### Exercise 3 ###########");
        Payment paymentLaiza = new Payment(Arrays.asList(imagemCapaAlbumAstroworld, videoShowTravisScoot), today, laiza, subscriptionGabriel);

        Double totalPayment = paymentUtils.calculateTotalPayment(paymentLaiza);
        LOG.info("Total Payment -> {}", totalPayment);

        LOG.info("########### Exercise 4 ###########");
        BigDecimal totalPayments = paymentUtils.calculateTotalPayments(payments);
        LOG.info("Total Payments -> {}", totalPayments);

        LOG.info("########### Exercise 5 ###########");
        paymentUtils.getQuantityPerProduct(payments)
                .forEach((x,y) ->
                        LOG.info(String.format("Product: \"%s\"  Quantity: %d%n",x,y))
                );

        LOG.info("########### Exercise 6 ###########");
        Map<Customer, List<Product>> productsPerCustomer = paymentUtils.getProductsPerCustomer(payments);
        productsPerCustomer.forEach((customer, products) -> {
            String productNames = products.stream()
                    .map(Product::getName)
                    .collect(Collectors.joining(", "));
            LOG.info("Customer {} -> products: {}", customer.getName(), productNames);
        });

        LOG.info("########### Exercise 7 ###########");
        Optional<Customer> customerWithHighestSpending = paymentUtils.extractCustomerWithHighestSpending(payments);
        customerWithHighestSpending.ifPresent(c -> LOG.info("Customer With Highest Spending -> {}", c.getName()));

        LOG.info("########### Exercise 8 ###########");
        Map<Month, BigDecimal> monthsSales = paymentUtils.calculateValuesByMonth(payments);
        monthsSales.forEach((months,sales)-> LOG.info(String.format(" %s sales -> %s%n", months, sales)));

        LOG.info("########### Exercise 9 ###########");
      Subscription subscriptionPaulo2 = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2023, Month.JANUARY, 1), paulo, SubscriptionType.ANNUAL, LocalDate.now().plusMonths(1));
      Subscription subscriptionYgor2 = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2022, Month.DECEMBER, 1),
              LocalDate.of(2023, Month.MARCH, 1), ygor, SubscriptionType.ANNUAL, LocalDate.now().minusMonths(1));
      Subscription subscriptionGabriel2 = new Subscription(BigDecimal.valueOf(99.98), LocalDate.of(2021, Month.NOVEMBER, 1),
               LocalDate.of(2022, Month.APRIL, 1), gabriel, SubscriptionType.ANNUAL, LocalDate.now().minusMonths(1));

        List<Subscription> subscriptionExercise9;
        subscriptionExercise9 = Arrays.asList(subscriptionPaulo2, subscriptionYgor2, subscriptionGabriel2);
        subscriptionExercise9.forEach(c -> LOG.info(c.toString()));

        LOG.info("########### Exercise 10 ###########");
        long activeSubscription = ChronoUnit.MONTHS.between(subscriptionPaulo2.getBegin(), subscriptionPaulo2.getEnd().orElse(today));
        LOG.info("Months {} ->", activeSubscription);


        LOG.info("########### Exercise 11 ###########");
        subscriptions.forEach(a ->
            LOG.info(String.format("Subscription -> %s. Start at %s. End at %s.  %d months", a.getCustomer().getName(), a.getBegin(), a.getEnd(), a.getSubscriptionMonths()))
        );

        LOG.info("########### Exercise 12 ###########");
        subscriptions.forEach(assinatura -> {
            BigDecimal valuePay = assinatura.getEnd()
                    .map(endDate -> assinatura.getMonthlyFee().multiply(BigDecimal.valueOf(ChronoUnit.MONTHS.between(assinatura.getBegin(), endDate))))
                    .orElse(assinatura.getMonthlyFee().multiply(BigDecimal.valueOf(ChronoUnit.MONTHS.between(assinatura.getBegin(), LocalDate.now()))));
            LOG.info("Subscription: {} - Total Payment: {}", assinatura.getCustomer().getName(), valuePay);
        });
    }
}
