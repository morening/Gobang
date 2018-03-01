package com.morening.java.learn;

import com.morening.java.learn.core.game.Game;
import com.morening.java.learn.ui.ChessBoard;
import com.morening.java.learn.ui.GamePanel;

import javax.swing.JFrame;
import java.awt.*;

public class Gobang extends JFrame{

    public Gobang(){
        setTitle("Gobang v1.0");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Game game = new Game();
        game.init();

        setLayout(new FlowLayout());
        add(new ChessBoard(game));
        add(new GamePanel());
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Gobang gobang = new Gobang();
            gobang.setVisible(true);
        });
    }
}
