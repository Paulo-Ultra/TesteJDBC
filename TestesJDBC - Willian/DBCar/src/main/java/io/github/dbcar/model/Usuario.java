package io.github.dbcar.model;

import java.util.Objects;

public class Usuario {

    private Integer idUsuario;
    private String nome;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nome) {
        this.idUsuario = idUsuario;
        this.nome = nome;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario user = (Usuario) o;
        return Objects.equals(idUsuario, user.idUsuario) && Objects.equals(nome, user.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, nome);
    }

    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"nome\": \"" + getNome()
                + "\"}";
    }

}
