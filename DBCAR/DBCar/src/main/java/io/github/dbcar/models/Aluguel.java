package io.github.dbcar.models;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Aluguel {

    private Integer idAluguel;
    private Carro carro;
    private Cliente cliente;
    private LocalDate dataDoAluguel;
    private LocalDate dataDaDevolucao;

    public Aluguel() {
    }

    public Aluguel(Integer idAluguel, Carro carro, Cliente cliente, LocalDate dataDoAluguel, LocalDate dataDaDevolucao) {
        this.idAluguel = idAluguel;
        this.carro = carro;
        this.cliente = cliente;
        this.dataDoAluguel = dataDoAluguel;
        this.dataDaDevolucao = dataDaDevolucao;
    }

    public Integer getId() {
        return this.idAluguel;
    }

    public void setId(Integer idAluguel) {
        this.idAluguel = idAluguel;
    }

    public Carro getCarro() {
        return this.carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(Integer idAluguel) {
        this.idAluguel = idAluguel;
    }

    public LocalDate getDataDoAluguel() {
        return dataDoAluguel;
    }

    public void setDataDoAluguel(LocalDate dataDoAluguel) {
        this.dataDoAluguel = dataDoAluguel;
    }

    public LocalDate getDataDaDevolucao() {
        return dataDaDevolucao;
    }

    public void setDataDaDevolucao(LocalDate dataDaDevolucao) {
        this.dataDaDevolucao = dataDaDevolucao;
    }

    public Long diasComCarro () {
        return dataDoAluguel.until(dataDaDevolucao, ChronoUnit.DAYS);
    }

    public Double calculateDaily() {
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
        Aluguel rent = (Aluguel) o;
        return Objects.equals(idAluguel, rent.idAluguel) && Objects.equals(carro, rent.carro) && Objects.equals(cliente, rent.cliente)
                && Objects.equals(dataDoAluguel, rent.dataDoAluguel) && Objects.equals(dataDaDevolucao, rent.dataDoAluguel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAluguel, carro, cliente, dataDoAluguel, dataDaDevolucao);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"id\": " + getIdAluguel() + ", "
                + "\"carro\": " + getCarro() + ", "
                + "\"cliente\": " + getCliente() + ", "
                + "\"data do Aluguel\": \"" + getDataDoAluguel().format(formatter) + "\", "
                + "\"data da devolução\": \"" + getDataDaDevolucao().format(formatter) + "\", "
                + "\"dias com carro\": " + diasComCarro() + ", "
                + "\"valor\": \"" + df.format(calculateDaily())
                + "\"}";
    }
}