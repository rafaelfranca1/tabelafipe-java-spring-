package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.service.ConsumoAPI;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        var menu = """
            *** OPÇÕES ***
            Carro
            Moto
            Caminhão
            
            Digite uma das opções para consultas:\s""";

        System.out.print(menu);
        var veiculoTipo = scanner.nextLine();
        String endereco;

        if (veiculoTipo.toLowerCase().contains("carr")) {
            endereco = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
        } else if (veiculoTipo.toLowerCase().contains("mot")) {
            endereco = "https://parallelum.com.br/fipe/api/v1/motos/marcas";
        } else {
            endereco = "https://parallelum.com.br/fipe/api/v1/caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.print("\nInforme o código da marca para consulta: ");
        var codigoMarca = scanner.nextLine();

        endereco += "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        System.out.println("\nModelos dessa marca: ");
        var modelosLista = conversor.obterDados(json, Modelos.class);
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.print("\nDigite um trecho do nome do carro a ser buscado: ");
        var trechoModelo = scanner.nextLine();
        System.out.println("\nModelos filtrados: ");
        modelosLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoModelo))
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.print("\nDigite por favor o código do modelo para buscar os valores de avaliação: ");
        var codigoModelo = scanner.nextLine();

        endereco += "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        var anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (Dados ano : anos) {
            String enderecoVeiculo = endereco + '/' + ano.codigo();
            json = consumo.obterDados(enderecoVeiculo);
            var veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}
