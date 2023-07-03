package PropostaBuilder;

import org.example.Proposta.Proposta;

import java.time.LocalDate;

public class Runner {
    public static void main(String[] args) {
        Proposta primeiraVenda = new PropostaBuilder.Builder()
                .tipo("Primeira Venda")
                .nomeCliente("João")
                .valor(1000.0)
                .prazoValidade(LocalDate.now().plusDays(7))
                .build();

        System.out.println(primeiraVenda);

        Proposta upgrade = new PropostaBuilder.Builder()
                .tipo("Upgrade")
                .nomeCliente("Maria")
                .valor(500.0)
                .prazoValidade(LocalDate.now().plusDays(5))
                .build();

        System.out.println(upgrade);

        Proposta migracao = new PropostaBuilder.Builder()
                .tipo("Migração")
                .nomeCliente("José")
                .valor(800.0)
                .prazoValidade(LocalDate.now().plusDays(15))
                .build();

        System.out.println(migracao);
    }
}
