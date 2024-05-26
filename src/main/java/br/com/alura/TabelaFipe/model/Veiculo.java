package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(String Marca, String Modelo, String AnoModelo, String Valor, String Combustivel) {
    @Override
    public String toString() {
        return Marca + ' ' + Modelo +
                " Ano: " + AnoModelo +
                " Valor: " + Valor +
                " Combustivel: " + Combustivel;
    }
}
