package Observer_Pattern_Demo;

import java.util.ArrayList;
import java.util.List;

interface Observer {
  void update(float temperature, float humidity);
}

class PhoneDisplay implements Observer {
  @Override
  public void update(float temperature, float humidity) {
    System.out.println("Phone Display: Temp = " + temperature + "°C, Humidity = " + humidity + "%");
  }
}

class TVDisplay implements Observer {
  @Override
  public void update(float temperature, float humidity) {
    System.out.println("TV Display: Temp = " + temperature + "°C, Humidity = " + humidity + "%");
  }
}

class WebDashboard implements Observer {
  @Override
  public void update(float temperature, float humidity) {
    System.out.println("Web Dashboard: Temp = " + temperature + "°C, Humidity = " + humidity + "%");
  }
}

interface Subject {
  void addObserver(Observer o);

  void removeObserver(Observer o);

  void notifyObservers();
}

class WeatherStation implements Subject {
  private List<Observer> observers = new ArrayList<>();
  private float temperature;
  private float humidity;

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update(temperature, humidity);
    }
  }

  // Method to change weather data
  public void setWeatherData(float temperature, float humidity) {
    this.temperature = temperature;
    this.humidity = humidity;
    notifyObservers(); // automatically updates all observers
  }
}

public class Observer_Pattern_Demo {

  public static void main(String[] args) throws InterruptedException {
    WeatherStation station = new WeatherStation();

    Observer phone = new PhoneDisplay();
    Observer tv = new TVDisplay();
    Observer web = new WebDashboard();

    // Register observers
    station.addObserver(phone);
    station.addObserver(tv);
    station.addObserver(web);

    // Simulate weather changes
    station.setWeatherData(26.0f, 58.0f);
    Thread.sleep(2000);
    station.setWeatherData(30.0f, 65.0f);

    // Remove one observer at runtime
    System.out.println("\n--- Removing TV Display ---");
    station.removeObserver(tv);

    Thread.sleep(2000);
    station.setWeatherData(28.0f, 60.0f);
  }

}
