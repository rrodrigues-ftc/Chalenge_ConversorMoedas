package Principal;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import modelos.Cotacoes;

import static modelos.ConversaoMoeda.getValorConvertido;
import static modelos.ConversaoMoeda.getValorMoedaFromJson;

public class PrincipalComBusca extends IllegalArgumentException {
    public static void main(String[] args)  throws IOException, InterruptedException {

        String menuOpcoes = """
                    ********************************************************
                    Seja bem-vindo(a) ao Conversor de Moeda =]
                    
                    1) Dólar Americano (USD) =>> Peso Argentino (ARS)
                    2) Peso Argentino (ARS) =>> Dólar Americano (USD)
                    3) Dólar Americano (USD) =>> Real Brasileiro (BRL)
                    4) Real Brasileiro (BRL) =>> Dólar Americano (USD)
                    5) Dólar Americano (USD) =>> Peso Colombiano (COP)
                    6) Peso Colombiano (COP) =>> Dólar Americano (USD)
                    7) Sair
                    Escolha uma opção Válida
                    ********************************************************
                    """;


        Scanner novaCotacao = new Scanner(System.in);
        int busca = 0;
        double valor = 0.0;
        double valorCotacaoOri = 0.0;
        double valorCotacaoDst = 0.0;
        double valorConvertido = 0.0;
        Object retornoConsulta = null;

        //List<Cotacoes> cotacoes = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while(busca != 7){
            System.out.println(menuOpcoes);
            busca = novaCotacao.nextInt();

            if (busca == 7) {
                System.out.println("App encerrado.");
                break;
            }else{
                System.out.println("Informe o valor a ser convertido: ");
                valor = novaCotacao.nextDouble();
            }

            System.out.println("Buscando cotações...");
            String exchangeKey = "_____Informe_Aqui_sua_API-KEY_____";
            String endereco = "https://v6.exchangerate-api.com/v6/" + exchangeKey + "/latest/USD";

            // Para capturar as siglas de todas as moedas, substituir a linha acima por esta abaixo
            //String endereco = "https://v6.exchangerate-api.com/v6/" + exchangeKey + "/codes";

//            System.out.println("Tratando eventuais erros...");
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                retornoConsulta = json;
//                System.out.println(json);
            } catch (NumberFormatException e) {
                System.out.println("+++>>> Aconteceu um erro:");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("+++>>> Erro de argumento na busca:");
                System.out.println(e);
            }

            String codMoedaOri = null;
            String codMoedaDst = null;

            if (busca == 1) {
                codMoedaOri = "USD";
                codMoedaDst = "ARS";
            }else if (busca == 2) {
                codMoedaOri = "ARS";
                codMoedaDst = "USD";
            }else if (busca == 3) {
                codMoedaOri = "USD";
                codMoedaDst = "BRL";
            }else if (busca == 4) {
                codMoedaOri = "BRL";
                codMoedaDst = "USD";
            }else if (busca == 5) {
                codMoedaOri = "USD";
                codMoedaDst = "COP";
            }else if (busca == 6) {
                codMoedaOri = "COP";
                codMoedaDst = "USD";
            }

            valorCotacaoOri = getValorMoedaFromJson(retornoConsulta.toString(), codMoedaOri);
            valorCotacaoDst = getValorMoedaFromJson(retornoConsulta.toString(), codMoedaDst);

            String msg = null;
            if (valorCotacaoOri != -1.0 && valorCotacaoDst != -1.0) { // Verifica se não houve erro
                System.out.printf(String.format("Origem: %s = %.6f", codMoedaOri, valorCotacaoOri));
                System.out.println(' ');
                System.out.printf(String.format("Destino %s = %.6f", codMoedaDst, valorCotacaoDst));
            }

            valorConvertido = getValorConvertido(valorCotacaoOri, valorCotacaoDst, valor);
            System.out.println(' ');
            System.out.println("O valor convertido é: " +  valorConvertido);

        }
    }
}