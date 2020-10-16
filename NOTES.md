# Notes
David Bayon

Time coding: around 1.25 hours

Points about code:

- If I was doing this properly the first thing I would have done is refactor the TotalCalculator out of the Basket class and properly test it in isolation. Then the DiscountCalculator would also have been its own class, but I stuck with the existing approach for simplicity and speed.
- Having a single Discount class works for x-for-the-price-of-x style discounts, and could be pretty easily adapted to also accept a fixed price rather than a percentage discount.
- I didn't have time to look at the weight-based discount, it may eventually make sense to have different types of Discount classes, each with their own logic.
- Applying a discount to a group rather than a product would require the addition of a productType enum field, and then some logic in the calculator to either filter items by code or type accordingly.