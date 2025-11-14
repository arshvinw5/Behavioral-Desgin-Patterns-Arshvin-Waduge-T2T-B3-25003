package Combine_Two_Patterns_Demo;

public class Combine_Two_Patterns_Demo {
  public static void main(String[] args) {
    // Receivers
    Light livingRoomLight = new Light();
    Fan ceilingFan = new Fan();

    // Observers
    NotificationCenter center = NotificationCenter.getInstance();
    center.addObserver(new MobileApp());
    center.addObserver(new DashboardDisplay());

    // Commands
    Command_Interface lightOn = new LightOnCommand(livingRoomLight);
    Command_Interface fanOn = new LightOnCommand(new Light() {
      @Override
      public void turnOn() {
        System.out.println("Fan is ON");
      }

      @Override
      public void turnOff() {
        System.out.println("Fan is OFF");
      }
    });

    // Invoker
    RemoteControl remote = new RemoteControl();
    remote.setCommand("LightOn", lightOn);
    remote.setCommand("FanOn", fanOn);

    // Execute commands
    remote.pressButton("LightOn");
    remote.pressButton("FanOn");
  }

}
