package modelos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import static modelos.Extracao.extractNumber;

public class ConversaoMoeda {

    public static double getValorMoedaFromJson(String jsonString, String moeda) {
        double valor = 0.0;

        try (BufferedReader reader = new BufferedReader(new StringReader(jsonString))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Verifica se a linha contém a string correspondente à moeda
                if (line.contains(moeda)) {
                    if (line.contains("base_code")){
                        boolean ignoraLinha = true;
                    }else {
                        String informacao = line.toString();
//                    System.out.println(informacao);
                        valor = extractNumber(line.toString());
                        return valor; // Retorna a linha encontrada, removendo espaços em branco extras
                    }
                }
            }
        } catch (IOException e) {
            // Em caso de erro de leitura (improvável com StringReader, mas bom para robustez)
            e.printStackTrace();
        }
        return 0.0; // Retorna null se "BRL" não for encontrado em nenhuma linha
    }

    public static double getValorConvertido(double cotacaoOrigem, double cotacaoDestino, double valor) {

        double valorCalculo = 0.0;

        valorCalculo = (valor / cotacaoOrigem);
        valorCalculo = (valorCalculo * cotacaoDestino);

        return valorCalculo;
    }
}