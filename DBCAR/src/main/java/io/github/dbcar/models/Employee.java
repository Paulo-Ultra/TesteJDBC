package io.github.dbcar.models;

import java.util.Objects;

public class Employee extends User {

    private String registration;

    public Employee() {
    }

    public Employee(Integer id, String name, String registration) {
        super(id, name);
        this.registration = registration;
    }

    public Integer getId() {
        return super.getId();
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getRegistration() {
        return this.registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee funcionario = (Employee) o;
        return Objects.equals(getId(), funcionario.getId()) && Objects.equals(getName(), funcionario.getName())
                && Objects.equals(registration, funcionario.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), registration);
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"name\": \"" + getName() + "\", "
                + "\"registration\": \"" + registration
                + "\"}";
    }

}
