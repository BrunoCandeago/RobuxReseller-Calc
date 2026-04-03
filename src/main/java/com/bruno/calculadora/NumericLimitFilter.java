package com.bruno.calculadora;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class NumericLimitFilter extends DocumentFilter {

        private int limit;
        private boolean allowDecimal;

        public NumericLimitFilter(int limit, boolean allowDecimal) {
            this.limit = limit; this.allowDecimal = allowDecimal;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;

            if (isValid(fb, fb.getDocument().getLength(),0, string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) text = "";

        if (isValid(fb, fb.getDocument().getLength(),length, text)) {
            super.replace(fb, offset, length, text, attr);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
    }


    private boolean isValid(FilterBypass fb, int currentLength, int lengthToRemove, String newText) {
            if (newText.isEmpty()) {
                return true;
            }

        String allowedRegex = allowDecimal ? "[0-9.,]+" : "\\d+";
        if (!newText.matches(allowedRegex)) {
            return false;
        }

        if (allowDecimal) {
            try {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());

                StringBuilder sb = new StringBuilder(currentText);
                sb.insert(currentLength, newText);

                String totalText = sb.toString().replace(",", ".");

                if (!totalText.equals(".") &&  !totalText.equals("")) {
                    Double.parseDouble(totalText);
                }

                if (totalText.contains(".")) {
                    int precision = totalText.length() - totalText.indexOf(".") - 1;
                    if (precision > 1) {
                        return false;
                    }
                }

            } catch (Exception e) {
                return false;
            }
        }

        int futureLength = (currentLength - lengthToRemove) + newText.length();
        return futureLength <= limit;
        }
}
