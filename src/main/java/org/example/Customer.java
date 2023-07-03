package org.example;

public class Customer {

    private String nome;

    public Customer(String nome) {
        this.nome = nome;
    }

    public String getName() {
        return nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                '}';
    }
}