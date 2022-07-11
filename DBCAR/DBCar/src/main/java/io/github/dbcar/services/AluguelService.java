package io.github.dbcar.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.models.Carro;
import io.github.dbcar.models.Cliente;
import io.github.dbcar.models.Aluguel;
import io.github.dbcar.repositories.CarroRepository;
import io.github.dbcar.repositories.AluguelRepository;

public class AluguelService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private AluguelRepository rentsRepository;
    private CarroRepository carsRepository;

    public AluguelService() throws SQLException {
        this.rentsRepository = new AluguelRepository();
        this.carsRepository = new CarroRepository();
    }

    public AluguelService(AluguelRepository rentsRepository, CarroRepository carsRepository) {
        this.rentsRepository = rentsRepository;
        this.carsRepository = carsRepository;
    }

    public Aluguel create(Carro carro, Cliente cliente, LocalDate dataDoAluguel, LocalDate dataDaDevolucao) {
        if (carro != null) {
            if (carro.getAlugado().equals("S")) {
                LOGGER.outLine("CARRO NÃO DISPONIVEL PARA ALUGUEL");
            } else {
                carro.setAlugado("S");
                carsRepository.update(carro.getIdCarro(), carro);

                Aluguel aluguel = new Aluguel();
                aluguel.setCarro(carro);
                aluguel.setCliente(cliente);
                aluguel.setDataDoAluguel(dataDoAluguel);
                aluguel.setDataDaDevolucao(dataDaDevolucao);

                LOGGER.reset();
                LOGGER.outLine("CARRO ALUGADO COM SUCESSO");
                LOGGER.outLine();

                return rentsRepository.create(aluguel);
            }
            LOGGER.outBar();
        }
        return null;
    }

    public ArrayList<Aluguel> findAll() {
        return this.rentsRepository.findAll();
    }

    public Aluguel findById(Integer id) {
        Optional<Aluguel> optionalRent = this.rentsRepository.findOne(id);

        if (optionalRent.isPresent()) {
            LOGGER.outLine("ALUGUEL LOCALIZADO");
            LOGGER.out(optionalRent.get() + "\n\n");
            return optionalRent.get();
        }

        LOGGER.outLine("ALUGUEL NÃO LOCALIZADO");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer id, Aluguel rent) {
        try {
            this.rentsRepository.update(id, rent);
            LOGGER.reset();
            LOGGER.outLine("ALUGUEL ATUALIZADO COM SUCESSO");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("ALUGUEL NÃO ATUALIZADO");
            LOGGER.outLine();
        }
    }

    public void delete(Integer id) {
        try {
            this.rentsRepository.delete(id);
            LOGGER.reset();
            LOGGER.outLine("ALUGUEL EXCLUIDO COM SUCESSO");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("ALUGUEL NÃO ATUALIZADO");
            LOGGER.outLine();
        }
    }

    public void printRents() {
        List<Aluguel> rents = this.rentsRepository.findAll();

        List<Object> collect = IntStream.range(0, rents.size())
                .mapToObj((index) -> index < rents.size() - 1 ? rents.get(index) + "," : rents.get(index))
                .toList();

        if (!collect.isEmpty()) {
            LOGGER.reset();
            LOGGER.outLine("ALUGUEIS NO SISTEMA");
            LOGGER.out("[\n");
            collect.forEach(System.out::println);
            LOGGER.out("]\n");
            LOGGER.outBar();
        } else {
            LOGGER.reset();
            LOGGER.outLine("NENHUM ALUGUEL NO SISTEMA");
            LOGGER.outLine();
        }
    }

    public void printActiveRents() {
        List<Aluguel> rents = this.rentsRepository.findAll().stream()
                .filter((rent) -> rent.getDataDaDevolucao().isAfter(LocalDate.now())).toList();

        List<Object> collect = IntStream.range(0, rents.size())
                .mapToObj((index) -> index < rents.size() - 1 ? rents.get(index) + "," : rents.get(index))
                .toList();

        if (!collect.isEmpty()) {
            LOGGER.reset();
            LOGGER.outLine("ALUGUEIS NO SISTEMA");
            LOGGER.out("[\n");
            collect.forEach(System.out::println);
            LOGGER.out("]\n");
            LOGGER.outBar();
        } else {
            LOGGER.reset();
            LOGGER.outLine("NENHUM ALUGUEL NO SISTEMA");
            LOGGER.outLine();
        }
    }
}
