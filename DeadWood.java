public class DeadWood{

  public static void main(String args[]) {
    Controller control = new Controller();
    GameManager manager = new GameManager();
    manager.setController(control);
    control.start(manager);
  }




}
