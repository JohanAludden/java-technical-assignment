package kata.supermarket;

import java.math.BigDecimal;

public class Discount {

    private final String productCode;
    private final long quantity;
    private final BigDecimal percentageToDeduct;

    public Discount(String productCode, long quantity, BigDecimal percentageToDeduct) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.percentageToDeduct = percentageToDeduct;
    }

    public String productCode() { return productCode; }

    public long quantity() {
        return quantity;
    }

    public BigDecimal percentageToDeduct() {
        return percentageToDeduct;
    }
}