package io.github.dbcar.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.infra.CustomConnection;
import io.github.dbcar.models.Client;

public class ClientsRepository implements Repository<Integer, Client> {

    private Connection conn;

    public ClientsRepository() {
        this.conn = CustomConnection.getConnection();
    }

    public ClientsRepository(Connection conn) {
        this.conn = conn;
    }

    public Client create(Client client) {
        // clients.add(client);

        return client;
    }

    public ArrayList<Client> findAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM clients");
            sql.append(" INNER JOIN users ON clients.id = users.id");

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            ArrayList<Client> clients = new ArrayList<>();

            while (result.next()) {
                clients.add(compile(result));
            }

            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }

        return null;
    }

    public Client findById(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM clients");
            sql.append(" INNER JOIN users ON clients.id = users.id WHERE id = " + id);

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

    public Optional<Client> findOne(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM clients");
            sql.append(" INNER JOIN users ON clients.id = users.id WHERE id = " + id);

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

    public void update(Integer id, Client client) {
        try {
            Client clientSelected = findById(id);

            if (clientSelected != null) {
                PreparedStatement stmt = conn
                        .prepareStatement("UPDATE clients SET cpf = ?, phone = ?, address = ? WHERE id = ?");

                stmt.setString(1, client.getName());
                stmt.setString(2, client.getPhone());
                stmt.setString(3, client.getAddress());
                stmt.setInt(4, id);

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
            Client clientSelected = findById(id);

            if (clientSelected != null) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM clients WHERE id = " + id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomConnection.closeConnection();
        }
    }

    protected static Client compile(ResultSet result) {
        try {
            Client client = new Client();

            client.setId(result.getInt("id"));
            client.setName(result.getString("name"));
            client.setPhone(result.getString("phone"));
            client.setAddress(result.getString("address"));
            client.setCpf(result.getString("cpf"));

            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
