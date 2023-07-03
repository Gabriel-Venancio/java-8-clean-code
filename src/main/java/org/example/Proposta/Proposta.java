package org.example.Proposta;

import java.time.LocalDate;

public class Proposta {
    private String tipo;
    private String nomeCliente;
    private String descricao;
    private double valor;
    private LocalDate prazoValidade;

    public Proposta(String tipo, String nomeCliente, String descricao, double valor, LocalDate prazoValidade) {
        this.tipo = tipo;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.valor = valor;
        this.prazoValidade = prazoValidade;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getPrazoValidade() {
        return prazoValidade;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "tipo='" + tipo + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", prazoValidade=" + prazoValidade +
                '}';
    }

}
