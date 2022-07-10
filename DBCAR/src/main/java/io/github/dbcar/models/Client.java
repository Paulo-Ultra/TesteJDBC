package io.github.dbcar.models;

import java.util.Objects;

public class Client extends User {

    private String cpf;
    private String phone;
    private String address;

    public Client() {
    }

    public Client(Integer id, String name, String cpf, String phone, String address) {
        super(id, name);
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
    }

    public Integer getId() {
        return super.getId();
    }

    public void setId(String id) {
        this.setId(id);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(getId(), client.getId()) && Objects.equals(getName(), client.getName())
                && Objects.equals(cpf, client.cpf) && Objects.equals(phone, client.phone)
                && Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), cpf, phone, address);
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"name\": \"" + getName() + "\", "
                + "\"cpf\": \"" + getCpf() + "\", "
                + "\"phone\": \"" + getPhone() + "\", "
                + "\"address\": \"" + getAddress()
                + "\"}";
    }

}