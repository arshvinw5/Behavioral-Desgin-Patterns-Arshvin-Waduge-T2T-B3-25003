package Combine_Two_Patterns_Demo;

public class FanOnCommand implements Command_Interface {
  private Fan fan;

  public FanOnCommand(Fan fan) {
    this.fan = fan;
  }

  @Override
  public void execute() {
    fan.turnOn();
  }

  @Override
  public void undo() {
    fan.turnOff();
  }

}
