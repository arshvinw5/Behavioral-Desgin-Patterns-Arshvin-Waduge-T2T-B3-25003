package Strategy_Pattern_Demo;

import java.util.ArrayList;
import java.util.List;

interface PaymentStrategy {
  void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
  private final String cardNumber;
  private final String cardHolderName;
  private final String cvv;
  private final String expiryDate;

  public CreditCardPayment(String cardNumber, String cardHolderName, String cvv, String expiryDate) {
    this.cardNumber = cardNumber;
    this.cardHolderName = cardHolderName;
    this.cvv = cvv;
    this.expiryDate = expiryDate;
  }

  @Override
  public void pay(int amount) {

    if (!validate()) {
      throw new IllegalArgumentException("Invalid credit card details.");
    } else {
      System.out.println("Paid " + amount + " using Credit Card (Card: " + mask(cardNumber) + ").");
    }
  }

  private String mask(String card) {
    if (card == null || card.length() <= 4)
      return card;

    return "****-****-****-" + card.substring(card.length() - 4);
  }

  public boolean validate() {
    return isValidCardNumber(cardNumber) &&
        isValidCardHolderName(cardHolderName) &&
        isValidCvv(cvv) &&
        isValidExpiryDate(expiryDate);
  }

  private boolean isValidCardNumber(String cardNumber) {
    return cardNumber != null &&
        cardNumber.replaceAll("[^0-9]", "").length() >= 13 &&
        cardNumber.replaceAll("[^0-9]", "").length() <= 19;
  }

  private boolean isValidCardHolderName(String name) {
    return name != null &&
        !name.trim().isEmpty() &&
        name.trim().length() >= 2;
  }

  private boolean isValidCvv(String cvv) {
    return cvv != null &&
        cvv.matches("\\d{3,4}");
  }

  private boolean isValidExpiryDate(String expiryDate) {
    if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
      return false;
    }

    String[] parts = expiryDate.split("/");
    int month = Integer.parseInt(parts[0]);
    int year = 2000 + Integer.parseInt(parts[1]);

    java.time.LocalDate now = java.time.LocalDate.now();
    java.time.LocalDate expiry = java.time.LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);

    return month >= 1 && month <= 12 && expiry.isAfter(now);
  }

}

class PayPalPayment implements PaymentStrategy {
  private final String email;

  public PayPalPayment(String email) {
    this.email = email;
  }

  @Override
  public void pay(int amount) {
    if (!validate()) {
      throw new IllegalArgumentException("Invalid PayPal email.");
    } else {
      System.out.println("Paid " + amount + " using PayPal (Account: " + email + ").");
    }
  }

  private boolean validate() {
    return isValidEmail(email);
  }

  private boolean isValidEmail(String email) {
    return email != null &&
        email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
  }
}

class UPIPayment implements PaymentStrategy {
  private final String upiId;

  public UPIPayment(String upiId) {
    this.upiId = upiId;
  }

  @Override
  public void pay(int amount) {
    System.out.println("Paid " + amount + " using UPI (UPI ID: " + upiId + ").");
  }
}

// simple item class for demonstration
class Item {
  private final String name;
  private final int price;

  public Item(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public int getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}

class ShoppingCart {
  private final List<Item> items = new ArrayList<>();
  // current strategy (can be changed at runtime)
  private PaymentStrategy paymentStrategy;

  // add item to cart
  public void addItem(Item item) {
    items.add(item);
    System.out.println("Added item worth " + item.getPrice());
  }

  // Calculate total
  public int calculateTotal() {
    return items.stream().mapToInt(Item::getPrice).sum();
  }

  // Set or change payment strategy
  public void setPaymentStrategy(PaymentStrategy strategy) {
    this.paymentStrategy = strategy;
  }

  // Pay using current strategy
  public void pay() {
    if (paymentStrategy == null) {
      System.out.println("No payment method selected.");
      return;
    }
    int total = calculateTotal();
    System.out.println("Total: " + total);
    paymentStrategy.pay(total);
  }

}

class Strategy_Pattern_Demo {
  public static void main(String[] args) {
    ShoppingCart cart = new ShoppingCart();

    cart.addItem(new Item("Book", 500));
    cart.addItem(new Item("Pen", 50));
    cart.addItem(new Item("Laptop", 50000));

    // Pay using Credit Card
    cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "John Doe", "123", "12/25"));
    cart.pay();

    // Pay using PayPal
    cart.setPaymentStrategy(new PayPalPayment("john.doe@example.com"));
    cart.pay();

    // Pay using UPI
    cart.setPaymentStrategy(new UPIPayment("john.doe@upi"));
    cart.pay();
  }
}