package com.morening.java.learn.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    private static final int DEFAULT_PANEL_WIDTH = 400;
    private static final int DEFAULT_PANEL_HEIGHT = 150;

    public InfoPanel(){
        setSize(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
        Border etched = BorderFactory.createEtchedBorder();
        Font font = new Font(null, Font.BOLD, 20);
        setBorder(BorderFactory.createTitledBorder(etched, "信息", TitledBorder.CENTER, TitledBorder.TOP, font));

        JLabel label = new JLabel();
        label.setText("请开始游戏");
        label.setFont(new Font(null, Font.BOLD, 36));
//        label.setLocation((int)(getPreferredSize().getWidth() - label.getPreferredSize().getWidth())/2, (int)(getPreferredSize().getHeight() - label.getPreferredSize().getHeight())/2);
        add(label);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
    }
}
