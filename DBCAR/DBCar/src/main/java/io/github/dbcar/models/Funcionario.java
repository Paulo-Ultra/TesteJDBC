package io.github.dbcar.models;

import java.util.Objects;

public class Funcionario extends Usuario {

    private String matricula;

    public Funcionario() {
    }

    public Funcionario(Integer id, String name, String matricula) {
        super(id, name);
        this.matricula = matricula;
    }

    public Integer getId() {
        return super.getId();
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    public String getNome() {
        return super.getNome();
    }

    public void setNome(String name) {
        super.setNome(name);
    }

    public String getRegistration() {
        return this.matricula;
    }

    public void setRegistration(String registration) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Funcionario)) {
            return false;
        }
        Funcionario funcionario = (Funcionario) o;
        return Objects.equals(getId(), funcionario.getId()) && Objects.equals(getNome(), funcionario.getNome())
                && Objects.equals(matricula, funcionario.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), matricula);
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"nome\": \"" + getNome() + "\", "
                + "\"matrícula\": \"" + matricula
                + "\"}";
    }

}
