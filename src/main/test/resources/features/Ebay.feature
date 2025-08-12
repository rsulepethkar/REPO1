Feature: Add item to cart on ebay.com

  Scenario: Verify item can be added to Cart
    Given I open the ebay homepage
    When I search for "book"
    And I click on the first book and add to cart
    Then the cart should show at least one item