package io.github.dbcar.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.connection.ConexaoBancoDeDados;
import io.github.dbcar.model.Carro;

public class CarroRepository implements Repository<Integer, Carro> {

    private Connection connect;

    public CarroRepository() {
        this.connect = ConexaoBancoDeDados.getConnection();
    }

    public CarroRepository(Connection connect) {
        this.connect = connect;
    }

    public Carro create(Carro carro) {
        // cars.add(car);
        return carro;
    }

    public ArrayList<Carro> findAll() {
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM CARRO");
            ResultSet result = stmt.executeQuery();

            ArrayList<Carro> cars = new ArrayList<>();

            while (result.next()) {
                cars.add(compile(result));
            }

            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
        return null;
    }

    public Carro findById(Integer idCarro) {
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM CARRO WHERE id = " + idCarro);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return compile(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
        return null;
    }

    public Optional<Carro> findOne(Integer idCarro) {
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM CARRO WHERE id = " + idCarro);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return Optional.of(compile(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }

        return Optional.empty();
    }

    public void update(Integer idCarro, Carro carro) {
        try {
            Carro carroSelecionado = findById(idCarro);

            if (carroSelecionado != null) {
                StringBuilder sql = new StringBuilder();

                sql.append("UPDATE CARRO SET nome = ?, marca = ?, classe = ?, ");
                sql.append("quantidade_passageiros = ?, km_rodados = ?, preco_diaria = ? WHERE id = ?");

                PreparedStatement stmt = connect.prepareStatement(sql.toString());

                stmt.setString(1, carro.getNome());
                stmt.setString(2, carro.getMarca());
                stmt.setString(3, carro.getClasse());
                stmt.setInt(4, carro.getQuantidade_passageiros());
                stmt.setInt(5, carro.getKmRodados());
                stmt.setDouble(6, carro.getPrecoDiaria());
                stmt.setInt(7, idCarro);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    public void delete(Integer idCarro) {
        try {
            Carro carroSelecionado = findById(idCarro);

            if (carroSelecionado != null) {
                PreparedStatement stmt = connect.prepareStatement("DELETE FROM CARRO WHERE id = " + idCarro);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    protected static Carro compile(ResultSet result) {
        try {
            Carro carro = new Carro();

            carro.setIdCarro(result.getInt("id_carro"));
            carro.setAlugado(result.getString("alugado"));
            carro.setNome(result.getString("nome"));
            carro.setMarca(result.getString("marca"));
            carro.setClasse(result.getString("classe"));
            carro.setQuantidade_passageiros(result.getInt("quantidade_passageiros"));
            carro.setKmRodados(result.getInt("km_rodados"));
            carro.setPrecoDiaria(result.getDouble("preco_diaria"));

            return carro;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
