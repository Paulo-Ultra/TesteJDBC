package io.github.dbcar.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.models.Car;
import io.github.dbcar.models.Client;
import io.github.dbcar.models.Rent;
import io.github.dbcar.repositories.CarsRepository;
import io.github.dbcar.repositories.RentsRepository;

public class RentsService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private RentsRepository rentsRepository;
    private CarsRepository carsRepository;

    public RentsService() {
        this.rentsRepository = new RentsRepository();
        this.carsRepository = new CarsRepository();
    }

    public RentsService(RentsRepository rentsRepository, CarsRepository carsRepository) {
        this.rentsRepository = rentsRepository;
        this.carsRepository = carsRepository;
    }

    public Rent create(Car car, Client client, LocalDate startDate, LocalDate endDate) {
        if (car != null) {
            if (car.getRented().equals("S")) {
                LOGGER.outLine("CARRO NÃO DISPONIVEL PARA ALUGUEL");
            } else {
                car.setRented("S");
                carsRepository.update(car.getId(), car);

                Rent rent = new Rent();
                rent.setCar(car);
                rent.setClient(client);
                rent.setStartOfRent(startDate);
                rent.setEndOfRent(endDate);

                LOGGER.reset();
                LOGGER.outLine("CARRO ALUGADO COM SUCESSO");
                LOGGER.outLine();

                return rentsRepository.create(rent);
            }
            LOGGER.outBar();
        }
        return null;
    }

    public ArrayList<Rent> findAll() {
        return this.rentsRepository.findAll();
    }

    public Rent findById(Integer id) {
        Optional<Rent> optionalRent = this.rentsRepository.findOne(id);

        if (optionalRent.isPresent()) {
            LOGGER.outLine("ALUGUEL LOCALIZADO");
            LOGGER.out(optionalRent.get() + "\n\n");
            return optionalRent.get();
        }

        LOGGER.outLine("ALUGUEL NÃO LOCALIZADO");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer id, Rent rent) {
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
        List<Rent> rents = this.rentsRepository.findAll();

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
        List<Rent> rents = this.rentsRepository.findAll().stream()
                .filter((rent) -> rent.getEndOfRent().isAfter(LocalDate.now())).toList();

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
