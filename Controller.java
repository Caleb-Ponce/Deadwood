import java.util.*;
// controller parses user input to send to the game
// and sends messages to the user
public class Controller{

  GameManager manager;
  // add veiw to this later

  public void start(GameManager manager){
    this.manager = manager;
    manager.startGame();
  }
  /*
   prints a message to the console and waits for return int
   optionally can be given a String[], else pass null
   prints options with format

   message here
   [1] the first option
   [2] the second option

   if options specified it continues to prompt the user until
   the user inputs one of the option numbers
   else it returns the first int given
   */
  public int getInputInt(String message, String[] options){
      Scanner sc= new Scanner(System.in);
      System.out.println(message);
      // optional print out individual options
      if (options != null) {
        for (int i = 0; i < options.length; i++) {
          int printI = i + 1;
          System.out.println( "[" + printI +"] " + options[i]);
        }
        int optionPicked = sc.nextInt();
        while (optionPicked < 1 || optionPicked > options.length) {
          System.out.println("Please choose a number between 1 and " + options.length);
          optionPicked = sc.nextInt() - 1;
        }
        return optionPicked;
      } else {
        // may need to catch invalid input or input that isnt a number
        return sc.nextInt();
      }
  }
  public int getInputString(String message, String[] options){
    Scanner sc= new Scanner(System.in);
    System.out.println(message);
    // optional print out individual options
    if (options != null) {
      for (int i = 0; i < options.length; i++) {
        int printI = i + 1;
        System.out.println( "[" + printI +"] " + options[i]);
      }
      int optionPicked = sc.nextInt();
      while (optionPicked < 1 || optionPicked > options.length) {
        System.out.println("Please choose a number between 1 and " + options.length);
        optionPicked = sc.nextInt() - 1;
      }
      return optionPicked;
    } else {
      // may need to catch invalid input or input that isnt a number
      return sc.nextInt();
    }
}


}
