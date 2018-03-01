package com.morening.java.learn.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Chessman extends JComponent {

    private static final int DEFAULT_CHESSMAN_SIZE = 30;

    private Color color = null;

    public Chessman(){
        setSize(DEFAULT_CHESSMAN_SIZE, DEFAULT_CHESSMAN_SIZE);
        setBackground(Color.RED);
        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_CHESSMAN_SIZE, DEFAULT_CHESSMAN_SIZE);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(color);
        Ellipse2D ellipse2D = new Ellipse2D.Double(0, 0, DEFAULT_CHESSMAN_SIZE, DEFAULT_CHESSMAN_SIZE);
        graphics2D.fill(ellipse2D);
    }

    public void setColor(Color color){
        this.color = color;
    }
}
