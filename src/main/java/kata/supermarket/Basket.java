package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {
    private final List<Item> items;
    private final List<Discount> discounts;

    public Basket() {
        this.items = new ArrayList<>();
        this.discounts = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public void add(final Discount discount) {
        this.discounts.add(discount);
    }

    List<Discount> discounts() {
        return Collections.unmodifiableList(discounts);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private BigDecimal discounts() {
            return new DiscountCalculator().calculate(items);
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }

    private class DiscountCalculator {
        private final List<Discount> discounts;

        DiscountCalculator() {
            this.discounts = discounts();
        }

        private BigDecimal calculate(List<Item> items) {
            return discounts.stream()
                    .map(discount -> {
                        List<Item> validItems = items.stream()
                                .filter(item -> item.productCode().equals(discount.productCode()))
                                .collect(Collectors.toList());
                        if (!validItems.isEmpty() && validItems.size() >= discount.quantity()) {
                            BigDecimal itemPrice = validItems.get(0).price();
                            return new BigDecimal(validItems.size()).multiply(itemPrice).multiply(discount.percentageToDeduct());
                        }
                        return BigDecimal.ZERO;
                    })
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }
}
