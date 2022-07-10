package io.github.dbcar.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.infra.CustomConnection;
import io.github.dbcar.models.Car;
import io.github.dbcar.models.Client;
import io.github.dbcar.models.Rent;

public class RentsRepository implements Repository<Integer, Rent> {

    private Connection conn;

    public RentsRepository() {
        this.conn = CustomConnection.getConnection();
    }

    public RentsRepository(Connection conn) {
        this.conn = conn;
    }

    public Rent create(Rent rent) {
        // rents.add(rent);

        return rent;
    }

    public ArrayList<Rent> findAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM rents");
            sql.append(" INNER JOIN clients ON rents.client_id = clients.id");
            sql.append(" INNER JOIN cars ON rents.car_id = cars.id");

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            ArrayList<Rent> rents = new ArrayList<>();

            while (result.next()) {
                rents.add(compile(result));
            }

            return rents;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }

        return null;
    }

    public Rent findById(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM rents");
            sql.append(" INNER JOIN clients ON rents.client_id = clients.id");
            sql.append(" INNER JOIN cars ON rents.car_id = cars.id");
            sql.append(" WHERE id = " + id);

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
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

    public Optional<Rent> findOne(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM rents");
            sql.append(" INNER JOIN clients ON rents.client_id = clients.id");
            sql.append(" INNER JOIN cars ON rents.car_id = cars.id");
            sql.append(" WHERE id = " + id);

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
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

    public void update(Integer id, Rent rent) {
        try {
            Rent rentSelected = findById(id);

            if (rentSelected != null) {
                StringBuilder sql = new StringBuilder("UPDATE rents");
                sql.append(" SET car_id = ?, client_id = ?, startOfRent = ?, endOfRent = ?  WHERE id = ?");

                PreparedStatement stmt = conn.prepareStatement(sql.toString());

                stmt.setInt(1, rent.getCar().getId());
                stmt.setInt(2, rent.getClient().getId());
                stmt.setDate(3, Date.valueOf(rent.getStartOfRent()));
                stmt.setDate(4, Date.valueOf(rent.getEndOfRent()));
                stmt.setInt(5, id);

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
            Rent rentSelected = findById(id);

            if (rentSelected != null) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM rents WHERE id = " + id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }
    }

    protected static Rent compile(ResultSet result) {
        try {
            Rent rent = new Rent();
            Car car = CarsRepository.compile(result);
            Client client = ClientsRepository.compile(result);

            rent.setId(result.getInt("id"));
            rent.setCar(car);
            rent.setClient(client);
            rent.setStartOfRent(result.getDate("startOfRent").toLocalDate());
            rent.setEndOfRent(result.getDate("endOfRent").toLocalDate());

            return rent;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
