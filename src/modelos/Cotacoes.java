package modelos;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cotacoes {
    private String codMoeda;
    private int numCodMoeda;
    private double vlrCotacaoMoeda;

    public String newCotacao(String codMoeda, int numCodMoeda, double vlrCotacaoMoeda) {
        return  "Moeda: '" + codMoeda +
                ", Código: " + numCodMoeda +
                ", Cotacao: " + vlrCotacaoMoeda ;
    }
}






