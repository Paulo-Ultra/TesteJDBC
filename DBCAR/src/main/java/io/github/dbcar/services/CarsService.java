package io.github.dbcar.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.models.Car;
import io.github.dbcar.repositories.CarsRepository;

public class CarsService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private CarsRepository carsRepository;

    public CarsService() {
        this.carsRepository = new CarsRepository();
    }

    public CarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public Car create(Car car) {
        Car createdCar = this.carsRepository.create(car);

        LOGGER.reset();
        LOGGER.outLine("CARRO CADASTRADO COM SUCESSO");
        LOGGER.outLine();

        return createdCar;
    }

    public ArrayList<Car> findAll() {
        return this.carsRepository.findAll();
    }

    public Car findById(Integer id) {
        Optional<Car> optionalCar = this.carsRepository.findOne(id);

        if (optionalCar.isPresent()) {
            LOGGER.outLine("CARRO LOCALIZADO");
            LOGGER.out(optionalCar.get() + "\n\n");
            return optionalCar.get();
        }

        LOGGER.outLine("CARRO NÃO LOCALIZADO");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer id, Car car) {
        try {
            this.carsRepository.update(id, car);
            LOGGER.reset();
            LOGGER.outLine("CARRO ATUALIZADO COM SUCESSO");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("CARRO NÃO ATUALIZADO");
            LOGGER.outLine();
        }
    }

    public void delete(Integer id) {
        Car car = findById(id);
        if (car.getRented().equals("S")) {
            LOGGER.outLine("CARRO ESTÁ ALUGADO NÃO É POSSÍVEL EXCLUIR");
            LOGGER.outLine();
        } else {
            try {
                this.carsRepository.delete(id);
                LOGGER.reset();
                LOGGER.outLine("CARRO EXCLUIDO COM SUCESSO");
                LOGGER.outLine();
            } catch (Exception e) {
                LOGGER.outLine("CARRO NÃO EXCLUIDO");
                LOGGER.outLine();
            }
        }
    }

    public void printCars() {
        List<Car> cars = this.carsRepository.findAll();

        List<Object> collect = IntStream.range(0, cars.size())
                .mapToObj((index) -> index < cars.size() - 1 ? cars.get(index) + "," : cars.get(index))
                .toList();

        if (!collect.isEmpty()) {
            LOGGER.reset();
            LOGGER.outLine("CARROS NO CATÁLOGO");
            LOGGER.out("[\n");
            collect.forEach(System.out::println);
            LOGGER.out("]\n");
            LOGGER.outBar();
        } else {
            LOGGER.reset();
            LOGGER.outLine("NENHUM CARRO NO CATÁLOGO");
            LOGGER.outLine();
        }
    }

    public void printAvailabreCars() {
        List<Car> cars = this.carsRepository.findAll().stream()
                .filter((cart) -> cart.getRented().equals("N")).toList();

        List<Object> collect = IntStream.range(0, cars.size())
                .mapToObj((index) -> index < cars.size() - 1 ? cars.get(index) + "," : cars.get(index))
                .toList();

        if (!collect.isEmpty()) {
            LOGGER.reset();
            LOGGER.outLine("CARROS DISPONIVEIS PARA ALUGUEL");
            LOGGER.out("[\n");
            collect.forEach(System.out::println);
            LOGGER.out("]\n");
            LOGGER.outBar();
        } else {
            LOGGER.reset();
            LOGGER.outLine("NENHUM CARRO DISPONIVEL NO SISTEMA");
            LOGGER.outLine();
        }
    }
}
