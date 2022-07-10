package io.github.dbcar.service;

import java.util.ArrayList;
import java.util.Optional;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.model.Cliente;
import io.github.dbcar.repository.ClienteRepository;

public class ClienteService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente create(Cliente cliente) {
        Cliente clienteCriado = this.clienteRepository.create(cliente);

        LOGGER.reset();
        LOGGER.outLine("CLIENTE CADASTRADO COM SUCESSO");
        LOGGER.outLine();

        return clienteCriado;
    }

    public ArrayList<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Cliente findById(Integer idCliente) {
        Optional<Cliente> clienteOptional = this.clienteRepository.findOne(idCliente);

        if (clienteOptional.isPresent()) {
            LOGGER.outLine("CLIENTE ENCONTRADO!");
            LOGGER.out(clienteOptional.get() + "\n\n");
            return clienteOptional.get();
        }

        LOGGER.outLine("CLIENTE NÃO ENCONTRADO.");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer idCliente, Cliente cliente) {
        try {
            this.clienteRepository.update(idCliente, cliente);
            LOGGER.reset();
            LOGGER.outLine("CLIENTE ATUALIZADO COM SUCESSO!");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("NÃO FOI POSSÍVEL ATUALIZAR O CLIENTE.");
            LOGGER.outLine();
        }
    }

    public void delete(Integer idCliente) {
        try {
            this.clienteRepository.delete(idCliente);
            LOGGER.reset();
            LOGGER.outLine("CLIENTE EXCLUÍDO COM SUCESSO!");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("NÃO FOI POSSÍVEL EXCLUIR O CLIENTE.");
            LOGGER.outLine();
        }
    }

    public void printClientes() {
        LOGGER.outLine("CLIENTES NO SISTEMA");

        this.clienteRepository.findAll().stream()
                .forEach(System.out::println);

        LOGGER.outBar();
    }
}
