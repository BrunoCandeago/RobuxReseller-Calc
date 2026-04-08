package com.bruno.robuxresellercalc;

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
            handleTextChange(fb, offset, 0, string, attr);
        }


    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) text = "";

        handleTextChange(fb, offset, length, text, attr);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        handleTextChange(fb, offset, length, "", null);
    }


    private void handleTextChange(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());

            StringBuilder sb = new StringBuilder(currentText);
            sb.replace(offset, offset + length, text);
            String futureText = sb.toString();

            if (isValid(futureText)) {
                super.replace(fb, offset, length, text, attr);
            } else  {
                Toolkit.getDefaultToolkit().beep();
            }
    }

    private boolean isValid(String futureText) {
            if (futureText.isEmpty()) return true;

            if (futureText.length() > limit) return false;

            String regex;
            if (allowDecimal) {
                regex = "^\\d*([.,]\\d{0,2})?$";
            } else {
                regex = "^\\d+$";
            }

            return futureText.matches(regex);
        }

}
