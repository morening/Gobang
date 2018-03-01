package com.morening.java.learn.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int DEFAULT_PANEL_WIDTH = 400;
    private static final int DEFAULT_PANEL_HEIGHT = 660;

    public GamePanel(){

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setVgap(20);
        setLayout(flowLayout);
        add(new InfoPanel());
        add(new SettingsPanel());

        setSize(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
    }
}
