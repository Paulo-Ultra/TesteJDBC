package io.github.dbcar.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.models.Cliente;
import io.github.dbcar.repositories.ClienteRepository;

public class ClienteService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private ClienteRepository clientsRepository;

    public ClienteService() throws SQLException {
        this.clientsRepository = new ClienteRepository();
    }

    public ClienteService(ClienteRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Cliente create(Cliente client) {
        Cliente createdClient = this.clientsRepository.create(client);

        LOGGER.reset();
        LOGGER.outLine("CLIENTE CADASTRADO COM SUCESSO");
        LOGGER.outLine();

        return createdClient;
    }

    public ArrayList<Cliente> findAll() {
        return this.clientsRepository.findAll();
    }

    public Cliente findById(Integer id) {
        Optional<Cliente> optionalClient = this.clientsRepository.findOne(id);

        if (optionalClient.isPresent()) {
            LOGGER.outLine("CLIENTE LOCALIZADO");
            LOGGER.out(optionalClient.get() + "\n\n");
            return optionalClient.get();
        }

        LOGGER.outLine("CLIENTE NÃO LOCALIZADO");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer id, Cliente client) {
        try {
            this.clientsRepository.update(id, client);
            LOGGER.reset();
            LOGGER.outLine("CLIENTE ATUALIZADO COM SUCESSO");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("CLIENTE NÃO ATUALIZADO");
            LOGGER.outLine();
        }
    }

    public void delete(Integer id) {
        try {
            this.clientsRepository.delete(id);
            LOGGER.reset();
            LOGGER.outLine("CLIENTE EXCLUIDO COM SUCESSO");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("CLIENTE NÃO ATUALIZADO");
            LOGGER.outLine();
        }
    }

    public void printClients() {
        LOGGER.outLine("CLIENTES NO SISTEMA");

        this.clientsRepository.findAll().stream()
                .forEach(System.out::println);

        LOGGER.outBar();
    }
}
