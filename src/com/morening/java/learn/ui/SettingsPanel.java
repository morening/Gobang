package com.morening.java.learn.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private static final int DEFAULT_PANEL_WIDTH = 400;
    private static final int DEFAULT_PANEL_HEIGHT = 300;

    public SettingsPanel(){
        setSize(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
        Border etched = BorderFactory.createEtchedBorder();
        Font font = new Font(null, Font.BOLD, 20);
        setBorder(BorderFactory.createTitledBorder(etched, "设置", TitledBorder.CENTER, TitledBorder.TOP, font));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
    }
}
