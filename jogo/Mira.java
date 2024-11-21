package jogo;

import java.awt.*;

public class Mira {
    public int x, y, tamanho;
    public boolean cima, baixo, esquerda, direita;

    public Mira(int x, int y) {
        this.x = x;
        this.y = y;
        this.tamanho = 20;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawOval(x - tamanho / 2, y - tamanho / 2, tamanho, tamanho);
    }

    public void atualizar() {
        if (cima) y -= 5;
        if (baixo) y += 5;
        if (esquerda) x -= 5;
        if (direita) x += 5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTamanho() {
        return tamanho;
    }
}