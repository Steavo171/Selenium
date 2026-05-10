Feature: Demo Web Shop End to End Scenario

  @DemoWebShop
  Scenario: Verify user can search, purchase a product and logout
    Given I launch the application
    When I login with email and password
    And I search for "laptop" and select a product
    And I add the product to cart and complete checkout
    Then I logout from the application