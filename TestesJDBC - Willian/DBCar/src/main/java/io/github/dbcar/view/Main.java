package io.github.dbcar.view;

import java.time.LocalDate;
import java.util.Scanner;

import io.github.dbcar.common.Constants;
import io.github.dbcar.common.Logger;
import io.github.dbcar.model.Carro;
import io.github.dbcar.model.Cliente;
import io.github.dbcar.service.CarroService;
import io.github.dbcar.service.ClienteService;
import io.github.dbcar.service.AluguelService;

public class Main {

    private static final Double MIN_VALUE = Double.MIN_VALUE;

    public static void main(String[] args) {
        Logger LOGGER = new Logger(Constants.PRINT_STYLE, Constants.PRINT_LENGHT);

        Scanner scanner = new Scanner(System.in);

        CarroService carroService = new CarroService();

        ClienteService clienteService = new ClienteService();

        AluguelService aluguelService = new AluguelService();

        Integer opcao = 0;

        while (opcao != 9) {
            LOGGER.out("SEJA BEM VINDO A LOCADORA DBCAR!\n\n");
            LOGGER.outLine("ESCOLHA O PERFIL");

            LOGGER.out("1 - ACESSO FUNCIONÁRIO;\n2 - ACESSO CLIENTE;\n9 - FINALIZAR;\n");
            LOGGER.out("SUA ESCOLHA: ");

            opcao = Integer.parseInt(scanner.nextLine());

            LOGGER.outBar();

            switch (opcao) {
                case 1 -> {
                    LOGGER.outLoading("VOCÊ ESTÁ ACESSANDO O MENU PARA FUNCIONÁRIOS");

                    while (opcao != 3) {
                        LOGGER.outLine("MENU FUNCIONÁRIO");

                        LOGGER.out(
                                "1 - LISTAR OS CARROS DO CATÁLOGO;\n2 - ADICIONAR UM NOVO CARRO AO CATÁLOGO;\n3 - ATUALIZAR UM CARRO DO CATÁLOGO;\n4 - REMOVER CARRO DO CATÁLOGO;\n5 - LISTAR TODOS ALUGUÉIS;\n6 - LISTAR ALUGUÉIS ATIVOS;\n0 - SAIR;\n");
                        LOGGER.out("SUA ESCOLHA: ");

                        opcao = Integer.parseInt(scanner.nextLine());

                        LOGGER.outBar();

                        switch (opcao) {
                            case 1 -> {
                                carroService.printCarros();
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
                                carro.setQuantidade_passageiros(Integer.parseInt(scanner.nextLine()));
                                LOGGER.out("Informe a quantidade de km rodados pelo carro: ");
                                carro.setKmRodados(Integer.parseInt(scanner.nextLine()));
                                LOGGER.out("Informe o preço da diária do carro R$: ");
                                carro.setPrecoDiaria(scanner.nextDouble());

                                carroService.create(carro);
                                break;
                            }
                            case 3 -> {
                                carroService.printCarros();

                                LOGGER.out("Informe o ID do carro que deseja atualizar: ");
                                int carroId = Integer.parseInt(scanner.nextLine());

                                String s = "";
                                Double d = MIN_VALUE;

                                Carro carro = carroService.findById(carroId);

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
                                            + carro.getQuantidade_passageiros() + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    carro.setQuantidade_passageiros(!d.equals(MIN_VALUE) ? d.intValue() : carro.getQuantidade_passageiros());
                                    LOGGER.out("Informe a quantidade de km rodados pelo carro (" + carro.getKmRodados()
                                            + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    carro.setKmRodados(!d.equals(MIN_VALUE) ? d.intValue() : carro.getKmRodados());
                                    LOGGER.out("Informe o preço da diária do carro R$ (" + carro.getPrecoDiaria() + "): ");
                                    s = scanner.nextLine();
                                    d = !s.equals("") ? Double.parseDouble(s) : MIN_VALUE;
                                    carro.setPrecoDiaria(!d.equals(MIN_VALUE) ? d : carro.getPrecoDiaria());

                                    carroService.update(carroId, carro);
                                }
                                break;
                            }
                            case 4 -> {
                                carroService.printCarros();

                                LOGGER.out("Informe o ID do carro que deseja remover: ");
                                int carroId = Integer.parseInt(scanner.nextLine());

                                carroService.delete(carroId);
                                break;
                            }
                            case 5 -> {
                                aluguelService.printAlugueis();
                                break;
                            }
                            case 6 -> {
                                aluguelService.printAlugueisAtivos();
                                break;
                            }
                            case 0 -> {
                                LOGGER.outLoading("VOCÊ ESTÁ VOLTANDO AO MENU INICIAL...");
                                opcao = 3;
                                break;
                            }
                            default -> {
                                LOGGER.out("OPÇÃO INVÁLIDA, DIGITE UMA OPÇÃO VÁLIDA!\n");
                                break;
                            }
                        }
                    }
                }
                case 2 -> {
                    LOGGER.outLoading("VOCÊ ESTÁ ACESSANDO O MENU PARA CLIENTES");

                    while (opcao != 3) {
                        LOGGER.outLine("MENU CLIENTE");

                        LOGGER.out(
                                "1 - LISTAR OS CARROS DISPONÍVEIS DO CATÁLOGO;\n2 - ALUGAR UM CARRO;\n3 - DEVOLVER CARRO;\n0 - SAIR;\n");
                        LOGGER.out("SUA ESCOLHA: ");

                        opcao = Integer.parseInt(scanner.nextLine());

                        LOGGER.outBar();

                        switch (opcao) {
                            case 1 -> {
                                carroService.printCarrosAtivos();
                                break;
                            }
                            case 2 -> {
                                carroService.printCarrosAtivos();

                                LOGGER.out("Informe o ID do carro que deseja alugar: ");
                                int carroId = Integer.parseInt(scanner.nextLine());

                                Carro carro = carroService.findById(carroId);

                                if (carro != null) {
                                    LOGGER.out("Informe seu ID de Cliente: ");
                                    int clienteId = Integer.parseInt(scanner.nextLine());

                                    Cliente cliente = clienteService.findById(clienteId);

                                    LocalDate dataInicial = LocalDate.now();
                                    if (cliente == null) {
                                        cliente = new Cliente();

                                        LOGGER.out("Informe seu nome: ");
                                        cliente.setNome(scanner.nextLine());
                                        LOGGER.out("Informe seu CPF: ");
                                        cliente.setCpf(scanner.nextLine());
                                        LOGGER.out("Informe um telefone para contato: ");
                                        cliente.setTelefone(scanner.nextLine());
                                        LOGGER.out("Informe um endereço: ");
                                        cliente.setEndereco(scanner.nextLine());

                                        cliente = clienteService.create(cliente);
                                    }

                                    LOGGER.out("Informe a quantidade de dias: ");
                                    int dias = Integer.parseInt(scanner.nextLine());

                                    aluguelService.create(carro, cliente, dataInicial, dataInicial.plusDays(dias));
                                }
                                break;
                            }
                            case 0 -> {
                                LOGGER.outLoading("VOCÊ ESTÁ VOLTANDO AO MENU INICIAL...");
                                opcao = 3;
                                break;
                            }
                            default -> {
                                LOGGER.out("OPÇÃO INVÁLIDA, DIGITE UMA OPÇÃO VÁLIDA!\n");
                                break;
                            }
                        }
                    }
                }
            }
        }
        LOGGER.outLine("OBRIGADO POR UTILIZAR O NOSSO SISTEMA");
        scanner.close();
    }
}
