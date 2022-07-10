package io.github.dbcar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.model.Carro;
import io.github.dbcar.model.Cliente;
import io.github.dbcar.model.Aluguel;
import io.github.dbcar.repository.AluguelRepository;
import io.github.dbcar.repository.CarroRepository;

public class AluguelService {

    private final Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);
    private AluguelRepository aluguelRepository;
    private CarroRepository carroRepository;

    public AluguelService() {
        this.aluguelRepository = new AluguelRepository();
        this.carroRepository = new CarroRepository();
    }

    public AluguelService(AluguelRepository aluguelRepository, CarroRepository carroRepository) {
        this.aluguelRepository = aluguelRepository;
        this.carroRepository = carroRepository;
    }

    public Aluguel create(Carro carro, Cliente cliente, LocalDate dataInicial, LocalDate dataFinal) {
        if (carro != null) {
            if (carro.getAlugado().equals("S")) {
                LOGGER.outLine("CARRO NÃO DISPONIVEL PARA ALUGUEL");
            } else {
                carro.setAlugado("S");
                carroRepository.update(carro.getIdCarro(), carro);

                Aluguel aluguel = new Aluguel();
                aluguel.setCarro(carro);
                aluguel.setCliente(cliente);
                aluguel.setDiaDoAluguel(dataInicial);
                aluguel.setDiaDaEntrega(dataFinal);

                LOGGER.reset();
                LOGGER.outLine("CARRO ALUGADO COM SUCESSO");
                LOGGER.outLine();

                return aluguelRepository.create(aluguel);
            }
            LOGGER.outBar();
        }
        return null;
    }

    public ArrayList<Aluguel> findAll() {
        return this.aluguelRepository.findAll();
    }

    public Aluguel findById(Integer idAluguel) {
        Optional<Aluguel> aluguelOptional = this.aluguelRepository.findOne(idAluguel);

        if (aluguelOptional.isPresent()) {
            LOGGER.outLine("ALUGUEL ENCONTRADO!");
            LOGGER.out(aluguelOptional.get() + "\n\n");
            return aluguelOptional.get();
        }

        LOGGER.outLine("ALUGUEL NÃO ENCONTRADO.");
        LOGGER.outLine();
        return null;
    }

    public void update(Integer idAluguel, Aluguel aluguel) {
        try {
            this.aluguelRepository.update(idAluguel, aluguel);
            LOGGER.reset();
            LOGGER.outLine("ALUGUEL ATUALIZADO COM SUCESSO!");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("NÃO FOI POSSÍVEL ATUALIZAR O ALUGUEL.");
            LOGGER.outLine();
        }
    }

    public void delete(Integer idAluguel) {
        try {
            this.aluguelRepository.delete(idAluguel);
            LOGGER.reset();
            LOGGER.outLine("ALUGUEL EXCLUÍDO COM SUCESSO!");
            LOGGER.outLine();
        } catch (Exception e) {
            LOGGER.outLine("NÃO FOI POSSÍVEL ATUALIZAR O ALUGUEL.");
            LOGGER.outLine();
        }
    }

    public void printAlugueis() {
        List<Aluguel> alugueis = this.aluguelRepository.findAll();

        List<Object> collect = IntStream.range(0, alugueis.size())
                .mapToObj((index) -> index < alugueis.size() - 1 ? alugueis.get(index) + "," : alugueis.get(index))
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

    public void printAlugueisAtivos() {
        List<Aluguel> alugueis = this.aluguelRepository.findAll().stream()
                .filter((aluguel) -> aluguel.getDiaDaEntrega().isAfter(LocalDate.now())).toList();

        List<Object> collect = IntStream.range(0, alugueis.size())
                .mapToObj((index) -> index < alugueis.size() - 1 ? alugueis.get(index) + "," : alugueis.get(index))
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
