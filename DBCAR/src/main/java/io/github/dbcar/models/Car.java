package io.github.dbcar.models;

import java.text.DecimalFormat;
import java.util.Objects;

public class Car {

    private Integer id;
    private String rented;
    private String name;
    private String brand;
    private String group;
    private Integer passengers;
    private Integer kmDriven;
    private Double dailyPrice;

    public Car() {
    }

    public Car(Integer id, String rented, String name, String brand, String group, Integer passengers,
            Integer kmDriven, Double dailyPrice) {
        this.id = id;
        this.rented = rented;
        this.name = name;
        this.brand = brand;
        this.group = group;
        this.passengers = passengers;
        this.kmDriven = kmDriven;
        this.dailyPrice = dailyPrice;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRented() {
        return this.rented;
    }

    public void setRented(String rented) {
        this.rented = rented;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Integer getKmDriven() {
        return this.kmDriven;
    }

    public void setKmDriven(Integer kmDriven) {
        this.kmDriven = kmDriven;
    }

    public Double getDailyPrice() {
        return this.dailyPrice;
    }

    public void setDailyPrice(Double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Car)) {
            return false;
        }
        Car carro = (Car) o;
        return Objects.equals(id, carro.id) && Objects.equals(rented, carro.rented) && Objects.equals(name, carro.name)
                && Objects.equals(brand, carro.brand) && Objects.equals(group, carro.group)
                && Objects.equals(passengers, carro.passengers)
                && Objects.equals(kmDriven, carro.kmDriven) && Objects.equals(dailyPrice, carro.dailyPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rented, name, brand, group, passengers, kmDriven, dailyPrice);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "{"
                + "\"id\": " + getId() + ", "
                + "\"name\": \"" + getName() + "\", "
                + "\"brand\": \"" + getBrand() + "\", "
                + "\"group\": \"" + getGroup() + "\", "
                + "\"kmDriven\": " + getKmDriven() + ", "
                + "\"dailyPrice\": \"" + df.format(getDailyPrice().doubleValue()) + "\", "
                + "\"passengers\": " + getPassengers() + ", "
                + "\"rented\": \"" + getRented()
                + "\"}";
    }

}
