package org.example.Proposta;

import java.time.LocalDate;

public class Runner {

    public static void main(String[] args) {
        Proposta primeiraVenda = new Proposta("Primeira Venda", "João", "Descrição da primeira venda", 1000.0, LocalDate.now().plusDays(7));
        System.out.println(primeiraVenda);

        Proposta upgrade = new Proposta("Upgrade", "Maria", "Descrição do upgrade", 500.0, LocalDate.now().plusDays(5));
        System.out.println(upgrade);

        Proposta migracao = new Proposta("Migração", "José", "Descrição da migração", 800.0, LocalDate.now().plusDays(10));
        System.out.println(migracao);
    }
}
