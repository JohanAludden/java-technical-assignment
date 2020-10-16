package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
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
                    .map(applyDiscount(items))
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private Function<Discount, BigDecimal> applyDiscount(List<Item> items) {
            return discount -> {
                List<Item> validItems = items.stream()
                        .filter(item -> item.productCode().equals(discount.productCode()))
                        .collect(Collectors.toList());
                return calculateDiscountAmount(discount, validItems);
            };
        }

        private BigDecimal calculateDiscountAmount(Discount discount, List<Item> validItems) {
            if (isApplicable(discount, validItems)) {
                BigDecimal itemPrice = validItems.get(0).price();
                BigDecimal itemsToDiscount = new BigDecimal(validItems.size() - (validItems.size() % discount.quantity()));
                return itemsToDiscount.multiply(itemPrice).multiply(discount.percentageToDeduct());
            }
            return BigDecimal.ZERO;
        }

        private boolean isApplicable(Discount discount, List<Item> validItems) {
            return !validItems.isEmpty() && validItems.size() >= discount.quantity();
        }
    }
}
