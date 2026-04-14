package com.bruno.robuxresellercalc;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AddSellerDialog extends javax.swing.JDialog {

    private JTextField fieldName;
    private JPanel tiersContainer;
    private List<TierRow> tierRows;

    private boolean saved = false;
    private String sellerName;
    private TreeMap<Long, Double> sellerTiers;
    private long generatedId;

    private class TierRow {
        JPanel rowPanel;
        JTextField limitField;
        JTextField priceField;
        JButton btnRemove;

        public TierRow() {
            rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            limitField = new JTextField(8);

            limitField.putClientProperty("JTextField.placeholderText", "MAX");

            ((AbstractDocument) limitField.getDocument()).setDocumentFilter(new NumericLimitFilter(15, false));

            priceField = new JTextField(8);

            ((AbstractDocument) priceField.getDocument()).setDocumentFilter(new NumericLimitFilter(10, true));

            btnRemove = new JButton("X");
            btnRemove.setMargin(new Insets(2, 5, 2, 5));

            limitField.setToolTipText("Leave blank for MAX limit");

            rowPanel.add(new JLabel("Below (<):"));
            rowPanel.add(limitField);
            rowPanel.add(new JLabel("Price:"));
            rowPanel.add(priceField);
            rowPanel.add(btnRemove);

            btnRemove.addActionListener(e -> {
                tiersContainer.remove(rowPanel);
                tierRows.remove(this);
                tiersContainer.revalidate();
                tiersContainer.repaint();
            });
        }

    }

    public AddSellerDialog(java.awt.Frame parent, boolean modal, int newId) {
        super(parent, modal);
        this.generatedId = newId;
        this.tierRows = new ArrayList<>();
        this.sellerTiers = new TreeMap<>();


        setTitle("Add Seller");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        initUI();

    }


    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(new JLabel("Seller Name:"));
        fieldName = new JTextField(15);
        topPanel.add(fieldName);

        tiersContainer = new JPanel();
        tiersContainer.setLayout(new BoxLayout(tiersContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(tiersContainer);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pricing Tiers"));
        scrollPane.setPreferredSize(new Dimension(380, 180));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        addNewTierRow();

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JButton btnAddTier = new JButton("+ Add Tier");
        JButton btnSave = new JButton("Save Changes");

        btnAddTier.addActionListener(e -> addNewTierRow());
        btnSave.addActionListener(e -> saveSeller());

        bottomPanel.add(btnAddTier, BorderLayout.WEST);
        bottomPanel.add(btnSave, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnSave);
    }

    private void addNewTierRow() {
        TierRow newRow = new TierRow();
        tierRows.add(newRow);
        tiersContainer.add(newRow.rowPanel);
        tiersContainer.revalidate();
        tiersContainer.repaint();
    }


    private void saveSeller() {
        String name = fieldName.getText().trim();

        if (name.isEmpty() || tierRows.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a name and at least one tier.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sellerTiers.clear();

        try {
            for (TierRow row : tierRows) {
                String limitText = row.limitField.getText().trim().toUpperCase();
                String priceText = row.priceField.getText().trim().replace(",",".");

                if (priceText.isEmpty()) throw new NumberFormatException("Price cannot be empty");

                long limit;
                if (limitText.isEmpty()) {
                    limit = Long.MAX_VALUE;
                } else {
                    long parsed = Long.parseLong(limitText);
                    limit = (parsed > 0) ? parsed - 1: 0;
                }

                double price = Double.parseDouble(priceText);

                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than 0.", "Invalid Price", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                sellerTiers.put(limit, price);
            }

            this.sellerName = name;
            generatedId = System.currentTimeMillis();
            saved = true;
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for limits and prices.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadSellerData(Seller seller) {
        fieldName.setText(seller.getName());

        tiersContainer.removeAll();
        tierRows.clear();

        for (Map.Entry<Long, Double> entry :  seller.getTiers().entrySet()) {
            TierRow existingRow = new TierRow();

            ((AbstractDocument) existingRow.limitField.getDocument()).setDocumentFilter(null);
            ((AbstractDocument) existingRow.priceField.getDocument()).setDocumentFilter(null);

            if (entry.getKey() == Long.MAX_VALUE) {
                existingRow.limitField.setText("");
            } else {
                existingRow.limitField.setText(String.valueOf(entry.getKey() + 1));
            }

            String formattedPrice = String.valueOf(entry.getValue()).replace(".", ",");

            existingRow.priceField.setText(formattedPrice);

            ((AbstractDocument) existingRow.limitField.getDocument()).setDocumentFilter(new NumericLimitFilter(15, false));
            ((AbstractDocument) existingRow.priceField.getDocument()).setDocumentFilter(new NumericLimitFilter(10, true));

            tierRows.add(existingRow);
            tiersContainer.add(existingRow.rowPanel);
        }
        tiersContainer.revalidate();
        tiersContainer.repaint();
    }



    public boolean isSaved() {return saved;}
    public String getSellerName() {return fieldName.getText();}
    public TreeMap<Long, Double> getSellerTiers() {return sellerTiers; }
    public long getGeneratedId() {return generatedId;}
}
