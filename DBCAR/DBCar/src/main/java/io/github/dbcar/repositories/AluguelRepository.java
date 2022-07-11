package io.github.dbcar.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.infra.ConexaoBancoDeDados;
import io.github.dbcar.models.Carro;
import io.github.dbcar.models.Cliente;
import io.github.dbcar.models.Aluguel;

public class AluguelRepository implements Repository<Integer, Aluguel> {

    private Connection con;

    public AluguelRepository() throws SQLException {
        this.con = ConexaoBancoDeDados.getConnection();
    }

    public AluguelRepository(Connection conn) {
        this.con = conn;
    }

    public Aluguel create(Aluguel rent) {
        // rents.add(rent);

        return rent;
    }

    public ArrayList<Aluguel> findAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM rents");
            sql.append(" INNER JOIN clients ON rents.client_id = clients.id");
            sql.append(" INNER JOIN cars ON rents.car_id = cars.id");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            ArrayList<Aluguel> rents = new ArrayList<>();

            while (result.next()) {
                rents.add(compile(result));
            }

            return rents;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }

        return null;
    }

    public Aluguel findById(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM rents");
            sql.append(" INNER JOIN clients ON rents.client_id = clients.id");
            sql.append(" INNER JOIN cars ON rents.car_id = cars.id");
            sql.append(" WHERE id = " + id);

            PreparedStatement stmt = con.prepareStatement(sql.toString());
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

    public Optional<Aluguel> findOne(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM rents");
            sql.append(" INNER JOIN clients ON rents.client_id = clients.id");
            sql.append(" INNER JOIN cars ON rents.car_id = cars.id");
            sql.append(" WHERE id = " + id);

            PreparedStatement stmt = con.prepareStatement(sql.toString());
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

    public void update(Integer id, Aluguel aluguel) {
        try {
            Aluguel aluguelSelecionado = findById(id);

            if (aluguelSelecionado != null) {
                StringBuilder sql = new StringBuilder("UPDATE rents");
                sql.append(" SET carro_id = ?, cliente_id = ?, dia_do_aluguel = ?, dia_da_entrega = ?  WHERE id = ?");

                PreparedStatement stmt = con.prepareStatement(sql.toString());

                stmt.setInt(1, aluguel.getCarro().getIdCarro());
                stmt.setInt(2, aluguel.getCliente().getId());
                stmt.setDate(3, Date.valueOf(aluguel.getDataDoAluguel()));
                stmt.setDate(4, Date.valueOf(aluguel.getDataDaDevolucao()));
                stmt.setInt(5, id);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    public void delete(Integer id) {
        try {
            Aluguel aluguelSelecionado = findById(id);

            if (aluguelSelecionado != null) {
                PreparedStatement stmt = con.prepareStatement("DELETE FROM rents WHERE id = " + id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    protected static Aluguel compile(ResultSet result) {
        try {
            Aluguel aluguel = new Aluguel();
            Carro carro = CarroRepository.compile(result);
            Cliente cliente = ClienteRepository.compile(result);

            aluguel.setId(result.getInt("id"));
            aluguel.setCarro(carro);
            aluguel.setCliente(cliente);
            aluguel.setDataDoAluguel(result.getDate("diadoaluguel").toLocalDate());
            aluguel.setDataDaDevolucao(result.getDate("diadadevolucao").toLocalDate());

            return aluguel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
