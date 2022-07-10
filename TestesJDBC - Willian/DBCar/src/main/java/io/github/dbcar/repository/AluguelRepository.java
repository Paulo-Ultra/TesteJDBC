package io.github.dbcar.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.connection.ConexaoBancoDeDados;
import io.github.dbcar.model.Carro;
import io.github.dbcar.model.Cliente;
import io.github.dbcar.model.Aluguel;

public class AluguelRepository implements Repository<Integer, Aluguel> {

    private Connection connect;

    public AluguelRepository() {
        this.connect = ConexaoBancoDeDados.getConnection();
    }

    public AluguelRepository(Connection conn) {
        this.connect = conn;
    }

    public Aluguel create(Aluguel rent) {
        // aluguel.add(aluguel);
        return rent;
    }

    public ArrayList<Aluguel> findAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM ALUGUEL");
            sql.append(" INNER JOIN CLIENTE ON ALUGUEL.id_cliente = CLIENTE.ID");
            sql.append(" INNER JOIN CARRO ON ALUGUEL.id_carro = CARRO.ID");

            PreparedStatement stmt = connect.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            ArrayList<Aluguel> aluguel = new ArrayList<>();

            while (result.next()) {
                aluguel.add(compile(result));
            }

            return aluguel;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
        return null;
    }

    public Aluguel findById(Integer idAluguel) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM ALUGUEL");
            sql.append(" INNER JOIN CLIENTE ON ALUGUEL.id_cliente = CLIENTE.ID");
            sql.append(" INNER JOIN CARRO ON ALUGUEL.id_carro = CARRO.ID");
            sql.append(" WHERE id = " + idAluguel);

            PreparedStatement stmt = connect.prepareStatement(sql.toString());
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

    public Optional<Aluguel> findOne(Integer idAluguel) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM ALUGUEL");
            sql.append(" INNER JOIN CLIENTE ON ALUGUEL.id_cliente = CLIENTE.ID");
            sql.append(" INNER JOIN CARRO ON ALUGUEL.id_carro = CARRO.ID");
            sql.append(" WHERE id = " + idAluguel);

            PreparedStatement stmt = connect.prepareStatement(sql.toString());
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

    public void update(Integer idAluguel, Aluguel aluguel) {
        try {
            Aluguel aluguelSelecionado = findById(idAluguel);

            if (aluguelSelecionado != null) {
                StringBuilder sql = new StringBuilder("UPDATE ALUGUEL");
                sql.append(" SET id_carro = ?, id_cliente = ?, diaDoAluguel = ?, diaDaEntrega = ?  WHERE id = ?");

                PreparedStatement stmt = connect.prepareStatement(sql.toString());

                stmt.setInt(1, aluguel.getCarro().getIdCarro());
                stmt.setInt(2, aluguel.getCliente().getIdUsuario());
                stmt.setDate(3, Date.valueOf(aluguel.getDiaDoAluguel()));
                stmt.setDate(4, Date.valueOf(aluguel.getDiaDaEntrega()));
                stmt.setInt(5, idAluguel);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    public void delete(Integer idAluguel) {
        try {
            Aluguel aluguelSelecionado = findById(idAluguel);

            if (aluguelSelecionado != null) {
                PreparedStatement stmt = connect.prepareStatement("DELETE FROM rents WHERE id = " + idAluguel);
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

            aluguel.setIdAluguel(result.getInt("id"));
            aluguel.setCarro(carro);
            aluguel.setCliente(cliente);
            aluguel.setDiaDoAluguel(result.getDate("diaDoAluguel").toLocalDate());
            aluguel.setDiaDaEntrega(result.getDate("diaDaEntrega").toLocalDate());

            return aluguel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
