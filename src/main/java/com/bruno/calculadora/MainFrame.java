package com.bruno.calculadora;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("RobuxReseller Calc");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel labelClean = new JLabel("Robux limpios: ");
        JTextField fieldClean = new JTextField(10);

        JLabel labelGamepass = new JLabel("Robux gamepass: ");
        JTextField fieldGamepass = new JTextField(10);

        JLabel labelPrice = new JLabel("Robux precio: ");
        JTextField fieldPrice = new JTextField(10);


        panel.add(labelClean);
        panel.add(fieldClean);

        panel.add(labelGamepass);
        panel.add(fieldGamepass);

        panel.add(labelPrice);
        panel.add(fieldPrice);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.NORTH);
    }

}
