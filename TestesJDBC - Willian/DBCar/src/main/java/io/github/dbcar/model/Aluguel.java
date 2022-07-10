package io.github.dbcar.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Aluguel {

    private Integer idAluguel;
    private Carro carro;
    private Cliente cliente;
    private LocalDate diaDoAluguel;
    private LocalDate diaDaEntrega;

    public Aluguel() {
    }

    public Aluguel(Integer idAluguel, Carro carro, Cliente cliente, LocalDate diaDoAluguel, LocalDate diaDaEntrega) {
        this.idAluguel = idAluguel;
        this.carro = carro;
        this.cliente = cliente;
        this.diaDoAluguel = diaDoAluguel;
        this.diaDaEntrega = diaDaEntrega;
    }

    public Integer getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(Integer idAluguel) {
        this.idAluguel = idAluguel;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDiaDoAluguel() {
        return diaDoAluguel;
    }

    public void setDiaDoAluguel(LocalDate diaDoAluguel) {
        this.diaDoAluguel = diaDoAluguel;
    }

    public LocalDate getDiaDaEntrega() {
        return diaDaEntrega;
    }

    public void setDiaDaEntrega(LocalDate diaDaEntrega) {
        this.diaDaEntrega = diaDaEntrega;
    }

    public Long diasComCarro() {
        return diaDaEntrega.until(diaDoAluguel, ChronoUnit.DAYS);
    }

    public Double calcularDiaria() {
        switch (carro.getClasse()) {
            case "A":
                return diasComCarro() * carro.getPrecoDiaria() * 1.5;
            case "B":
                return diasComCarro() * carro.getPrecoDiaria() * 1.2;
            case "C":
                return diasComCarro() * carro.getPrecoDiaria();
            default:
                System.out.println("O carro informado não está disponível.");
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Aluguel)) {
            return false;
        }
        Aluguel aluguel = (Aluguel) o;
        return Objects.equals(idAluguel, aluguel.idAluguel) && Objects.equals(carro, aluguel.carro) && Objects.equals(cliente, aluguel.cliente)
                && Objects.equals(diaDoAluguel, aluguel.diaDoAluguel) && Objects.equals(diaDaEntrega, aluguel.diaDaEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAluguel, carro, cliente, diaDoAluguel, diaDaEntrega);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"idAluguel\": " + getIdAluguel() + ", "
                + "\"Carro\": " + getCarro() + ", "
                + "\"Cliente\": " + getCliente() + ", "
                + "\"Dia do Aluguel\": \"" + getDiaDoAluguel().format(formatter) + "\", "
                + "\"Dia da Entrega\": \"" + getDiaDaEntrega().format(formatter) + "\", "
                + "\"Dias\": " + diasComCarro() + ", "
                + "\"Diária\": \"" + df.format(calcularDiaria())
                + "\"}";
    }
}