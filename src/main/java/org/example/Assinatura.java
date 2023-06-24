package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class Assinatura {

    private BigDecimal mensalidade;
    private LocalDate begin;
    private Optional<LocalDate> end;
    private Cliente cliente;

    public Assinatura(BigDecimal mensalidade, LocalDate begin, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = Optional.empty();
        this.cliente = cliente;
    }

    public Assinatura(BigDecimal mensalidade, LocalDate begin, LocalDate end, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = Optional.of(end);
        this.cliente = cliente;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(BigDecimal mensalidade) {
        this.mensalidade = mensalidade;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public Optional<LocalDate> getEnd() {
        return end;
    }

    public void setEnd(Optional<LocalDate> end) {
        this.end = end;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getMonthsActive() {
        LocalDate endDate = end.orElse(LocalDate.now());
        return Period.between(begin, endDate).getYears() * 12 + Period.between(begin, endDate).getMonths();
    }

    public BigDecimal getTotalPayment() {
        LocalDate endDate = end.orElse(LocalDate.now());
        int monthsActive = getMonthsActive();
        return mensalidade.multiply(BigDecimal.valueOf(monthsActive));
    }
}
