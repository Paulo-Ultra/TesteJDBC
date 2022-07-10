package io.github.dbcar.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.infra.CustomConnection;
import io.github.dbcar.models.Car;

public class CarsRepository implements Repository<Integer, Car> {

    private Connection conn;

    public CarsRepository() {
        this.conn = CustomConnection.getConnection();
    }

    public CarsRepository(Connection conn) {
        this.conn = conn;
    }

    public Car create(Car car) {
        // cars.add(car);

        return car;
    }

    public ArrayList<Car> findAll() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cars");
            ResultSet result = stmt.executeQuery();

            ArrayList<Car> cars = new ArrayList<>();

            while (result.next()) {
                cars.add(compile(result));
            }

            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }

        return null;
    }

    public Car findById(Integer id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cars WHERE id = " + id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return compile(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }

        return null;
    }

    public Optional<Car> findOne(Integer id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cars WHERE id = " + id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return Optional.of(compile(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }

        return Optional.empty();
    }

    public void update(Integer id, Car car) {
        try {
            Car carSelected = findById(id);

            if (carSelected != null) {
                StringBuilder sql = new StringBuilder();

                sql.append("UPDATE cars SET name = ?, brand = ?, rank = ?, ");
                sql.append("passengers = ?, kmDriven = ?, dailyPrice = ? WHERE id = ?");

                PreparedStatement stmt = conn.prepareStatement(sql.toString());

                stmt.setString(1, car.getName());
                stmt.setString(2, car.getBrand());
                stmt.setString(3, car.getGroup());
                stmt.setInt(4, car.getPassengers());
                stmt.setInt(5, car.getKmDriven());
                stmt.setDouble(6, car.getDailyPrice());
                stmt.setInt(7, id);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }
    }

    public void delete(Integer id) {
        try {
            Car carSelected = findById(id);

            if (carSelected != null) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM cars WHERE id = " + id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }
    }

    protected static Car compile(ResultSet result) {
        try {
            Car car = new Car();

            car.setId(result.getInt("id"));
            car.setRented(result.getString("rented"));
            car.setName(result.getString("name"));
            car.setBrand(result.getString("brand"));
            car.setGroup(result.getString("rank"));
            car.setPassengers(result.getInt("passengers"));
            car.setKmDriven(result.getInt("kmDriven"));
            car.setDailyPrice(result.getDouble("dailyPrice"));

            return car;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
