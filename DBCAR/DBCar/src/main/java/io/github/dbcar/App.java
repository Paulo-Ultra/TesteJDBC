package io.github.dbcar;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.models.Carro;
import io.github.dbcar.models.Cliente;
import io.github.dbcar.services.CarroService;
import io.github.dbcar.services.ClienteService;
import io.github.dbcar.services.AluguelService;

public class App {

    private static final Double MIN_VALUE = Double.MIN_VALUE;

    public static void main(String[] args) throws SQLException {
        Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);

        Scanner scanner = new Scanner(System.in);

        CarroService carsService = new CarroService();

        ClienteService clientsService = new ClienteService();

        AluguelService aluguelService = new AluguelService();

        Integer option = 0;

        while (option != 9) {
            LOGGER.out("SEJA BEM VINDO A LOCADORA DBCAR!\n\n");
            LOGGER.outLine("ESCOLHA O PERFIL");

            LOGGER.out("1 - ACESSO FUNCIONÁRIO;\n2 - ACESSO CLIENTE;\n9 - FINALIZAR;\n");
            LOGGER.out("SUA ESCOLHA: ");

            option = Integer.parseInt(scanner.nextLine());

            LOGGER.outBar();

            switch (option) {
                case 1 -> {
                    LOGGER.outLoading("VOCÊ ESTÁ ACESSANDO O MENU PARA FUNCIONÁRIOS");

                    while (option != 3) {
                        LOGGER.outLine("MENU FUNCIONÁRIO");

                        LOGGER.out(
                                "1 - LISTAR OS CARROS DO CATÁLOGO;\n2 - ADICIONAR UM NOVO CARRO AO CATÁLOGO;\n3 - ATUALIZAR UM CARRO DO CATÁLOGO;\n4 - REMOVER CARRO DO CATÁLOGO;\n5 - LISTAR TODOS ALUGUÉIS;\n6 - LISTAR ALUGUÉIS ATIVOS;\n0 - SAIR;\n");
                        LOGGER.out("SUA ESCOLHA: ");

                        option = Integer.parseInt(scanner.nextLine());

                        LOGGER.outBar();

                        switch (option) {
                            case 1 -> {
                                carsService.printCars();
                                break;
                            }
                            case 2 -> {
                                Carro carro = new Carro();

                                LOGGER.out("Carro está disponível? (S/N): ");
                                carro.setAlugado(scanner.nextLine());
                                LOGGER.out("Informe o nome do carro: ");
                                carro.setNome(scanner.nextLine());
                                LOGGER.out("Informe a marca do carro: ");
                                carro.setMarca(scanner.nextLine());
                                LOGGER.out("Informe a classe do carro: ");
                                carro.setClasse(scanner.nextLine());
                                LOGGER.out("Informe a quantidade de passageiros que o carro suporta: ");
                                carro.setQntPassageiros(Integer.parseInt(scanner.nextLine()));
                                LOGGER.out("Informe a quantidade de km rodados pelo carro: ");
                                carro.setKmRodados(Integer.parseInt(scanner.nextLine()));
                                LOGGER.out("Informe o preço da diária do carro R$: ");
                                carro.setPrecoDiaria(scanner.nextDouble());

                                carsService.create(carro);
                                break;
                            }
                            case 3 -> {
                                carsService.printCars();

                                LOGGER.out("Informe o ID do carro que deseja atualizar: ");
                                int carId = Integer.parseInt(scanner.nextLine());

                                String s = "";
                                Double d = MIN_VALUE;

                                Carro carro = carsService.findById(carId);

                                if (carro != null) {
                                    LOGGER.out("Informe o nome do carro (" + carro.getNome() + "): ");
                                    s = scanner.nextLine();
                                    carro.setNome(!s.equals("") ? s : carro.getNome());
                                    LOGGER.out("Informe a marca do carro (" + carro.getMarca() + "): ");
                                    s = scanner.nextLine();
                                    carro.setMarca(!s.equals("") ? s : carro.getMarca());
                                    LOGGER.out("Informe a classe do carro (" + carro.getClasse() + "): ");
                                    s = scanner.nextLine();
                                    carro.setClasse(!s.equals("") ? s : carro.getClasse());
                                    LOGGER.out("Informe a quantidade de passageiros que o carro suporta ("
                                            + carro.getQntPassageiros() + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    carro.setQntPassageiros(!d.equals(MIN_VALUE) ? d.intValue() : carro.getQntPassageiros());
                                    LOGGER.out("Informe a quantidade de km rodados pelo carro (" + carro.getKmRodados()
                                            + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    carro.setKmRodados(!d.equals(MIN_VALUE) ? d.intValue() : carro.getKmRodados());
                                    LOGGER.out("Informe o preço da diária do carro R$ (" + carro.getPrecoDiaria() + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    carro.setPrecoDiaria((!d.equals(MIN_VALUE) ? d : carro.getPrecoDiaria()));

                                    carsService.update(carId, carro);
                                }
                                break;
                            }
                            case 4 -> {
                                carsService.printCars();

                                LOGGER.out("Informe o ID do carro que deseja remover: ");
                                int carId = Integer.parseInt(scanner.nextLine());

                                carsService.delete(carId);
                                break;
                            }
                            case 5 -> {
                                aluguelService.printRents();
                                break;
                            }
                            case 6 -> {
                                aluguelService.printActiveRents();
                                break;
                            }
                            case 0 -> {
                                LOGGER.outLoading("VOCÊ ESTÁ VOLTANDO A ESCOLHA DE PERFIL");
                                option = 3;
                                break;
                            }
                            default -> {
                                LOGGER.out("OPÇÃO INVÁLIDA, TENTE NOVAMENTE\n");
                                break;
                            }
                        }
                    }
                }
                case 2 -> {
                    LOGGER.outLoading("VOCÊ ESTÁ ACESSANDO O MENU PARA CLIENTES");

                    while (option != 3) {
                        LOGGER.outLine("MENU CLIENTE");

                        LOGGER.out(
                                "1 - LISTAR OS CARROS DISPONÍVEIS DO CATÁLOGO;\n2 - ALUGAR UM CARRO;\n3 - DEVOLVER CARRO;\n0 - SAIR;\n");
                        LOGGER.out("SUA ESCOLHA: ");

                        option = Integer.parseInt(scanner.nextLine());

                        LOGGER.outBar();

                        switch (option) {
                            case 1 -> {
                                carsService.printAvailabreCars();
                                break;
                            }
                            case 2 -> {
                                carsService.printAvailabreCars();

                                LOGGER.out("Informe o ID do carro que deseja alugar: ");
                                int carId = Integer.parseInt(scanner.nextLine());

                                Carro car = carsService.findById(carId);

                                if (car != null) {
                                    LOGGER.out("Informe seu ID de Cliente: ");
                                    int clientId = Integer.parseInt(scanner.nextLine());

                                    Cliente client = clientsService.findById(clientId);

                                    LocalDate startDate = LocalDate.now();
                                    if (client == null) {
                                        client = new Cliente();

                                        LOGGER.out("Informe seu nome: ");
                                        client.setNome(scanner.nextLine());
                                        LOGGER.out("Informe seu CPF: ");
                                        client.setCpf(scanner.nextLine());
                                        LOGGER.out("Informe um telefone para contato: ");
                                        client.setTelefone(scanner.nextLine());
                                        LOGGER.out("Informe um endereço: ");
                                        client.setEndereco(scanner.nextLine());

                                        client = clientsService.create(client);
                                    }

                                    LOGGER.out("Informe a quantidade de dias: ");
                                    int days = Integer.parseInt(scanner.nextLine());

                                    aluguelService.create(car, client, startDate, startDate.plusDays(days));
                                }
                                break;
                            }
                            case 0 -> {
                                LOGGER.outLoading("VOCÊ ESTÁ VOLTANDO A ESCOLHA DE PERFIL");
                                option = 3;
                                break;
                            }
                            default -> {
                                LOGGER.out("OPÇÃO INVÁLIDA, TENTE NOVAMENTE\n");
                                break;
                            }
                        }
                    }
                }
            }
        }

        LOGGER.outLine("OBRIGADO POR UTILIZAR O SISTEMA");
        scanner.close();
    }
}
