package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.DadosMarca;
import br.com.alura.TabelaFipe.service.ConsumoAPI;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        var menu = """
            *** OPÇÕES ***
            Carro
            Moto
            Caminhão
            
            Digite uma das opções para consulta: """;

        System.out.print(menu);
        var veiculo = scanner.nextLine();
        String endereco;

        if (veiculo.toLowerCase().contains("carr")) {
            endereco = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
        } else if (veiculo.toLowerCase().contains("mot")) {
            endereco = "https://parallelum.com.br/fipe/api/v1/motos/marcas";
        } else {
            endereco = "https://parallelum.com.br/fipe/api/v1/caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        List<DadosMarca> marcas = conversor.obterLista(json, DadosMarca.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosMarca::codigo))
                .forEach(System.out::println);

        System.out.print("\ndigite o código da marca desejada: ");
        var codigoMarca = scanner.nextLine();

        String enderecoModelos = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(enderecoModelos);
        System.out.println(json);

        System.out.print("\ndigite o código do modelo desejado: ");
        var codigoModelo = scanner.nextLine();

        String enderecoAnos = enderecoModelos + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(enderecoAnos);
        System.out.println(json);

    }
}
