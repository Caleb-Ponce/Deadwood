public class DeadWood{

  public static void main(String args[]) {
    GameManager manager = GameManager.getGameManager();
    if (args.length == 0) {
      manager.startGame(10);
    } else {
      manager.startGame(Integer.valueOf(args[0]));
    }
  }
}
