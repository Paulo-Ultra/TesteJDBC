package io.github.dbcar.models;

import java.util.Objects;

public class Cliente extends Usuario {

    private String cpf;
    private String telefone;
    private String endereco;

    public Cliente() {
    }

    public Cliente(String cpf, String telefone, String endereco) {
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Cliente(Integer id, String name, String cpf, String telefone, String endereco) {
        super(id, name);
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente client = (Cliente) o;
        return Objects.equals(getId(), client.getId()) && Objects.equals(getNome(), client.getNome())
                && Objects.equals(cpf, client.cpf) && Objects.equals(telefone, client.telefone)
                && Objects.equals(endereco, client.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), cpf, telefone, endereco);
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"name\": \"" + getNome() + "\", "
                + "\"cpf\": \"" + getCpf() + "\", "
                + "\"phone\": \"" + getTelefone() + "\", "
                + "\"address\": \"" + getEndereco()
                + "\"}";
    }

}