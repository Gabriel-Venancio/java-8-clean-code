package PropostaBuilder;

import org.example.Proposta.Proposta;

import java.time.LocalDate;

public class PropostaBuilder {
    private String tipo;
    private String nomeCliente;
    private String descricao;
    private double valor;
    private LocalDate prazoValidade;

    private PropostaBuilder(String tipo, String nomeCliente, String descricao, double valor, LocalDate prazoValidade) {
        this.tipo = tipo;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.valor = valor;
        this.prazoValidade = prazoValidade;
    }

    public static class Builder {
        private String tipo;
        private String nomeCliente;
        private String descricao;
        private double valor;
        private LocalDate prazoValidade;

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder nomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder valor(double valor) {
            this.valor = valor;
            return this;
        }

        public Builder prazoValidade(LocalDate prazoValidade) {
            this.prazoValidade = prazoValidade;
            return this;
        }

        public Proposta build() {
            return new Proposta(tipo, nomeCliente, descricao, valor, prazoValidade);
        }
    }
}
