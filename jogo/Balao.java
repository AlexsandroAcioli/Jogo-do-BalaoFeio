package jogo;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Balao {
    protected double x, y;
    protected int largura, altura;
    protected Image imagem;

    public Balao(int x, int y, String caminhoImagem) {
        this.x = x;
        this.y = y;
        this.largura = 30;
        this.altura = 40;
        this.imagem = new ImageIcon(getClass().getResource(caminhoImagem)).getImage();
    }

    public void atualizar() {
        y -= 2;
    }

    public void desenhar(Graphics g) {
        g.drawImage(imagem, (int) x, (int) y, largura, altura, null);
    }

    public boolean foraDaTela() {
        return y + altura < 0;
    }

    public boolean colidiuComMira(int miraX, int miraY, int miraTamanho) {
        int centroX = (int) x + largura / 2;
        int centroY = (int) y + altura / 2;
        int distancia = (int) Math.sqrt(Math.pow(centroX - miraX, 2) + Math.pow(centroY - miraY, 2));
        return distancia < (miraTamanho / 2) + (largura / 2);
    }
}