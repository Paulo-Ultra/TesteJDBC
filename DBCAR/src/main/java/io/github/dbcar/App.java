package io.github.dbcar;

import java.time.LocalDate;
import java.util.Scanner;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.models.Car;
import io.github.dbcar.models.Client;
import io.github.dbcar.services.CarsService;
import io.github.dbcar.services.ClientsService;
import io.github.dbcar.services.RentsService;

public class App {

    private static final Double MIN_VALUE = Double.MIN_VALUE;

    public static void main(String[] args) {
        Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);

        Scanner scanner = new Scanner(System.in);

        CarsService carsService = new CarsService();

        ClientsService clientsService = new ClientsService();

        RentsService rentsService = new RentsService();

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
                                Car car = new Car();

                                LOGGER.out("Carro está disponível? (S/N): ");
                                car.setRented(scanner.nextLine());
                                LOGGER.out("Informe o nome do carro: ");
                                car.setName(scanner.nextLine());
                                LOGGER.out("Informe a marca do carro: ");
                                car.setBrand(scanner.nextLine());
                                LOGGER.out("Informe a classe do carro: ");
                                car.setGroup(scanner.nextLine());
                                LOGGER.out("Informe a quantidade de passageiros que o carro suporta: ");
                                car.setPassengers(Integer.parseInt(scanner.nextLine()));
                                LOGGER.out("Informe a quantidade de km rodados pelo carro: ");
                                car.setKmDriven(Integer.parseInt(scanner.nextLine()));
                                LOGGER.out("Informe o preço da diária do carro R$: ");
                                car.setDailyPrice(scanner.nextDouble());

                                carsService.create(car);
                                break;
                            }
                            case 3 -> {
                                carsService.printCars();

                                LOGGER.out("Informe o ID do carro que deseja atualizar: ");
                                int carId = Integer.parseInt(scanner.nextLine());

                                String s = "";
                                Double d = MIN_VALUE;

                                Car car = carsService.findById(carId);

                                if (car != null) {
                                    LOGGER.out("Informe o nome do carro (" + car.getName() + "): ");
                                    s = scanner.nextLine();
                                    car.setName(!s.equals("") ? s : car.getName());
                                    LOGGER.out("Informe a marca do carro (" + car.getBrand() + "): ");
                                    s = scanner.nextLine();
                                    car.setBrand(!s.equals("") ? s : car.getBrand());
                                    LOGGER.out("Informe a classe do carro (" + car.getGroup() + "): ");
                                    s = scanner.nextLine();
                                    car.setGroup(!s.equals("") ? s : car.getGroup());
                                    LOGGER.out("Informe a quantidade de passageiros que o carro suporta ("
                                            + car.getPassengers() + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    car.setPassengers(!d.equals(MIN_VALUE) ? d.intValue() : car.getPassengers());
                                    LOGGER.out("Informe a quantidade de km rodados pelo carro (" + car.getKmDriven()
                                            + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    car.setKmDriven(!d.equals(MIN_VALUE) ? d.intValue() : car.getKmDriven());
                                    LOGGER.out("Informe o preço da diária do carro R$ (" + car.getDailyPrice() + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    car.setDailyPrice(!d.equals(MIN_VALUE) ? d : car.getDailyPrice());

                                    carsService.update(carId, car);
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
                                rentsService.printRents();
                                break;
                            }
                            case 6 -> {
                                rentsService.printActiveRents();
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

                                Car car = carsService.findById(carId);

                                if (car != null) {
                                    LOGGER.out("Informe seu ID de Cliente: ");
                                    int clientId = Integer.parseInt(scanner.nextLine());

                                    Client client = clientsService.findById(clientId);

                                    LocalDate startDate = LocalDate.now();
                                    if (client == null) {
                                        client = new Client();

                                        LOGGER.out("Informe seu nome: ");
                                        client.setName(scanner.nextLine());
                                        LOGGER.out("Informe seu CPF: ");
                                        client.setCpf(scanner.nextLine());
                                        LOGGER.out("Informe um telefone para contato: ");
                                        client.setPhone(scanner.nextLine());
                                        LOGGER.out("Informe um endereço: ");
                                        client.setAddress(scanner.nextLine());

                                        client = clientsService.create(client);
                                    }

                                    LOGGER.out("Informe a quantidade de dias: ");
                                    int days = Integer.parseInt(scanner.nextLine());

                                    rentsService.create(car, client, startDate, startDate.plusDays(days));
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
