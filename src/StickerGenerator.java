import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Font;

import javax.imageio.ImageIO;

public class StickerGenerator {
    public static void main(String[] args) throws Exception {
        generator();
    }

    static void generator() throws Exception {
        // criar path e arquivos de entrada/saida
        /*
         * File entrada = new File("../Sticker/entrada/fdg.jpg");
         * if (!entrada.exists()) {
         * entrada.mkdirs();
         * }
         * entrada = new File(entrada + "/fdg.jpg");
         * if (!entrada.exists()) {
         * entrada.mkdirs();
         * }
         * 
         * File saida = new File("../Sticker/saida/figurinha.png");
         * if (!saida.exists()) {
         * saida.mkdirs();
         * }
         * saida = new File(saida + "/figurinha.png");
         * if (!saida.exists()) {
         * saida.mkdirs();S
         * }
         */
        BufferedImage imagem = ImageIO.read(new File("../Sticker/entrada/entrada.jpg"));

        // ajustar tamanho da imagem
        int largura = imagem.getWidth();
        int altura = imagem.getHeight();
        int alturaNova = altura + 200;
        BufferedImage imagemNova = new BufferedImage(largura, alturaNova, BufferedImage.TRANSLUCENT);

        // copiar imagem para a nova imagem
        Graphics2D graphics = (Graphics2D) imagemNova.getGraphics();
        graphics.drawImage(imagem, 0, 0, null);

        // setar fonte da escrita na nova imagem
        Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 64);
        graphics.setFont(fonte);

        // escrever na nova imagem
        graphics.drawString("FILMEZAZO", 50, alturaNova - 100);
        // salvar nova imagem
        ImageIO.write(imagemNova, "png", new File("../Sticker/saida/figurinha.png"));

    };

}
