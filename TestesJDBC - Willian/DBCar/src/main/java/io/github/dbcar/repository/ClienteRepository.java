package io.github.dbcar.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.connection.ConexaoBancoDeDados;
import io.github.dbcar.model.Cliente;

public class ClienteRepository implements Repository<Integer, Cliente> {

    private Connection connect;

    public ClienteRepository() {
        this.connect = ConexaoBancoDeDados.getConnection();
    }

    public ClienteRepository(Connection connect) {
        this.connect = connect;
    }

    public Cliente create(Cliente cliente) {
        // clients.add(client);
        return cliente;
    }

    public ArrayList<Cliente> findAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM CLIENTE");
            sql.append(" INNER JOIN USUARIO ON CLIENTE.ID = USUARIO.ID");

            PreparedStatement stmt = connect.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            ArrayList<Cliente> cliente = new ArrayList<>();

            while (result.next()) {
                cliente.add(compile(result));
            }

            return cliente;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
        return null;
    }

    public Cliente findById(Integer idCliente) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM CLIENTE");
            sql.append(" INNER JOIN USUARIO ON CLIENTE.ID = USUARIO.ID WHERE id = " + idCliente);

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

    public Optional<Cliente> findOne(Integer idCliente) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM CLIENTE");
            sql.append(" INNER JOIN USUARIO ON CLIENTE.ID = USUARIO.ID WHERE id = " + idCliente);

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

    public void update(Integer idCliente, Cliente cliente) {
        try {
            Cliente clienteSelecionado = findById(idCliente);

            if (clienteSelecionado != null) {
                PreparedStatement stmt = connect
                        .prepareStatement("UPDATE CLIENTE SET cpf = ?, telefone = ?, endereco = ? WHERE id = ?");

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getTelefone());
                stmt.setString(3, cliente.getEndereco());
                stmt.setInt(4, idCliente);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    public void delete(Integer idCliente) {
        try {
            Cliente clienteSelecionado = findById(idCliente);

            if (clienteSelecionado != null) {
                PreparedStatement stmt = connect.prepareStatement("DELETE FROM CLIENTE WHERE id = " + idCliente);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
    }

    protected static Cliente compile(ResultSet result) {
        try {
            Cliente cliente = new Cliente();

            cliente.setIdUsuario(result.getInt("id_usuario"));
            cliente.setNome(result.getString("nome"));
            cliente.setTelefone(result.getString("telefone"));
            cliente.setEndereco(result.getString("endereco"));
            cliente.setCpf(result.getString("cpf"));

            return cliente;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
