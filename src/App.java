import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class App {
    final static String NASA_KEY = System.getenv().get("NASA_KEY");

    static String URL_FILME = "https://api.mocki.io/v2/549a5d8b/MostPopularMovies";
    static String URL_SERIE = "https://api.mocki.io/v2/549a5d8b/MostPopularTVs";
    static String URL_LINGUAGEM = "";
    static String URL_ALEATORIA = "https://api.nasa.gov/planetary/apod?api_key=LxRbvwQqReFLXvlZf26pgC5xHQPvdOQbobVhgB4P";
    static ExtratorConteudo extrator;

    final static String RESET = "\u001b[m";
    final static String NEGRITO = "\u001b[1m";
    final static String VERDE = "\u001b[32m";
    final static String SUBLINHADO = "\u001b[4m";
    final static String AMARELO = "\u001b[33m";

    public static void main(String[] args) throws Exception {
        // Realizar a conexão HTTP
        String url = solicitarURL();

        var http = new ClientHttp();
        String json = http.buscaDados(url);

        // Extrair ou parsear os dados que precisamos (titulo, poster e rating)
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        // Exibir e manipular os dados
        var geradora = new StickerGenerator();

        for (int i = 0; i < 3; i++) {
            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = conteudo.getTitulo() + ".png";

            geradora.generator(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo());
            System.out.println();

        }

    }

    private static String solicitarURL() {
        int escolha = 0;
        String url = "";
        while ((escolha < 1) || (escolha > 4)) {
            System.out.print(
                    "Gerar figurinhas:\n1 - Filmes mais populares\n2 - Séries mais populares\n3 - Linguagens mais populares\n4 - Aleatórias\nR.:");
            escolha = new Scanner(System.in).nextInt();
            switch (escolha) {
                case 1:
                    System.out.println(NEGRITO + "\nListando filmes mais populares\n" + RESET);
                    url = URL_FILME;
                    extrator = new ExtratorConteudoImdb();
                    break;
                case 2:
                    System.out.println(NEGRITO + "\nListando séries mais populares\n" + RESET);
                    url = URL_SERIE;
                    extrator = new ExtratorConteudoNasa();
                    break;
                case 3:
                    System.out.println(NEGRITO + "\nListando linguagens mais populares\n" + RESET);
                    url = URL_LINGUAGEM;
                    extrator = new ExtratorConteudoImdb();
                    break;
                case 4:
                    System.out.println(NEGRITO + "\nListando URL aleatória\n" + RESET);
                    url = URL_ALEATORIA;
                    extrator = new ExtratorConteudoNasa();
                    break;
                default:
                    System.out.println(NEGRITO + "\nDigitar valores entre 1 à 5\n" + RESET);
                    break;
            }
        }
        return url;

    }

    /*
     * public int printarImdb() {
     * String titulo = filme.get("title");
     * String imagem = filme.get("image");
     * 
     * String extensao = imagem.substring(imagem.length() - 4);
     * String nomeArquivo = titulo.replace(":", "-") + extensao;
     * 
     * System.out.println(NEGRITO + VERDE + titulo + RESET);
     * System.out.println(SUBLINHADO + imagem + RESET);
     * 
     * int nota = Math.round(Float.parseFloat(filme.get("imDbRating")));
     * var estrela = "";
     * for (int i = 0; i < nota; i++) {
     * estrela = estrela + "\u2B50";
     * }
     * System.out.print(estrela);
     * System.out.println(AMARELO + " - (" + filme.get("imDbRating") + ")" + RESET);
     * System.out.println();
     * 
     * try {
     * InputStream inputStream = new URL(imagem).openStream();
     * var geranerator = new StickerGenerator();
     * System.out.println("Gerando imagem - [" + titulo + "]\n");
     * geranerator.generator(inputStream, nomeArquivo, nota);
     * } catch (java.io.FileNotFoundException err) {
     * System.out.println("Imagem não encontrada ou link inválido\n");
     * }
     * return nota;
     * }
     */

}
