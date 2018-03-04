package com.morening.java.learn.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private static final int DEFAULT_PANEL_WIDTH = 400;
    private static final int DEFAULT_PANEL_HEIGHT = 450;
    private static final int DEFAULT_V_GAP = 20;

    public SettingsPanel(){
        setSize(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
        Border etched = BorderFactory.createEtchedBorder();
        Font borderFont = new Font(null, Font.BOLD, 20);
        setBorder(BorderFactory.createTitledBorder(etched, "设置", TitledBorder.CENTER, TitledBorder.TOP, borderFont));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        setLayout(flowLayout);

        Font radioFont = new Font(null, Font.PLAIN, 16);
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        add(panelTop);
        addHSeparator();
        add(panelBottom);
        addHSeparator();

        GridLayout gridLayoutTop = new GridLayout(4, 1);
        gridLayoutTop.setVgap(DEFAULT_V_GAP);
        panelTop.setLayout(gridLayoutTop);
        GridLayout gridLayoutBottom = new GridLayout(2, 1);
        gridLayoutBottom.setVgap(DEFAULT_V_GAP);
        panelBottom.setLayout(gridLayoutBottom);

        ButtonGroup btnGroup_mode = new ButtonGroup();
        JRadioButton radioBtn_human_vs_computer = new JRadioButton("Human vs Computer");radioBtn_human_vs_computer.setFont(radioFont);
        JRadioButton radioBtn_computer_vs_human = new JRadioButton("Computer vs Human");radioBtn_computer_vs_human.setFont(radioFont);
        JRadioButton radioBtn_human_vs_human = new JRadioButton("Human vs Human");radioBtn_human_vs_human.setFont(radioFont);
        JRadioButton radioBtn_computer_vs_computer = new JRadioButton("Computer vs Computer");radioBtn_computer_vs_computer.setFont(radioFont);
        btnGroup_mode.add(radioBtn_human_vs_computer);
        btnGroup_mode.add(radioBtn_computer_vs_human);
        btnGroup_mode.add(radioBtn_human_vs_human);
        btnGroup_mode.add(radioBtn_computer_vs_computer);
        radioBtn_human_vs_computer.setSelected(true);
        panelTop.add(radioBtn_human_vs_computer);
        panelTop.add(radioBtn_computer_vs_human);
        panelTop.add(radioBtn_human_vs_human);
        panelTop.add(radioBtn_computer_vs_computer);

        ButtonGroup btnGroup_depth = new ButtonGroup();
        JRadioButton radioBtn_1_depth = new JRadioButton("1 depth");radioBtn_1_depth.setFont(radioFont);
        JRadioButton radioBtn_3_depth = new JRadioButton("3 depth");radioBtn_3_depth.setFont(radioFont);
        btnGroup_depth.add(radioBtn_1_depth);
        btnGroup_depth.add(radioBtn_3_depth);
        radioBtn_1_depth.setSelected(true);
        panelBottom.add(radioBtn_1_depth);
        panelBottom.add(radioBtn_3_depth);

        JButton startBtn = new JButton("START");
        startBtn.setFont(new Font(null, Font.PLAIN, 18));
        add(startBtn);

        int topHeight = (int)(radioBtn_human_vs_computer.getPreferredSize().getHeight()
                + radioBtn_computer_vs_human.getPreferredSize().getHeight()
                + radioBtn_human_vs_human.getPreferredSize().getHeight()
                + radioBtn_computer_vs_computer.getPreferredSize().getHeight()
                + DEFAULT_V_GAP * 3);
        panelTop.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH-40, topHeight));
        int bottomHeight = (int)(radioBtn_1_depth.getPreferredSize().getHeight()
                + radioBtn_3_depth.getPreferredSize().getHeight()
                + DEFAULT_V_GAP);
        panelBottom.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH-40, bottomHeight));
    }

    private void addHSeparator(){
        JSeparator hSeparator = new JSeparator();
        hSeparator.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH-40, 5));
        hSeparator.setOrientation(SwingConstants.HORIZONTAL);
        add(hSeparator);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
    }
}
