package io.github.dbcar.models;

import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nome;

    public Usuario() {
    }

    public Usuario(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return Objects.equals(id, user.id) && Objects.equals(nome, user.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"name\": \"" + getNome()
                + "\"}";
    }

}
