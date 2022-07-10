package io.github.dbcar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.model.Carro;
import io.github.dbcar.repository.CarroRepository;

public class CarroService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private CarroRepository carroRepository;

    public CarroService() {
        this.carroRepository = new CarroRepository();
    }

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public Carro create(Carro carro) {
        Carro carroCriado = this.carroRepository.create(carro);

        LOGGER.reset();
        LOGGER.outLine("CARRO CADASTRADO COM SUCESSO");
        LOGGER.outLine();

        return carroCriado;
    }

    public ArrayList<Carro> findAll() {
        return this.carroRepository.findAll();
    }

    public Carro findById(Integer idCarro) {
        Optional<Carro> carroOptional = this.carroRepository.findOne(idCarro);

        if (carroOptional.isPresent()) {
            LOGGER.outLine("CARRO ENCONTRADO");
            LOGGER.out(carroOptional.get() + "\n\n");
            return carroOptional.get();
        }

        LOGGER.outLine("CARRO NÃO ENCONTRADO");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer idCarro, Carro carro) {
        try {
            this.carroRepository.update(idCarro, carro);
            LOGGER.reset();
            LOGGER.outLine("CARRO ATUALIZADO COM SUCESSO!");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("NÃO FOI POSSÍVEL ATUALIZAR O CARRO.");
            LOGGER.outLine();
        }
    }

    public void delete(Integer idCarro) {
        Carro carro = findById(idCarro);
        if (carro.getAlugado().equals("S")) {
            LOGGER.outLine("NÃO É POSSÍVEL EXCLUIR UM CARRO QUE ESTÁ ALUGADO!");
            LOGGER.outLine();
        } else {
            try {
                this.carroRepository.delete(idCarro);
                LOGGER.reset();
                LOGGER.outLine("CARRO EXCLUIDO COM SUCESSO!");
                LOGGER.outLine();
            } catch (Exception e) {
                LOGGER.outLine("NÃO FOI POSSÍVEL EXCLUIR O CARRO!");
                LOGGER.outLine();
            }
        }
    }

    public void printCarros() {
        List<Carro> carros = this.carroRepository.findAll();

        List<Object> collect = IntStream.range(0, carros.size())
                .mapToObj((index) -> index < carros.size() - 1 ? carros.get(index) + "," : carros.get(index))
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

    public void printCarrosAtivos() {
        List<Carro> carros = this.carroRepository.findAll().stream()
                .filter((carro) -> carro.getAlugado().equals("N")).toList();

        List<Object> collect = IntStream.range(0, carros.size())
                .mapToObj((index) -> index < carros.size() - 1 ? carros.get(index) + "," : carros.get(index))
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
