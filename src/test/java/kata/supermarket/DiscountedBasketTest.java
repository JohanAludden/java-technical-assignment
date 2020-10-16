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
                twoItemsWithTwoForOneDiscount(),
                threeItemsWithTwoForOneDiscount(),
                fourItemsWithTwoForOneDiscount(),
                multipleItemsWithTwoForOneDiscount(),
                twoItemsWithThreeForTwoDiscount(),
                threeItemsWithThreeForTwoDiscount(),
                fourItemsWithThreeForTwoDiscount(),
                multipleItemsWithThreeForTwoDiscount(),
                multipleItemsWithMultipleDiscounts()
        );
    }

    private static Arguments aSingleItemWithTwoForOneDiscount() {
        return Arguments.of("a single item, 2-4-1 discount", "0.49",
                Collections.singleton(aPintOfMilk()),
                Collections.singletonList(twoForOne("milk")));
    }

    private static Arguments twoItemsWithTwoForOneDiscount() {
        return Arguments.of("two items, 2-4-1 discount", "0.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(twoForOne("milk")));
    }

    private static Arguments threeItemsWithTwoForOneDiscount() {
        return Arguments.of("three items, 2-4-1 discount", "0.98",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(twoForOne("milk")));
    }

    private static Arguments fourItemsWithTwoForOneDiscount() {
        return Arguments.of("four items, 2-4-1 discount", "0.98",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(twoForOne("milk")));
    }

    private static Arguments multipleItemsWithTwoForOneDiscount() {
        return Arguments.of("multiple items, 2-4-1 discount", "3.59",
                Arrays.asList(aPintOfMilk(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()),
                Collections.singletonList(twoForOne("milk")));
    }

    private static Arguments twoItemsWithThreeForTwoDiscount() {
        return Arguments.of("two items, 3-4-2 discount", "0.98",
                Arrays.asList(aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(threeForTwo("milk")));
    }

    private static Arguments threeItemsWithThreeForTwoDiscount() {
        return Arguments.of("three items, 3-4-2 discount", "0.98",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(threeForTwo("milk")));
    }

    private static Arguments fourItemsWithThreeForTwoDiscount() {
        return Arguments.of("four items, 3-4-2 discount", "1.47",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(threeForTwo("milk")));
    }

    private static Arguments multipleItemsWithThreeForTwoDiscount() {
        return Arguments.of("multiple items, 3-4-2 discount", "3.02",
                Arrays.asList(aPintOfMilk(), aPackOfDigestives(), aPintOfMilk(), aPintOfMilk(), aPintOfMilk()),
                Collections.singletonList(threeForTwo("milk")));
    }

    private static Arguments multipleItemsWithMultipleDiscounts() {
        return Arguments.of("multiple items, multiple discounts", "4.27",
                Arrays.asList(aPintOfMilk(), aPackOfDigestives(), aPintOfMilk(), twoFiftyGramsOfAmericanSweets(), aPintOfMilk(), aPackOfDigestives(), aPintOfMilk()),
                Arrays.asList(twoForOne("digestives"), threeForTwo("milk")));
    }

    private static Arguments noItemsNoDiscounts() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product("milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product("digestives", new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct("americanSweets", new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static Discount twoForOne(String productCode) { return new Discount(productCode, 2, new BigDecimal("0.5")); }

    private static Discount threeForTwo(String productCode) { return new Discount(productCode, 3, new BigDecimal("0.33")); }
}
