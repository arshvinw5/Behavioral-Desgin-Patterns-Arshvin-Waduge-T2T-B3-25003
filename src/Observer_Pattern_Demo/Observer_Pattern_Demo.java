package Observer_Pattern_Demo;

import java.util.ArrayList;
import java.util.List;

//creating subscribers interface to make different observers implement it
interface Subscribers {
  void update(float temperature, float humidity);
}

class PhoneDisplay implements Subscribers {
  @Override
  public void update(float temperature, float humidity) {
    System.out.println("Phone Display -> Temperature: " + temperature + "°C, Humidity: " + humidity + "%");
  }
}

class TVDisplay implements Subscribers {
  @Override
  public void update(float temperature, float humidity) {
    System.out.println("TV Display -> Temperature: " + temperature + "°C, Humidity: " + humidity + "%");
  }
}

class WebDashboard implements Subscribers {
  @Override
  public void update(float temperature, float humidity) {
    System.out.println("Web Dashboard -> Temperature: " + temperature + "°C, Humidity: " + humidity + "%");
  }
}

// creating subject interface to make different subjects implement it
interface Subject {
  void addObserver(Subscribers s);

  void removeObserver(Subscribers s);

  void notifyObservers();
}

class WeatherStation implements Subject {
  private List<Subscribers> subscribersList = new ArrayList<>();
  private float temperature;
  private float humidity;

  @Override
  public void addObserver(Subscribers s) {
    subscribersList.add(s);
  }

  @Override
  public void removeObserver(Subscribers s) {
    subscribersList.remove(s);
  }

  @Override
  public void notifyObservers() {
    for (Subscribers s : subscribersList) {
      s.update(temperature, humidity);
    }
  }

  public void setWeatherData(float temperature, float humidity) {
    this.temperature = temperature;
    this.humidity = humidity;
    notifyObservers();
  }
}

public class Observer_Pattern_Demo {
  public static void main(String[] args) throws InterruptedException {
    WeatherStation station = new WeatherStation();

    Subscribers phone = new PhoneDisplay();
    Subscribers tv = new TVDisplay();
    Subscribers web = new WebDashboard();

    // Register observers
    station.addObserver(phone);
    station.addObserver(tv);
    station.addObserver(web);

    // Simulate weather changes
    station.setWeatherData(26.0f, 58.0f);

    // to have delay for notification visibility
    Thread.sleep(2000);
    station.setWeatherData(30.0f, 65.0f);

    // Remove one observer at runtime
    System.out.println("\n--- Removing TV Display ---");
    station.removeObserver(tv);

    // to have delay for notification visibility
    Thread.sleep(2000);
    station.setWeatherData(28.0f, 60.0f);
  }
}