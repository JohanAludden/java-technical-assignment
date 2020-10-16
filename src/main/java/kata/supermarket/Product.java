package kata.supermarket;

import java.math.BigDecimal;

public class Product {

    private final String productCode;
    private final BigDecimal pricePerUnit;

    public Product(final String productCode,
                   final BigDecimal pricePerUnit) {
        this.productCode = productCode;
        this.pricePerUnit = pricePerUnit;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }

    public String productCode() {
        return productCode;
    }
}
