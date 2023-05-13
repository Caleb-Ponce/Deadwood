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
   else it returns the first int given based on its position in the options
   if user enters a 1 getInputInt will return 0
   */
  public int getInputInt(String message, String[] options){
      Scanner sc= new Scanner(System.in);
      System.out.println(message);
      int optionPicked = -1;
      String check = "test";
      // optional print out individual options
      if (options != null) {
        for (int i = 0; i < options.length; i++) {
          int printI = i + 1;
          System.out.println( "[" + printI +"] " + options[i]);
        }
        // check input was a number and that it has the right value
        if (sc.hasNextInt()) {
          optionPicked = sc.nextInt() - 1;
          if (optionPicked >= 0 && optionPicked < options.length) {
            return optionPicked;
          }
        }
        // option chosen was to high or too low
        System.out.println("Please choose a number between 1 and " + options.length);
        while (optionPicked < 1 || optionPicked > options.length) {
          while (!sc.hasNextInt()) {
            System.out.println("Please input a number");
            sc.next();
          }
          optionPicked = sc.nextInt();
          System.out.println("Please choose a number between 1 and " + options.length);
        }
        optionPicked--;

      } else {
        // no options just need a number
        if (sc.hasNextInt()) {
          optionPicked = sc.nextInt();
        } else {
          while (!sc.hasNextInt()) {
            System.out.println("Please enter a number");
            sc.nextLine();
          }
          optionPicked = sc.nextInt();
        }
      }
      return optionPicked;
  }


}
