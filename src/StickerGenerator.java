import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerGenerator {
    static void generator(InputStream inputStream, String nomeArquivo) throws Exception {
        // criar path e arquivos de entrada

        // InputStream inputStream = new FileInputStream(new
        // File("../Sticker/entrada/entrada.jpg"));
        // InputStream inputStream = new
        // URL("https://imersao-java-apis.s3.amazonaws.com/MostPopularMovies_1.jpg").openStream();
        BufferedImage imagem = ImageIO.read(inputStream);

        // ajustar tamanho da imagem
        int largura = imagem.getWidth();
        int altura = imagem.getHeight();
        int alturaNova = altura + 200;
        BufferedImage imagemNova = new BufferedImage(largura, alturaNova, BufferedImage.TRANSLUCENT);

        // copiar imagem para a nova imagem
        Graphics2D graphics = (Graphics2D) imagemNova.getGraphics();
        graphics.drawImage(imagem, 0, 0, null);

        // setar fonte da escrita na nova imagem
        Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 200);
        graphics.setFont(fonte);

        // escrever na nova imagem
        graphics.drawString("FILMAÇO", largura / 2 - 450, alturaNova - 45);
        // salvar nova imagem
        ImageIO.write(imagemNova, "png", new File("../Sticker/saida/" + nomeArquivo));

    };

}
