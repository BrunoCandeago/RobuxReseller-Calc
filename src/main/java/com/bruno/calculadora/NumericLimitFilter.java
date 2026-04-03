package com.bruno.calculadora;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class NumericLimitFilter extends DocumentFilter {

        private int limit;

        public NumericLimitFilter(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;

            if (isValid(fb.getDocument().getLength(),0, string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) text = "";

        if (isValid(fb.getDocument().getLength(),length, text)) {
            super.replace(fb, offset, length, text, attr);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
    }


    private boolean isValid(int currentLength, int lengthToRemove, String newText) {
            if (newText.isEmpty()) {
                return true;
            }

        if (!newText.matches("\\d+")) {
            return false;
        }

        int futureLength = (currentLength - lengthToRemove) + newText.length();
        return futureLength <= limit;
        }
}
