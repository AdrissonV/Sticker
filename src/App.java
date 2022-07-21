import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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
    final static String VERDE = "\u001b[32m";
    final static String SUBLINHADO = "\u001b[4m";
    final static String AMARELO = "\u001b[33m";

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
            String titulo = filme.get("title");
            String imagem = filme.get("image");

            String extensao = imagem.substring(imagem.length() - 4);
            String nomeArquivo = titulo.replace(":", "-") + extensao;

            System.out.println(NEGRITO + VERDE + titulo + RESET);
            System.out.println(SUBLINHADO + imagem + RESET);

            int nota = Math.round(Float.parseFloat(filme.get("imDbRating")));
            var estrela = "";
            for (int i = 0; i < nota; i++) {
                estrela = estrela + "\u2B50";
            }
            System.out.print(estrela);
            System.out.println(AMARELO + " - (" + filme.get("imDbRating") + ")" + RESET);
            System.out.println();

            try {
                InputStream inputStream = new URL(imagem).openStream();
                var geranerator = new StickerGenerator();
                System.out.println("Gerando imagem - [" + titulo + "]\n");
                geranerator.generator(inputStream, nomeArquivo, nota);
            } catch (java.io.FileNotFoundException err) {
                System.out.println("Imagem não encontrada ou link inválido\n");
            }
        }

    }

    private static String solicitarURL() {
        int escolha = 0;
        String url = "";
        while ((escolha < 1) || (escolha > 3)) {
            System.out.print(
                    "Qual lista você deseja?\n1 - Filmes mais populares\n2 - Séries mais populares\nR.:");
            escolha = new Scanner(System.in).nextInt();
            switch (escolha) {
                case 1:
                    System.out.println(NEGRITO + "\nListando filmes mais populares\n" + RESET);
                    url = "https://api.mocki.io/v2/549a5d8b/MostPopularMovies";
                    break;
                case 2:
                    System.out.println(NEGRITO + "\nListando séries mais populares\n" + RESET);
                    url = "https://api.mocki.io/v2/549a5d8b/MostPopularTVs";
                    break;
                default:
                    System.out.println(NEGRITO + "\nEscolher 1 ou 2\n" + RESET);
                    break;
            }
        }
        return url;
    };

}
