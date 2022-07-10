package io.github.dbcar.models;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Rent {

    private Integer id;
    private Car car;
    private Client client;
    private LocalDate startOfRent;
    private LocalDate endOfRent;

    public Rent() {
    }

    public Rent(Integer id, Car car, Client client, LocalDate startOfRent, LocalDate endOfRent) {
        this.id = id;
        this.car = car;
        this.client = client;
        this.startOfRent = startOfRent;
        this.endOfRent = endOfRent;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getStartOfRent() {
        return this.startOfRent;
    }

    public void setStartOfRent(LocalDate startOfRent) {
        this.startOfRent = startOfRent;
    }

    public LocalDate getEndOfRent() {
        return this.endOfRent;
    }

    public void setEndOfRent(LocalDate endOfRent) {
        this.endOfRent = endOfRent;
    }

    public Long daysWithCar() {
        return startOfRent.until(endOfRent, ChronoUnit.DAYS);
    }

    public Double calculateDaily() {
        switch (car.getGroup()) {
            case "A":
                return daysWithCar() * car.getDailyPrice() * 1.5;
            case "B":
                return daysWithCar() * car.getDailyPrice() * 1.2;
            case "C":
                return daysWithCar() * car.getDailyPrice();
            default:
                System.out.println("O carro informado não está disponível.");
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Rent)) {
            return false;
        }
        Rent rent = (Rent) o;
        return Objects.equals(id, rent.id) && Objects.equals(car, rent.car) && Objects.equals(client, rent.client)
                && Objects.equals(startOfRent, rent.startOfRent) && Objects.equals(endOfRent, rent.endOfRent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, client, startOfRent, endOfRent);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"car\": " + getCar() + ", "
                + "\"client\": " + getClient() + ", "
                + "\"startOfRent\": \"" + getStartOfRent().format(formatter) + "\", "
                + "\"endOfRent\": \"" + getEndOfRent().format(formatter) + "\", "
                + "\"days\": " + daysWithCar() + ", "
                + "\"amount\": \"" + df.format(calculateDaily())
                + "\"}";
    }
}