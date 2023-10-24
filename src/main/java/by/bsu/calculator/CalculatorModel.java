package by.bsu.calculator;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

@Getter
@Setter
public class CalculatorModel {
    private String a;
    private String b;

    public String sum() throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).add(new BigDecimal(b)));
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    public String sub() throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).subtract(new BigDecimal(b)));
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    public String mul() throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).multiply(new BigDecimal(b)));
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    public String divide() throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).divide(new BigDecimal(b), 6, RoundingMode.HALF_UP));
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    private void formatNumbers() throws IllegalNumberFormatException {
        String regex = "^((\\d{1,3}( \\d{3})*)|(\\d+))(\\.\\d+)?$";

        if (a.contains(","))
            a = a.replace(",", ".");

        if (!a.matches(regex))
            throw new IllegalNumberFormatException("Wrong format of the first number");
        else
            a = a.replace(" ", "");

        if (b.contains(","))
            b = b.replace(",", ".");

        if (!b.matches(regex))
            throw new IllegalNumberFormatException("Wrong format of the second number");
        else
            b = b.replace(" ", "");
    }

    private String formatNumber(BigDecimal number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.######", symbols);
        System.out.println(decimalFormat.format(number));
        return decimalFormat.format(number);
    }

    private void validateNumbers() throws Exception{
        if (a.contains("e+") || b.contains("e+"))
            throw new Exception();
    }
}
