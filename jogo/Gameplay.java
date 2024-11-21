package jogo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Gameplay extends Canvas implements Runnable, KeyListener {
    public static int largura = 450;
    public static int altura = 400;
    private Mira mira;
    private List<Balao> baloes;
    private int vidas = 3;
    private Random random;
    private int pontos;

    public Gameplay() {
        this.setPreferredSize(new Dimension(largura, altura));
        this.addKeyListener(this);
        mira = new Mira(largura / 2, altura - 50);
        baloes = new ArrayList<>();
        random = new Random();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo dos Balões");
        Gameplay jogo = new Gameplay();
        frame.add(jogo);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        new Thread(jogo).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                atualizar();
                desenhar();
                Thread.sleep(1000 / 60);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void atualizar() {
        mira.atualizar();

        
        if (random.nextInt(100) < 3) {
            if (random.nextInt(100) < 10) { // CHANCE DE 10% PARA NASCER BALÂO DE VIDA
                baloes.add(new BalaoVida(random.nextInt(largura - 30), altura));
            } else {
                baloes.add(new Balao(random.nextInt(largura - 30), altura, "/jogo/Balao.png"));
            }
        }

        List<Balao> baloesRemovidos = new ArrayList<>();
        for (Balao balao : baloes) {
            balao.atualizar();

            if (balao.foraDaTela()) {
                if (!(balao instanceof BalaoVida)) {
                    vidas--;
                }
                baloesRemovidos.add(balao);
            }

            if (balao.colidiuComMira(mira.x, mira.y, mira.tamanho)) {
                if (balao instanceof BalaoVida) {
                    vidas++; 
                } else {
                    pontos++; 
                }
                baloesRemovidos.add(balao);
            }
        }

        baloes.removeAll(baloesRemovidos);

        if (vidas <= 0) {
            System.out.println("Perdeu. Pontos: " + pontos);
            System.exit(0);
        }
    }

    private void desenhar() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, largura, altura);

        mira.desenhar(g);

        for (Balao balao : baloes) {
            balao.desenhar(g);
        }

        
        g.setColor(Color.WHITE);
        g.drawString("Vidas: " + vidas, 10, 20);
        g.drawString("Pontos: " + pontos, 10, 40);

        g.dispose();
        bs.show();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) mira.cima = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) mira.baixo = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) mira.esquerda = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) mira.direita = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) mira.cima = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) mira.baixo = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) mira.esquerda = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) mira.direita = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}