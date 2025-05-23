package modelos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extracao {
    public static double extractNumber(String text) {
        // Padrão para encontrar um número decimal (com ou sem sinal, com ou sem parte decimal)
        Pattern pattern = Pattern.compile("[-+]?\\d*\\.?\\d+");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group());
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter o número: " + matcher.group());
                return 0.0; // Ou lance uma exceção, dependendo do seu tratamento de erro
            }
        }
        return 0.0; // Nenhum número encontrado, ou lance uma exceção
    }
}
