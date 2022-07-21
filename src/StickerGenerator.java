import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerGenerator {
    void generator(InputStream inputStream, String nomeArquivo, int nota) throws Exception {

        // criar path e arquivos de entrada
        File entrada = new File("../Sticker/entrada");
        if (!entrada.exists()) {
            entrada.mkdirs();
        }

        entrada = new File(entrada + "/" + nomeArquivo);
        if (!entrada.exists()) {
            entrada.createNewFile();
        }

        File saida = new File("../Sticker/saida");
        if (!saida.exists()) {
            saida.mkdirs();
        }
        saida = new File(saida + "/" + nomeArquivo + ".png");
        if (!saida.exists()) {
            saida.createNewFile();
        }

        // leitura da imagem
        BufferedImage imagem = ImageIO.read(inputStream);

        // cria nova imagem local com transparencia e tamanho
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
        if (nota <= 8) {
            graphics.drawString("RUIM", largura / 2 - 250, alturaNova - 45);
        } else
            graphics.drawString("FILMAÇO", largura / 2 - 450, alturaNova - 45);

        // salvar nova imagem em um arquivo
        ImageIO.write(imagemNova, "png", saida);
    };

}
