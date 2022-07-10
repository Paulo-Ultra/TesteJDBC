package io.github.dbcar.model;

import java.util.Objects;

public class Cliente extends Usuario {

    private String cpf;
    private String telefone;
    private String endereco;

    public Cliente() {
    }

    public Cliente(Integer idUsuario, String nome, String cpf, String telefone, String endereco) {
        super(idUsuario, nome);
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
        Cliente cliente = (Cliente) o;
        return Objects.equals(getIdUsuario(), cliente.getIdUsuario()) && Objects.equals(getNome(), cliente.getNome())
                && Objects.equals(getCpf(), cliente.getCpf()) && Objects.equals(telefone, cliente.telefone)
                && Objects.equals(endereco, cliente.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNome(), cpf, telefone, endereco);
    }

    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"Nome\": \"" + getNome() + "\", "
                + "\"CPF\": \"" + getCpf() + "\", "
                + "\"Telefone\": \"" + getTelefone() + "\", "
                + "\"Endereço\": \"" + getEndereco()
                + "\"}";
    }
}