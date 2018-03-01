package com.morening.java.learn.ui;

import com.morening.java.learn.core.game.Computer;
import com.morening.java.learn.core.game.Game;
import com.morening.java.learn.core.game.Human;
import com.morening.java.learn.core.game.IPlayer;
import com.morening.java.learn.core.model.Move;
import com.morening.java.learn.core.model.OnOutputListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;

public class ChessBoard extends JPanel implements MouseListener, OnOutputListener{

    private static final int DEFAULT_BOARD_PADDING = 10;
    private static final int DEFAULT_BOARD_SCALE = 40;
    private static final int MAX_BOARD_SIZE = 15;
    private static final int DEFAULT_BOARD_SIZE = DEFAULT_BOARD_SCALE*MAX_BOARD_SIZE;
    private Image bgImage = null;
    private Game game = null;
    private IPlayer player1 = null;
    private IPlayer player2 = null;

    public ChessBoard(Game game){
        try {
            bgImage = ImageIO.read(new FileInputStream("assets/chessboard_bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(DEFAULT_BOARD_SIZE+DEFAULT_BOARD_SCALE+2*DEFAULT_BOARD_PADDING,
                DEFAULT_BOARD_SIZE+DEFAULT_BOARD_SCALE+2*DEFAULT_BOARD_PADDING);

        addMouseListener(this);

        this.game = game;
        this.game.setOnOutputListener(this);
        player1 = new Human();
        player2 = new Computer(3);
        player1.setEnemyMark(player2.getPlayerMark());
        player2.setEnemyMark(player1.getPlayerMark());
        this.game.setPlayer1(player1);
        this.game.setPlayer2(player2);
        this.game.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_BOARD_SIZE+DEFAULT_BOARD_SCALE+2*DEFAULT_BOARD_PADDING,
                DEFAULT_BOARD_SIZE+DEFAULT_BOARD_SCALE+2*DEFAULT_BOARD_PADDING);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(bgImage, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2,
                DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2,
                DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE, this);

        for (int k=0; k<=MAX_BOARD_SIZE; k++){
            graphics.drawLine(DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+DEFAULT_BOARD_SCALE*k,
                    DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+DEFAULT_BOARD_SIZE, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+DEFAULT_BOARD_SCALE*k);
            graphics.drawLine(DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+DEFAULT_BOARD_SCALE*k, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2,
                    DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+DEFAULT_BOARD_SCALE*k, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+DEFAULT_BOARD_SIZE);
        }

        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setStroke(new BasicStroke(3.0f));
        graphics2D.drawLine(DEFAULT_BOARD_PADDING, DEFAULT_BOARD_PADDING,
                DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE, DEFAULT_BOARD_PADDING);
        graphics2D.drawLine(DEFAULT_BOARD_PADDING, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE,
                DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE);
        graphics2D.drawLine(DEFAULT_BOARD_PADDING, DEFAULT_BOARD_PADDING,
                DEFAULT_BOARD_PADDING, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE);
        graphics2D.drawLine(DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE, DEFAULT_BOARD_PADDING,
                DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE, DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE+DEFAULT_BOARD_SIZE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickedX = e.getX();
        int clickedY = e.getY();
        int indexX = (clickedX-DEFAULT_BOARD_PADDING)/DEFAULT_BOARD_SCALE;
        int indexY = (clickedY-DEFAULT_BOARD_PADDING)/DEFAULT_BOARD_SCALE;
        Chessman chessman = new Chessman();
        chessman.setColor(Color.BLACK);
        chessman.setLocation(DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+indexX*DEFAULT_BOARD_SCALE-chessman.getWidth()/2,
                DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+indexY*DEFAULT_BOARD_SCALE-chessman.getHeight()/2);
        add(chessman);
        game.inputMove(player1, new Move(indexX, indexY));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void onOutput(IPlayer player, Move move) {
        Chessman chessman = new Chessman();
        chessman.setColor(Color.WHITE);
        chessman.setLocation(DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+move.x*DEFAULT_BOARD_SCALE-chessman.getWidth()/2,
                DEFAULT_BOARD_PADDING+DEFAULT_BOARD_SCALE/2+move.y*DEFAULT_BOARD_SCALE-chessman.getHeight()/2);
        add(chessman);
        repaint();
    }
}
