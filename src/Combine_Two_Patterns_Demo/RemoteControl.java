package Combine_Two_Patterns_Demo;

import java.util.HashMap;
import java.util.Map;

public class RemoteControl {
  private final Map<String, Command_Interface> buttons = new HashMap<>();

  public void setCommand(String buttonName, Command_Interface command) {
    buttons.put(buttonName, command);
  }

  public void pressButton(String buttonName) {
    Command_Interface command = buttons.get(buttonName);
    if (command != null) {
      command.execute();
    } else {
      System.out.println("No command assigned to button: " + buttonName);
    }
  }

}
