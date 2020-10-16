package kata.supermarket;

import java.math.BigDecimal;

public class WeighedProduct {

    private final String productCode;
    private final BigDecimal pricePerKilo;

    public WeighedProduct(final String productCode,
                          final BigDecimal pricePerKilo) {
        this.productCode = productCode;
        this.pricePerKilo = pricePerKilo;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }

    public String productCode() {
        return productCode;
    }
}
