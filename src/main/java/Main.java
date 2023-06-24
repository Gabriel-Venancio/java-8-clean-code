import org.example.Assinatura;
import org.example.Cliente;
import org.example.Pagamento;
import org.example.Produto;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println("########### Exercício 1 ###########");

        Produto musicaStargazing = new Produto("Música", Paths.get("musicas/travis-scoot/stargazing.mp3"), BigDecimal.valueOf(1.99));
        Produto musicaAstroThunder = new Produto("Música", Paths.get("musicas/travis-scoot/astrothunder.mp3"), BigDecimal.valueOf(1.99));
        Produto videoShowTravisScoot = new Produto("Vídeo", Paths.get("videos/travis-scoot/show-travis-scoot.mp4"), BigDecimal.valueOf(3.99));
        Produto imagemCapaAlbumAstroworld = new Produto("Imagem", Paths.get("imagens/travis-scoot/astroworld.png"), BigDecimal.valueOf(0.99));

        Cliente paulo = new Cliente("Paulo");
        Cliente ygor = new Cliente ("Ygor");
        Cliente gabriel = new Cliente ("Gabriel");
        Cliente laiza = new Cliente ("Laiza");

        LocalDate hoje = LocalDate.now();
        LocalDate ontem = hoje.minusDays(1);
        LocalDate mesPassado = hoje.minusMonths(1);


        Pagamento pagamentoYgor = new Pagamento(Arrays.asList(musicaStargazing,musicaAstroThunder), ontem, ygor);
        Pagamento pagamentoGabriel = new Pagamento(Arrays.asList(imagemCapaAlbumAstroworld,videoShowTravisScoot), hoje, gabriel);
        Pagamento pagamentoPaulo = new Pagamento(Arrays.asList(musicaStargazing, videoShowTravisScoot, musicaAstroThunder), mesPassado, paulo);
        Pagamento pagamentoLaiza = new Pagamento(Arrays.asList(videoShowTravisScoot), hoje, laiza);

        List<Pagamento> pagamentos = Arrays.asList(pagamentoPaulo, pagamentoYgor, pagamentoGabriel, pagamentoLaiza);

        System.out.println("########### Exercício 2 ###########");

        Collections.sort(pagamentos, Comparator.comparing(Pagamento::getDataCompra));
        System.out.println("Pagamentos ordenados pela data de compra:");
        pagamentos.forEach(pagamento -> System.out.println(pagamento.getDataCompra()));

        System.out.println("########### Exercício 3 ###########");

        System.out.println(pagamentoGabriel.getTotalPayment());
        System.out.println(pagamentoGabriel.getTotalPaymentDouble());

        System.out.println("########### Exercício 4 ###########");

        BigDecimal valorTotal = pagamentos.stream().map(Pagamento::getTotalPayment).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Valor total dos pagamentos: " + valorTotal);

        System.out.println("########### Exercício 5 ###########");

        Map<String, Long> quantidadePorProduto = pagamentos.stream()
                .flatMap(pagamento -> pagamento.getProdutos().stream())
                .collect(Collectors.groupingBy(Produto::getNome, Collectors.counting()));
        System.out.println("Quantidade de cada produto vendido:");
        quantidadePorProduto.forEach((produto, quantidade) -> System.out.println(produto + ": " + quantidade));

        System.out.println("########### Exercício 6 ###########");

        Map<Cliente, List<Produto>> mapaClientesProdutos = pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente,
                        Collectors.flatMapping(pagamento -> pagamento.getProdutos().stream(), Collectors.toList())));
        System.out.println("Mapa de clientes e produtos:");
        mapaClientesProdutos.forEach((cliente, produtos) -> System.out.println(cliente.getNome() + ": " + produtos));

        System.out.println("########### Exercício 7 ###########");

        Map<Cliente, BigDecimal> totalGastoPorCliente = pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente,
                        Collectors.mapping(Pagamento::getTotalPayment, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        Cliente clienteGastouMais = totalGastoPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println("Cliente que gastou mais: " + clienteGastouMais.getNome());


        System.out.println("########### Exercício 8 ###########");

        BigDecimal faturamentoMesPassado  = pagamentos
                .stream()
                .filter(pagto -> pagto.getDataCompra().equals(mesPassado))
                .map(Pagamento::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Faturamento em " + mesPassado + ": " + faturamentoMesPassado);

        System.out.println("########### Exercício 9 ###########");

        Assinatura assinaturaPaulo = new Assinatura(BigDecimal.valueOf(99.98), LocalDate.of(2023, Month.JANUARY, 1), paulo);
        Assinatura assinaturaYgor = new Assinatura(BigDecimal.valueOf(99.98), LocalDate.of(2022, Month.DECEMBER, 1),
                LocalDate.of(2023, Month.MARCH, 1), ygor);
        Assinatura assinaturaGabriel = new Assinatura(BigDecimal.valueOf(99.98), LocalDate.of(2021, Month.NOVEMBER, 1),
                LocalDate.of(2022, Month.APRIL, 1), gabriel);

        List<Assinatura> assinaturas = List.of(assinaturaPaulo, assinaturaYgor, assinaturaGabriel);

        System.out.println("########### Exercício 10 ###########");
        long tempoAssinaturaAtivaPaulo = ChronoUnit.MONTHS.between(assinaturaPaulo.getBegin(), assinaturaPaulo.getEnd().orElse(hoje));
        System.out.println(tempoAssinaturaAtivaPaulo + " meses");

        System.out.println("########### Exercício 11 ###########");

        assinaturas.stream()
                .filter(assinatura -> assinatura.getEnd().isPresent())
                .forEach(assinatura -> {
                    long mesesAssinatura = ChronoUnit.MONTHS.between(assinatura.getBegin(), assinatura.getEnd().get());
                    System.out.println("Assinatura: " + assinatura.getCliente().getNome() + " - Meses: " + mesesAssinatura);
                });

        System.out.println("########### Exercício 12 ###########");

        assinaturas.forEach(assinatura -> {
            BigDecimal valorPago = assinatura.getEnd()
                    .map(endDate -> assinatura.getMensalidade().multiply(BigDecimal.valueOf(ChronoUnit.MONTHS.between(assinatura.getBegin(), endDate))))
                    .orElse(assinatura.getMensalidade().multiply(BigDecimal.valueOf(ChronoUnit.MONTHS.between(assinatura.getBegin(), LocalDate.now()))));
            System.out.println("Assinatura: " + assinatura.getCliente().getNome() + " - Valor pago: " + valorPago);
        });
    }
}
