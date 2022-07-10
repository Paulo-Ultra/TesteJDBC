package io.github.dbcar.model;

import java.util.Objects;

public class Funcionario extends Usuario {

    private String matricula;

    public Funcionario() {
    }

    public Funcionario(Integer idUsuario, String nome, String matricula) {
        super(idUsuario, nome);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
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
        return Objects.equals(getIdUsuario(), funcionario.getIdUsuario()) && Objects.equals(getNome(), funcionario.getNome())
                && Objects.equals(matricula, funcionario.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNome(), matricula);
    }

    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"Nome\": \"" + getNome() + "\", "
                + "\"Matrícula\": \"" + matricula
                + "\"}";
    }

}
