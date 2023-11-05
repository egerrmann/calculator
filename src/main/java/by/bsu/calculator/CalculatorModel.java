package by.bsu.calculator;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

@Getter
@Setter
public class CalculatorModel {
    private String a = "0";
    private String b = "0";
    private String c = "0";
    private String d = "0";

    private String roundedResult;

    private String operator1 = "+";
    private String operator2 = "+";
    private String operator3 = "+";

    private ArrayList<String> operations;
    {
        operations = new ArrayList<>();
        operations.add("+");
        operations.add("-");
        operations.add("*");
        operations.add("/");
    }

    String strMathsRounding = "Математическое";
    String strBankRounding = "Бухгалтерское";
    String strTruncRounding = "Усечение";

    RoundingMode selectedRounding = RoundingMode.HALF_UP;
    String strSelectedRounding = "Математическое";

    public String getResult(String roundingMode) throws Exception {
        String midVal = calculate(b, c, operator2, RoundingMode.HALF_UP);
        String firstVal = calculate(a, midVal, operator1, RoundingMode.HALF_UP);
        String result = calculate(firstVal, d, operator3, RoundingMode.HALF_UP);

        strSelectedRounding = roundingMode;
        if (strSelectedRounding.equals("Математическое"))
            selectedRounding = RoundingMode.HALF_UP;
        if (strSelectedRounding.equals("Бухгалтерское"))
            selectedRounding = RoundingMode.HALF_EVEN;
        if (strSelectedRounding.equals("Усечение"))
            selectedRounding = RoundingMode.DOWN;
        roundedResult = new BigDecimal(result).setScale(0, selectedRounding).toString();

        return result;
    }

    public String calculate(String a, String b, String operator, RoundingMode roundingMode) throws Exception {
        return switch (operator) {
            case "+" -> sum(a, b, roundingMode);
            case "-" -> sub(a, b, roundingMode);
            case "*" -> mul(a, b, roundingMode);
            case "/" -> divide(a, b, roundingMode);
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

    public String sum(String a, String b, RoundingMode roundingMode) throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).add(new BigDecimal(b)), roundingMode);
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    public String sub(String a, String b, RoundingMode roundingMode) throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).subtract(new BigDecimal(b)), roundingMode);
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    public String mul(String a, String b, RoundingMode roundingMode) throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).multiply(new BigDecimal(b)), roundingMode);
        } catch (IllegalNumberFormatException e) {
            return e.getLocalizedMessage();
        }
    }

    public String divide(String a, String b, RoundingMode roundingMode) throws Exception {
        try {
            validateNumbers();
            formatNumbers();
            return formatNumber(new BigDecimal(a).divide(new BigDecimal(b)), roundingMode);
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

        if (c.contains(","))
            c = c.replace(",", ".");

        if (!c.matches(regex))
            throw new IllegalNumberFormatException("Wrong format of the second number");
        else
            c = c.replace(" ", "");

        if (d.contains(","))
            d = d.replace(",", ".");

        if (!d.matches(regex))
            throw new IllegalNumberFormatException("Wrong format of the second number");
        else
            d = d.replace(" ", "");
    }

    private String formatNumber(BigDecimal number, RoundingMode roundingMode) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##########", symbols);
        decimalFormat.setRoundingMode(roundingMode);
        System.out.println(decimalFormat.format(number));
        return decimalFormat.format(number);
    }

    private void validateNumbers() throws Exception{
        if (a.contains("e+") || b.contains("e+"))
            throw new Exception();
    }
}
