import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    final static String RESET = "\u001b[m";
    final static String NEGRITO = "\u001b[1m";
    final static String FUNDO_VERDE = "\u001b[32m";
    final static String SUBLINHADO = "\u001b[4m";

    public static void main(String[] args) throws Exception {

        // Realizar a conexão HTTP
        String url = solicitarURL();
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair ou parsear os dados que precisamos (titulo, poster e rating)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaFilmes = parser.parse(body);

        // Exibir e manipular os dados
        for (Map<String, String> filme : listaFilmes) {
            System.out.println(NEGRITO + FUNDO_VERDE + filme.get("title") + RESET);
            System.out.println(SUBLINHADO + filme.get("image") + RESET);
            System.out.print(NEGRITO + filme.get("imDbRating") + RESET + " - ");
            int nota = Math.round(Float.parseFloat(filme.get("imDbRating")));
            var estrela = "";
            for (int i = 0; i < nota; i++) {
                estrela = estrela + "\u2B50";
            }
            System.out.println(estrela);
            System.out.println();
        }

    }

    private static String solicitarURL() {
        int escolha = 0;
        String url = "";
        while ((escolha < 1) || (escolha > 3)) {
            System.out.print(
                    "Qual lista você deseja?\n1 - Filmes mais populares\n2 - Programas de TV mais populares\nR.:");
            escolha = new Scanner(System.in).nextInt();
            System.out.println(escolha);
            switch (escolha) {
                case 1:
                    System.out.println("\nLista dos filmes mais populares");
                    url = "https://api.mocki.io/v2/549a5d8b/MostPopularMovies";
                    break;
                case 2:
                    System.out.println("\nLista dos programas de TV mais populares");
                    url = "https://api.mocki.io/v2/549a5d8b/MostPopularTVs";
                    break;
                default:
                    System.out.println("\nEscolher 1 ou 2\n");
                    break;
            }
        }
        return url;
    };

}
