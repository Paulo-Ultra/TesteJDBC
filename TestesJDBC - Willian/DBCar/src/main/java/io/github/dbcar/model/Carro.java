package io.github.dbcar.model;

import java.text.DecimalFormat;
import java.util.Objects;

public class Carro {
    private Integer idCarro;
    private String alugado;
    private String nome;
    private String marca;
    private String classe;
    private Integer quantidade_passageiros;
    private Integer kmRodados;
    private Double precoDiaria;

    public Carro() {
    }

    public Carro(Integer idCarro, String alugado, String nome, String marca, String classe, Integer quantidade_passageiros, Integer kmRodados, Double precoDiaria) {
        this.idCarro = idCarro;
        this.alugado = alugado;
        this.nome = nome;
        this.marca = marca;
        this.classe = classe;
        this.quantidade_passageiros = quantidade_passageiros;
        this.kmRodados = kmRodados;
        this.precoDiaria = precoDiaria;
    }

    public Integer getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(Integer idCarro) {
        this.idCarro = idCarro;
    }

    public String getAlugado() {
        return alugado;
    }

    public void setAlugado(String alugado) {
        this.alugado = alugado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getQuantidade_passageiros() {
        return quantidade_passageiros;
    }

    public void setQuantidade_passageiros(Integer quantidade_passageiros) {
        this.quantidade_passageiros = quantidade_passageiros;
    }

    public Integer getKmRodados() {
        return kmRodados;
    }

    public void setKmRodados(Integer kmRodados) {
        this.kmRodados = kmRodados;
    }

    public Double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(Double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Carro)) {
            return false;
        }
        Carro carro = (Carro) o;
        return Objects.equals(idCarro, carro.idCarro) && Objects.equals(alugado, carro.alugado) && Objects.equals(nome, carro.nome)
                && Objects.equals(marca, carro.marca) && Objects.equals(classe, carro.classe)
                && Objects.equals(quantidade_passageiros, carro.quantidade_passageiros)
                && Objects.equals(kmRodados, carro.kmRodados) && Objects.equals(precoDiaria, carro.precoDiaria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarro, alugado, nome, marca, classe, quantidade_passageiros, kmRodados, precoDiaria);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"idCarro\": " + getIdCarro() + ", "
                + "\"nome\": \"" + getNome() + "\", "
                + "\"marca\": \"" + getMarca() + "\", "
                + "\"classe\": \"" + getClasse() + "\", "
                + "\"Km Rodados\": " + getKmRodados() + ", "
                + "\"Preço Diária\": \"" + df.format(getPrecoDiaria().doubleValue()) + "\", "
                + "\"Quantidade de Passageiros\": " + getQuantidade_passageiros() + ", "
                + "\"Alugado\": \"" + getAlugado()
                + "\"}";
    }
}