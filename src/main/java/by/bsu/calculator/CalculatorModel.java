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

    public String getResult(String roundingMode) {
        try {
            validateNumbers();
            formatNumbers();
        } catch (IllegalNumberFormatException e) {
            return e.getMessage();
        }

        String midVal;
        String firstVal;
        String result;

        try {
            midVal = calculate(b, c, operator2, RoundingMode.HALF_UP);
            firstVal = calculate(a, midVal, operator1, RoundingMode.HALF_UP);
            result = calculate(firstVal, d, operator3, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return "";
        }

        strSelectedRounding = roundingMode;
        if (strSelectedRounding.equals("Математическое"))
            selectedRounding = RoundingMode.HALF_UP;
        if (strSelectedRounding.equals("Бухгалтерское"))
            selectedRounding = RoundingMode.HALF_EVEN;
        if (strSelectedRounding.equals("Усечение"))
            selectedRounding = RoundingMode.DOWN;
        roundedResult = new BigDecimal(result).setScale(0, selectedRounding).toPlainString();

        result = formatNumber(new BigDecimal(result), RoundingMode.HALF_UP);

        return result;
    }

    public String calculate(String a, String b, String operator, RoundingMode roundingMode) throws Exception {
        return switch (operator) {
            case "+" -> sum(a, b, roundingMode);
            case "-" -> sub(a, b, roundingMode);
            case "*" -> mul(a, b, roundingMode);
            case "/" -> divide(a, b, roundingMode);
            default -> throw new Exception("Unexpected value: " + operator);
        };
    }

    public String sum(String a, String b, RoundingMode roundingMode) {
        return new BigDecimal(a).add(new BigDecimal(b))
                .setScale(10, roundingMode).toPlainString();
    }

    public String sub(String a, String b, RoundingMode roundingMode) {
        return new BigDecimal(a).subtract(new BigDecimal(b))
                .setScale(10, roundingMode).toPlainString();
    }

    public String mul(String a, String b, RoundingMode roundingMode) {
        return new BigDecimal(a).multiply(new BigDecimal(b))
                .setScale(10, roundingMode).toPlainString();
    }

    public String divide(String a, String b, RoundingMode roundingMode) {
        return new BigDecimal(a).divide(new BigDecimal(b), 10, roundingMode)
                .toPlainString();
    }

    private void formatNumbers() throws IllegalNumberFormatException {
        String regex = "^-?((\\d{1,3}( \\d{3})*)|(\\d+))(\\.\\d+)?$";

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
            throw new IllegalNumberFormatException("Wrong format of the third number");
        else
            c = c.replace(" ", "");

        if (d.contains(","))
            d = d.replace(",", ".");

        if (!d.matches(regex))
            throw new IllegalNumberFormatException("Wrong format of the fourth number");
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

    private void validateNumbers() throws IllegalNumberFormatException{
        if (a.contains("e+") || b.contains("e+") || c.contains("e+") || d.contains("e+"))
            throw new IllegalNumberFormatException("Exponential numbers " +
                    "are forbidden");
    }
}
