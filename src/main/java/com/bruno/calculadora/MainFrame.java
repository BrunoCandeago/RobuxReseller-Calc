package com.bruno.calculadora;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField fieldClean;
    private JTextField fieldGamepass;
    private JTextField fieldPrice;
    private JComboBox<Seller> sellerBox;
    private JButton btnAddSeller;

    private List<Seller> listaVendedores;

    private boolean calculando = false;

    public MainFrame(List<Seller> listaVendedores) {
        this.listaVendedores = listaVendedores;

        setTitle("RobuxReseller Calc");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        initListeners();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel labelClean = new JLabel("Robux limpios: ");
        fieldClean = new JTextField(10);
        ((javax.swing.text.AbstractDocument) fieldClean.getDocument()).setDocumentFilter(new NumericLimitFilter(10));

        JLabel labelGamepass = new JLabel("Robux gamepass: ");
        fieldGamepass = new JTextField(10);
        ((javax.swing.text.AbstractDocument) fieldGamepass.getDocument()).setDocumentFilter(new NumericLimitFilter(10));

        JLabel labelPrice = new JLabel("Robux precio: ");
        fieldPrice = new JTextField(10);
        ((javax.swing.text.AbstractDocument) fieldPrice.getDocument()).setDocumentFilter(new NumericLimitFilter(10));

        JLabel labelSeller = new JLabel("Robux seller: ");

        sellerBox = new JComboBox<>();
        for (Seller s : listaVendedores) {
            sellerBox.addItem(s);
        }

        btnAddSeller = new JButton("Add Seller");


        panel.add(labelClean);
        panel.add(fieldClean);

        panel.add(labelGamepass);
        panel.add(fieldGamepass);

        panel.add(labelPrice);
        panel.add(fieldPrice);

        panel.add(labelSeller);
        panel.add(sellerBox);

        panel.add(btnAddSeller);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.NORTH);
    }

    private void initListeners() {
        DocumentListener documentListenerClean = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                actualizarDesdeLimpios();
            }
            public void removeUpdate(DocumentEvent e) {
                actualizarDesdeLimpios();
            }
        };

        DocumentListener documentListenerGamepass = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                actualizarDesdeGamepass();
            }
            public void removeUpdate(DocumentEvent e) {
                actualizarDesdeGamepass();
            }
        };

        DocumentListener documentListenerPrice = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                actualizarDesdePrecio();
            }
            public void removeUpdate(DocumentEvent e) {
                actualizarDesdePrecio();
            }
        };

        ActionListener actionListenerBox = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDesdeLimpios();
            }
        };

        ActionListener actionListenerSeller = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {addSeller();}
        };

        fieldClean.getDocument().addDocumentListener(documentListenerClean);
        fieldGamepass.getDocument().addDocumentListener(documentListenerGamepass);
        fieldPrice.getDocument().addDocumentListener(documentListenerPrice);
        sellerBox.addActionListener(actionListenerBox);
        btnAddSeller.addActionListener(actionListenerSeller);

    }

    private void actualizarDesdeLimpios() {
        if (calculando) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
        try {

            calculando = true;

            long textClean = Long.parseLong(fieldClean.getText());
            Calculator calculator = new Calculator();
            Seller actualSeller = (Seller) sellerBox.getSelectedItem();

            long resultadoGamepass = calculator.calculateGamepassFromClean(textClean);
            long resultadoPrecio = calculator.calculatePriceFromClean(textClean, actualSeller);

            fieldGamepass.setText(String.valueOf(resultadoGamepass));
            fieldPrice.setText(String.valueOf(resultadoPrecio));

        } catch (NumberFormatException ex) {
            fieldGamepass.setText("");
            fieldPrice.setText("");
        } finally {
            calculando = false;
        }
    });
        }

    private void actualizarDesdeGamepass() {
        if (calculando) {
            return;
        }

        SwingUtilities.invokeLater(() -> {

            try {

                calculando = true;

                long textGamepass = Long.parseLong(fieldGamepass.getText());
                Calculator calculator = new Calculator();
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                long resultadoClean = calculator.calculateCleanFromGamepass(textGamepass);
                long resultadoPrecio = calculator.calculatePriceFromGamepass(textGamepass, actualSeller);

                fieldClean.setText(String.valueOf(resultadoClean));
                fieldPrice.setText(String.valueOf(resultadoPrecio));

            } catch (NumberFormatException ex) {
                fieldClean.setText("");
                fieldPrice.setText("");
            } finally {
                calculando = false;
            }
        });
    }

    private void actualizarDesdePrecio() {
        if (calculando) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {

                calculando = true;

                long textPrice = Long.parseLong(fieldPrice.getText());
                Calculator calculator = new Calculator();
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                long resultadoGamepass = calculator.calculateGamepassFromPrice(textPrice, actualSeller);
                long resultadoClean = calculator.calculateCleanFromPrice(textPrice, actualSeller);

                fieldGamepass.setText(String.valueOf(resultadoGamepass));
                fieldClean.setText(String.valueOf(resultadoClean));

            } catch (NumberFormatException ex) {
                fieldGamepass.setText("");
                fieldClean.setText("");
            } finally {
                calculando = false;
            }
        });
    }

    private void addSeller() {
        AddSellerDialog sellerDialog = new AddSellerDialog(this, true, listaVendedores.size() + 1);
        sellerDialog.setVisible(true);

        if (sellerDialog.isSaved()) {
            String newName = sellerDialog.getSellerName();
            String newPrice = sellerDialog.getSellerPrice();

            double newPriceDouble = Double.parseDouble(newPrice);

            Seller newSeller = new Seller(newName, newPriceDouble);

            listaVendedores.add(newSeller);
            sellerBox.addItem(newSeller);
        }
    }

}
