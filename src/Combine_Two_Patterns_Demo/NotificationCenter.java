package Combine_Two_Patterns_Demo;

import java.util.ArrayList;
import java.util.List;

public class NotificationCenter {
  private static NotificationCenter instance;
  private final List<Observer_Interface> observers = new ArrayList<>();

  private NotificationCenter() {
  }

  public static NotificationCenter getInstance() {
    if (instance == null) {
      instance = new NotificationCenter();
    }
    return instance;
  }

  public void addObserver(Observer_Interface observer) {
    observers.add(observer);
  }

  public void removeObserver(Observer_Interface observer) {
    observers.remove(observer);
  }

  public void notifyObservers(String message) {
    for (Observer_Interface obs : observers) {
      obs.update(message);
    }
  }

}
