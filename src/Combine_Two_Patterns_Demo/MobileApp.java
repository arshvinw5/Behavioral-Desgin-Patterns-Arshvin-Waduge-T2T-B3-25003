package Combine_Two_Patterns_Demo;

class MobileApp implements Observer_Interface {
  @Override
  public void update(String message) {
    System.out.println("[Mobile App Notification] " + message);
  }
}
