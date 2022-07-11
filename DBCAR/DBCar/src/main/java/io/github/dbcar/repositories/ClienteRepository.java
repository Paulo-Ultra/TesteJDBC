package io.github.dbcar.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.infra.ConexaoBancoDeDados;
import io.github.dbcar.models.Cliente;

public class ClienteRepository implements Repository<Integer, Cliente> {

    private Connection con;

    public ClienteRepository() throws SQLException {
        this.con = ConexaoBancoDeDados.getConnection();
    }

    public ClienteRepository(Connection con) {
        this.con = con;
    }

    public Cliente create(Cliente cliente) {
        // clients.add(client);

        return cliente;
    }

    public ArrayList<Cliente> findAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM cliente");
            sql.append(" INNER JOIN users ON clients.id = users.id");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            ArrayList<Cliente> clientes = new ArrayList<>();

            while (result.next()) {
                clientes.add(compile(result));
            }

            return clientes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }

        return null;
    }

    public Cliente findById(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM cliente");
            sql.append(" INNER JOIN users ON cliente.id = usuario.id WHERE id = " + id);

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

    public Optional<Cliente> findOne(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM clients");
            sql.append(" INNER JOIN usuario ON cliente.id = usuario.id WHERE id = " + id);

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

    public void update(Integer id, Cliente client) {
        try {
            Cliente clienteSelecionado = findById(id);

            if (clienteSelecionado != null) {
                PreparedStatement stmt = con
                        .prepareStatement("UPDATE cliente SET cpf = ?, telefone = ?, endereco = ? WHERE id = ?");

                stmt.setString(1, clienteSelecionado.getNome());
                stmt.setString(2, clienteSelecionado.getTelefone());
                stmt.setString(3, clienteSelecionado.getEndereco());
                stmt.setInt(4, id);

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
            Cliente clienteSelecionado = findById(id);

            if (clienteSelecionado != null) {
                PreparedStatement stmt = con.prepareStatement("DELETE FROM cliente WHERE id = " + id);
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

            cliente.setId(result.getInt("id"));
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
