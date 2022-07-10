package io.github.dbcar.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.infra.CustomConnection;
import io.github.dbcar.models.Carro;

public class CarroRepository implements Repository<Integer, Carro> {

    private Connection con;

    public CarroRepository() {
        this.con = CustomConnection.getConnection();
    }

    public CarroRepository(Connection con) {
        this.con = con;
    }

    public Carro create(Carro carro) {
//         carro.add(carro);

        return carro;
    }

    public ArrayList<Carro> findAll() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cars");
            ResultSet result = stmt.executeQuery();

            ArrayList<Carro> cars = new ArrayList<>();

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

    public Carro findById(Integer id) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cars WHERE id = " + id);
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

    public Optional<Carro> findOne(Integer id) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cars WHERE id = " + id);
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

    public void update(Integer id, Carro carro) {
        try {
            Carro carroSelecionado = findById(id);

            if (carroSelecionado != null) {
                StringBuilder sql = new StringBuilder();

                sql.append("UPDATE cars SET name = ?, brand = ?, rank = ?, ");
                sql.append("passengers = ?, kmDriven = ?, dailyPrice = ? WHERE id = ?");

                PreparedStatement stmt = con.prepareStatement(sql.toString());

                stmt.setString(1, carro.getNome());
                stmt.setString(2, carro.getMarca());
                stmt.setString(3, carro.getClasse());
                stmt.setInt(4, carro.getQntPassageiros());
                stmt.setInt(5, carro.getKmRodados());
                stmt.setDouble(6, carro.getPrecoDiaria());
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
            Carro carroSelecionado = findById(id);

            if (carroSelecionado != null) {
                PreparedStatement stmt = con.prepareStatement("DELETE FROM cars WHERE id = " + id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }
    }

    protected static Carro compile(ResultSet result) {
        try {
            Carro carro = new Carro();

            carro.setIdCarro(result.getInt("id"));
            carro.setAlugado(result.getString("rented"));
            carro.setNome(result.getString("name"));
            carro.setMarca(result.getString("brand"));
            carro.setClasse(result.getString("rank"));
            carro.setQntPassageiros(result.getInt("passengers"));
            carro.setQntPassageiros(result.getInt("kmDriven"));
            carro.setPrecoDiaria(result.getDouble("dailyPrice"));

            return carro;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
