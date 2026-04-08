package com.bruno.robuxresellercalc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileWriter;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField fieldClean;
    private JTextField fieldGamepass;
    private JTextField fieldPrice;
    private JComboBox<Seller> sellerBox;
    private JButton btnAddSeller;
    private JButton btnDeleteSeller;
    private JButton btnEditSeller;
    private JButton btnCopyClean;
    private JButton btnCopyGamepass;
    private JButton btnCopyPrice;
    private List<Seller> sellerList;

    private boolean isCalculating = false;

    public MainFrame(List<Seller> sellerList) {
        this.sellerList = sellerList;

        setTitle("Robux Reseller Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        initListeners();

        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel labelClean = new JLabel("Clean Robux: ");
        fieldClean = new JTextField("0",10);
        ((javax.swing.text.AbstractDocument) fieldClean.getDocument()).setDocumentFilter(new NumericLimitFilter(10, false));

        btnCopyClean = new JButton("Copy");

        JPanel wrapClean = new JPanel(new BorderLayout(5, 0));
        wrapClean.add(fieldClean, BorderLayout.CENTER);
        wrapClean.add(btnCopyClean, BorderLayout.EAST);

        JLabel labelGamepass = new JLabel("Gamepass Robux: ");
        fieldGamepass = new JTextField("0",10);
        ((javax.swing.text.AbstractDocument) fieldGamepass.getDocument()).setDocumentFilter(new NumericLimitFilter(10, false));

        btnCopyGamepass = new JButton("Copy");

        JPanel wrapGamepass = new JPanel(new BorderLayout(5, 0));
        wrapGamepass.add(fieldGamepass, BorderLayout.CENTER);
        wrapGamepass.add(btnCopyGamepass, BorderLayout.EAST);

        JLabel labelPrice = new JLabel("Price: ");
        fieldPrice = new JTextField("0",10);
        ((javax.swing.text.AbstractDocument) fieldPrice.getDocument()).setDocumentFilter(new NumericLimitFilter(10, false));

        btnCopyPrice = new JButton("Copy");

        JPanel wrapPrice = new JPanel(new BorderLayout(5, 0));
        wrapPrice.add(fieldPrice, BorderLayout.CENTER);
        wrapPrice.add(btnCopyPrice, BorderLayout.EAST);

        JLabel labelSeller = new JLabel("Seller: ");

        sellerBox = new JComboBox<>();
        for (Seller s : sellerList) {
            sellerBox.addItem(s);
        }

        formPanel.add(labelClean);
        formPanel.add(wrapClean);
        formPanel.add(labelGamepass);
        formPanel.add(wrapGamepass);
        formPanel.add(labelPrice);
        formPanel.add(wrapPrice);
        formPanel.add(labelSeller);
        formPanel.add(sellerBox);

        btnAddSeller = new JButton("Add");
        btnEditSeller = new JButton("Edit");
        btnDeleteSeller = new JButton("Delete");

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionPanel.add(btnAddSeller);
        actionPanel.add(btnEditSeller);
        actionPanel.add(btnDeleteSeller);

        JPanel mainPanel = new JPanel(new BorderLayout(10,15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.NORTH);
    }

    private void initListeners() {
        DocumentListener documentListenerClean = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) { updateFromClean(); }
            public void removeUpdate(DocumentEvent e) {
                updateFromClean();
            }
        };

        DocumentListener documentListenerGamepass = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                updateFromGamepass();
            }
            public void removeUpdate(DocumentEvent e) {
                updateFromGamepass();
            }
        };

        DocumentListener documentListenerPrice = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                updateFromPrice();
            }
            public void removeUpdate(DocumentEvent e) {
                updateFromPrice();
            }
        };

        ActionListener actionListenerBox = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                if (actualSeller != null) {

                }

                updateFromClean();
            }
        };

        ActionListener actionListenerSeller = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {addSeller();}
        };

        ActionListener actionListenerCopyAll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnCopyClean) {
                    copyToClipboard(fieldClean.getText());
                } else if (e.getSource() == btnCopyGamepass) {
                    copyToClipboard(fieldGamepass.getText());
                } else if (e.getSource() == btnCopyPrice) {
                    copyToClipboard(fieldPrice.getText());
                }
            }
        };

        FocusAdapter zeroHandler = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();
                if (field.getText().equals("0")) {
                    field.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();
                if (field.getText().isEmpty()) {
                    field.setText("0");
                    updateFromClean();
                }
            }
        };

        fieldClean.getDocument().addDocumentListener(documentListenerClean);
        fieldClean.addFocusListener(zeroHandler);
        fieldGamepass.getDocument().addDocumentListener(documentListenerGamepass);
        fieldGamepass.addFocusListener(zeroHandler);
        fieldPrice.getDocument().addDocumentListener(documentListenerPrice);
        fieldPrice.addFocusListener(zeroHandler);
        sellerBox.addActionListener(actionListenerBox);
        btnAddSeller.addActionListener(actionListenerSeller);
        btnCopyClean.addActionListener(actionListenerCopyAll);
        btnCopyGamepass.addActionListener(actionListenerCopyAll);
        btnCopyPrice.addActionListener(actionListenerCopyAll);
        btnEditSeller.addActionListener(e -> editSeller());
        btnDeleteSeller.addActionListener(e -> deleteSeller());

    }

    private void updateFromClean() {
        if (isCalculating) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
        try {

            isCalculating = true;

            String rawText = fieldClean.getText();
            long textClean = rawText.isEmpty() ? 0 : Long.parseLong(rawText);
            Calculator calculator = new Calculator();
            Seller actualSeller = (Seller) sellerBox.getSelectedItem();

            long gamepassResult = calculator.calculateGamepassFromClean(textClean);
            long priceResult = calculator.calculatePriceFromClean(textClean, actualSeller);

            fieldGamepass.setText(String.valueOf(gamepassResult));
            fieldPrice.setText(String.valueOf(priceResult));

        } catch (NumberFormatException ex) {
            fieldGamepass.setText("");
            fieldPrice.setText("");
        } finally {
            isCalculating = false;
        }
    });
        }

    private void updateFromGamepass() {
        if (isCalculating) {
            return;
        }

        SwingUtilities.invokeLater(() -> {

            try {

                isCalculating = true;

                String rawText = fieldGamepass.getText();
                long textGamepass = rawText.isEmpty() ? 0 : Long.parseLong(rawText);
                Calculator calculator = new Calculator();
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                long cleanResult = calculator.calculateCleanFromGamepass(textGamepass);
                long priceResult = calculator.calculatePriceFromGamepass(textGamepass, actualSeller);

                fieldClean.setText(String.valueOf(cleanResult));
                fieldPrice.setText(String.valueOf(priceResult));

            } catch (NumberFormatException ex) {
                fieldClean.setText("");
                fieldPrice.setText("");
            } finally {
                isCalculating = false;
            }
        });
    }

    private void updateFromPrice() {
        if (isCalculating) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {

                isCalculating = true;

                String rawText = fieldPrice.getText();
                long textPrice = rawText.isEmpty() ? 0 : Long.parseLong(rawText);
                Calculator calculator = new Calculator();
                Seller actualSeller = (Seller) sellerBox.getSelectedItem();

                long gamepassResult = calculator.calculateGamepassFromPrice(textPrice, actualSeller);
                long cleanResult = calculator.calculateCleanFromPrice(textPrice, actualSeller);

                fieldGamepass.setText(String.valueOf(gamepassResult));
                fieldClean.setText(String.valueOf(cleanResult));

            } catch (NumberFormatException ex) {
                fieldGamepass.setText("");
                fieldClean.setText("");
            } finally {
                isCalculating = false;
            }
        });
    }

    private void addSeller() {
        AddSellerDialog sellerDialog = new AddSellerDialog(this, true, sellerList.size() + 1);
        sellerDialog.setVisible(true);

        if (sellerDialog.isSaved()) {
            long newId = sellerDialog.getGeneratedId();
            String newName = sellerDialog.getSellerName();
            String newPrice = sellerDialog.getSellerPrice();

            double newPriceDouble = Double.parseDouble(newPrice.replace(",","."));

            Seller newSeller = new Seller(newId, newName, newPriceDouble);

            sellerList.add(newSeller);
            sellerBox.addItem(newSeller);
        }
    }

    private void copyToClipboard(String text) {
        if (text != null &&  !text.isEmpty()) {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    private void deleteSeller() {
        Seller selectedSeller = (Seller) sellerBox.getSelectedItem();
        if (selectedSeller == null) {
            return;
        }

        if (selectedSeller.getId() == 1) {
            JOptionPane.showMessageDialog(this,
                    "The 'Base' seller cannot be deleted as it is required for default calculations.",
                    "Action Denied",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete '" + selectedSeller.getName() + "'?",
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            sellerList.remove(selectedSeller);
            sellerBox.removeItem(selectedSeller);

            rewriteConfigFile();
        }
    }

    private void rewriteConfigFile() {
        try (FileWriter writer = new FileWriter("config.properties",false)) {
            for (Seller seller : sellerList) {
                writer.write("seller" + seller.getId() + ".name=" + seller.getName() + "\n");
                writer.write("seller" + seller.getId() + ".pricePer80Robux=" + seller.getPricePer80Robux() + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating config.properties", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSeller() {
        Seller  selectedSeller = (Seller) sellerBox.getSelectedItem();
        if (selectedSeller == null) {
            return;
        }

        JTextField editNameField = new JTextField(selectedSeller.getName());
        JTextField editPriceField = new JTextField(String.valueOf(selectedSeller.getPricePer80Robux()));

        ((AbstractDocument) editPriceField.getDocument())
                .setDocumentFilter(new NumericLimitFilter(10, true));

        JPanel panel = new JPanel(new GridLayout(2,2,5,5));
        panel.add(new JLabel("New Name:"));
        panel.add(editNameField);
        panel.add(new JLabel("New Price:"));
        panel.add(editPriceField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Edit Seller", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String newName = editNameField.getText().trim();
            String newPriceStr = editPriceField.getText().trim().replace(",",".");

            if (newName.isEmpty() || newPriceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields", "Missing data", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double newPrice = Double.parseDouble(newPriceStr);

                selectedSeller.setName(newName);
                selectedSeller.setPricePer80Robux(newPrice);

                int index = sellerBox.getSelectedIndex();
                sellerBox.removeItemAt(index);
                sellerBox.insertItemAt(selectedSeller, index);
                sellerBox.setSelectedIndex(index);

                rewriteConfigFile();
                updateFromClean();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numerical price.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
            }
            }
        }
    }
