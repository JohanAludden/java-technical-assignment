package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountedBasketTest {

    @DisplayName("basket provides discounted value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesDiscountedValue(String description, String expectedTotal, Iterable<Item> items, Iterable<Discount> discounts) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        discounts.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesDiscountedValue() {
        return Stream.of(
                noItemsNoDiscounts(),
                aSingleItemWithTwoForOneDiscount(),
                twoItemsWithTwoForOneDiscount()
        );
    }

    private static Arguments aSingleItemWithTwoForOneDiscount() {
        return Arguments.of("a single item, 2-4-1 discount", "0.49",
                Collections.singleton(aPintOfMilk()),
                Collections.singletonList(twoForOne()));
    }

    private static Arguments twoItemsWithTwoForOneDiscount() {
        return Arguments.of("two items, 2-4-1 discount", "0.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(twoForOne()));
    }

    private static Arguments noItemsNoDiscounts() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product("milk", new BigDecimal("0.49")).oneOf();
    }

    private static Discount twoForOne() { return new Discount("milk", 2, new BigDecimal("0.5")); }
}
