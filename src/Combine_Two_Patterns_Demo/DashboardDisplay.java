package Combine_Two_Patterns_Demo;

class DashboardDisplay implements Observer_Interface {
  @Override
  public void update(String message) {
    System.out.println("[Dashboard] " + message);
  }
}
