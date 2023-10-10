package by.bsu.calculator;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CalculatorModel {
    private String a;
    private String b;

    public BigDecimal sum() throws Exception {
        validateNumbers();
        formatNumbers();
        return new BigDecimal(a).add(new BigDecimal(b));
    }

    public BigDecimal sub() throws Exception {
        validateNumbers();
        formatNumbers();
        return new BigDecimal(a).subtract(new BigDecimal(b));
    }

    private void formatNumbers() {
        if (a.contains(","))
            a = a.replace(",", ".");

        if (b.contains(","))
            b = b.replace(",", ".");
    }

    private void validateNumbers() throws Exception{
        if (a.contains("e+") || b.contains("e+"))
            throw new Exception();
    }
}
