package Command_Pattern_Demo;

import java.util.HashMap;
import java.util.Map;

//creating interface for commands
interface Command {
  void execute();

  void undo();
}

// Receiver ( Device that will perform the action)

class Light {
  private final String location;

  public Light(String location) {
    this.location = location;
  }

  public void on() {
    System.out.println(location + " Light is ON");
  }

  public void off() {
    System.out.println(location + " Light is OFF");
  }

}

class Fan {
  private final String location;

  public Fan(String location) {
    this.location = location;
  }

  public void start() {
    System.out.println(location + " Fan is STARTED");
  }

  public void stop() {
    System.out.println(location + " Fan is STOPPED");
  }
}

// Concrete Command for devices

class LightOnCommand implements Command {

  // creating reference of Light class
  private final Light light;

  public LightOnCommand(Light light) {
    this.light = light;
  }

  @Override
  public void execute() {
    light.on();
  }

  @Override
  public void undo() {
    light.off();
  }
}

class LightOffCommand implements Command {

  // creating reference of Light class
  private final Light light;

  public LightOffCommand(Light light) {
    this.light = light;
  }

  @Override
  public void execute() {
    light.off();
  }

  @Override
  public void undo() {
    light.on();
  }
}

class FanStartCommand implements Command {

  // creating reference of Fan class
  private final Fan fan;

  public FanStartCommand(Fan fan) {
    this.fan = fan;
  }

  @Override
  public void execute() {
    fan.start();
  }

  @Override
  public void undo() {
    fan.stop();
  }
}

class FanStopCommand implements Command {

  // creating reference of Fan class
  private final Fan fan;

  public FanStopCommand(Fan fan) {
    this.fan = fan;
  }

  @Override
  public void execute() {
    fan.stop();
  }

  @Override
  public void undo() {
    fan.start();
  }
}

class RemoteControl {

  // add Hash map for store commands with their slots and to support undo options
  private final Map<String, Command> buttons = new HashMap<>();
  private Command lastCommand = null;

  // method for set command
  public void steCommand(String slot, Command command) {
    buttons.put(slot, command);
  }

  // to trigger command
  public void pressButton(String buttonName) {
    // getting the command from the map
    Command command = buttons.get(buttonName);
    if (command != null) {
      command.execute();
      lastCommand = command;
    } else {
      System.out.println("No command assigned to this button.");
    }
  }

  // Undo the last command

  public void pressUndo() {
    if (lastCommand != null) {
      lastCommand.undo();
    } else {
      System.out.println("No command to undo.");
    }
  }

}

public class Command_Pattern_Demo {
  public static void main(String[] args) {
    // Receivers
    Light livingLight = new Light("Living Room");
    Light bedroomLight = new Light("Bedroom");

    Fan bedroomFan = new Fan("Bedroom");
    Fan kitchenFan = new Fan("Kitchen");

    // Commands
    Command livingLightOn = new LightOnCommand(livingLight);
    Command livingLightOff = new LightOffCommand(livingLight);

    Command bedroomLightOn = new LightOnCommand(bedroomLight);
    Command bedroomLightOff = new LightOffCommand(bedroomLight);

    Command bedroomFanOn = new FanStartCommand(bedroomFan);
    Command bedroomFanOff = new FanStopCommand(bedroomFan);

    Command kitchenFanOn = new FanStartCommand(kitchenFan);
    Command kitchenFanOff = new FanStopCommand(kitchenFan);

    // set commands to the remote control
    RemoteControl remote = new RemoteControl();
    remote.steCommand("Living Light ON", livingLightOn);
    remote.steCommand("Living Light OFF", livingLightOff);
    remote.steCommand("Bedroom Light ON", bedroomLightOn);
    remote.steCommand("Bedroom Light OFF", bedroomLightOff);
    remote.steCommand("Bedroom Fan ON", bedroomFanOn);
    remote.steCommand("Bedroom Fan OFF", bedroomFanOff);
    remote.steCommand("Kitchen Fan ON", kitchenFanOn);
    remote.steCommand("Kitchen Fan OFF", kitchenFanOff);

    // simulate button presses

    remote.pressButton("Living Light ON");
    remote.pressButton("Bedroom Fan ON");
    remote.pressButton("Kitchen Fan ON");

    // Undo last (Kitchen Fan ON -> undo -> Fan OFF)
    System.out.println("\n--- To Display Undo Operations ---");
    remote.pressUndo();
    System.out.println("\n");

    remote.pressButton("Living Light OFF");

    // Undo last (Living Light OFF -> undo -> Light ON)
    System.out.println("\n--- To Display Undo Operations ---");
    remote.pressUndo();

  }
}
