package Strategy_Pattern_Demo;

import java.util.ArrayList;
import java.util.List;

interface PaymentStrategy {
  void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
  private final String cardNumber;
  private final String cardHolder;

  public CreditCardPayment(String cardNumber, String cardHolder) {
    this.cardNumber = cardNumber;
    this.cardHolder = cardHolder;
  }

  @Override
  public void pay(int amount) {
    System.out.println("Paid " + amount + " using Credit Card (Card: " + mask(cardNumber) + ").");
  }

  private String mask(String card) {
    if (card.length() <= 4)
      return card;
    return "****-****-****-" + card.substring(card.length() - 4);
  }
}

// PayPalPayment.java
class PayPalPayment implements PaymentStrategy {
  private final String email;

  public PayPalPayment(String email) {
    this.email = email;
  }

  @Override
  public void pay(int amount) {
    System.out.println("Paid " + amount + " using PayPal (Account: " + email + ").");
  }
}

// UPIPayment.java
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

// Item.java
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
  private PaymentStrategy paymentStrategy; // current strategy

  public void addItem(Item item) {
    items.add(item);
    System.out.println("Added item worth " + item.getPrice());
  }

  public int calculateTotal() {
    return items.stream().mapToInt(Item::getPrice).sum();
  }

  // inject or change strategy at runtime
  public void setPaymentStrategy(PaymentStrategy strategy) {
    this.paymentStrategy = strategy;
  }

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

public class Strategy_Pattern_Demo {

  public static void main(String[] args) {
    ShoppingCart cart = new ShoppingCart();

    cart.addItem(new Item("Book", 40));
    cart.addItem(new Item("Shirt", 60));

    // Use Credit Card
    cart.setPaymentStrategy(new CreditCardPayment("1234123412341234", "Alice"));
    cart.pay(); // Paid 100 using Credit Card

    // Switch to PayPal (same cart, strategy changed at runtime)
    cart.setPaymentStrategy(new PayPalPayment("alice@example.com"));
    cart.pay(); // Paid 100 using PayPal

    // Switch to UPI
    cart.setPaymentStrategy(new UPIPayment("alice@upi"));
    cart.pay(); // Paid 100 using UPI
  }

}
