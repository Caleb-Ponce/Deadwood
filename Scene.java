import java.util.*;
import java.util.Arrays;

public class Scene{
    private String name;
    private Position[] positionsOnCard;
    private Position[] positionsOffCard;
    private String image;
    private int sceneNum; // switch to int
    private String description;
    private int budget; //switch to int
    private Room room;
    private ArrayList<Player> currentPlayers = new ArrayList<Player>();

    public Scene(String name, Position[] positions, String image, String sceneNum, String description, String budget){
        this.name = name;
        this.positionsOnCard = positions;
        this.image = image;
        this.sceneNum = Integer.valueOf(sceneNum);
        this.description = description;
        this.budget = Integer.valueOf(budget);
    }

    public void act(Player player, Position pos) {
      Controller controller = new Controller();
      System.out.println();
      System.out.println("This scene has a budget of " + this.budget + " and " + this.room.getShotCounters() + " shot counters left");
      System.out.println(player.getName() + " currently has " + player.getRehearsalCounters() + " Rehearsal counters");
      System.out.println("Your character is: " + pos.getName() + ", your line is: " + pos.getLine());
      System.out.println();
      boolean playerCounters = this.checkPlayerCounters(player);
      if (playerCounters) {
        System.out.println("Looks like you have rehearsed enough to already pass the roll on the scene");
        System.out.println();
        this.payForWork(player, true, pos.getOnCard());
        this.room.removeShotCounter();
      } else {
        String message = "Looks like you are tied up on a scene would you like to: ";
        String[] actRehearse = new String[]{"Act", "Rehearse"};
        int option = controller.getInputInt(message, actRehearse);
        if (option == 0) {
          int roll = (int)((Math.random() * (6 - 1)) + 1) + player.getRehearsalCounters();
          if (roll >= this.budget) {
            // Print success
            System.out.println();
            System.out.println("Your roll: " + String.valueOf(roll) + ", Success!!!");
            this.payForWork(player, true, pos.getOnCard());
            this.room.removeShotCounter();
          } else {
            //printFail
            System.out.println("Your roll: " + String.valueOf(roll) + ", Whoops");
            this.payForWork(player, false, pos.getOnCard());
          }
        } else if (option == 1) {
          player.addRehearsalCounter();
        }
      }
    }

    public boolean checkPlayerCounters(Player player) {
      // returns true if player rehearsalCounters would auto complete a scene
      if (player.getRehearsalCounters() >= this.budget - 1) {
        return true;
      }
      return false;
    }

    public void endScene(){
      // pay for work happens when a player completes a shot counter
      // this only triggers if all of the shot counters are done
      payBonus();
      while (!this.currentPlayers.isEmpty()) {
        this.currentPlayers.get(0).setOnCard(false);
        this.currentPlayers.get(0).rehearsalZero();
        this.currentPlayers.remove(0);
      }
      //pay out players and remove them from the scene
      this.room.removeScene();
      GameManager.getGameManager().getBoard().lowerSceneCount();
    }

    public void payBonus(){
        //get budget amount of dice rolls and pay those out in order to players on the card

        // check if there is a player on card.
        boolean playerOnCard = false;
        for (Position pos : this.positionsOnCard) {
          if (pos.getPlayer() != null) {
            playerOnCard = true;
          }
        }
        if (!playerOnCard) {
          System.out.println("Scene: " + this.name + " has ended players have been returned to the room");
          System.out.println("no one on the card, no payments made");
          return;
        }
        // logic for paying out players
        int[] payments = new int[this.budget];
        Random random = new Random();
        for(int i = 0; i < this.budget; i++){
          payments[i] = random.nextInt(6 - 1) + 1;
        }
        Arrays.sort(payments);
        int j = this.positionsOnCard.length - 1;
        for(int i = 0; i < this.budget; i++) {
          if (j < 0) {
            j = this.positionsOnCard.length - 1;
          }
          if (this.positionsOnCard[j].getPlayer() == null) {
            i--;
          } else {
            this.positionsOnCard[j].getPlayer().addMoney(payments[i]);
          }
          j--;
        }
        System.out.println("Scene: " + this.name + " has ended players have been returned to the room");
        System.out.println("Players on card paid bonus money");
    }

    public void payForWork(Player player, boolean success, boolean onCard){
      // get paid based on if you were on the card or off and if it was successfull
      if (onCard) {
        if (success) {
          player.addCredits(2);
          System.out.println("You were paid 2 credits");
        }
      } else {
        if (success) {
          player.addMoney(1);
          player.addCredits(1);
          System.out.println("You were paid $1 and 1 credit");
        } else {
          player.addMoney(1);
          System.out.println("You were paid 1 dollar");
        }
      }
    }

    public void joinScene(Player player){
      Controller control = new Controller();
      Position[] openPoses = this.checkCanJoin(player);
      if (openPoses.length == 0) {
        // maybe add logic for the next available position
        System.out.println("Looks like there isn't a spot on the scene in this room that you can join.");
        return;
      }
      System.out.println();
      System.out.println("Scene Name: "  + this.name + ", Description: ");
      System.out.println(this.description);
      System.out.println("This scene has a budget of " + this.budget + " and " + this.room.getShotCounters() + " shot counters left");
      String[] posenames = new String[openPoses.length + 1];
      posenames[0] = "CANCEL";
      for (int i = 1; i <= openPoses.length; i++) {
        String name = openPoses[i-1].getName();
        String rank = String.valueOf(openPoses[i-1].getRank());
        String oncard = "";
        if (openPoses[i-1].getOnCard()) {
          oncard = "On Scene";
        } else {
          oncard = "Off Scene";
        }
        posenames[i] = name + ", rank: " + rank + ", " + oncard;
      }
      String message = "Please choose a Position to join the scene: ";
      int choice = control.getInputInt(message, posenames);
      if (choice == 0) {
        return;
      }
      player.setPose(openPoses[choice - 1]);
      openPoses[choice - 1].setPlayer(player);
      player.setOnCard(true);
      System.out.println("you successfully joined the scene!");
      this.currentPlayers.add(player);
  }


    public Position[] checkCanJoin(Player player) {
      ArrayList<Position> canPoses = new ArrayList<Position>();
      for (int i = 0; i < this.positionsOnCard.length; i++) {
        if (this.positionsOnCard[i].getPlayer() == null && player.getRank() >= this.positionsOnCard[i].getRank()) {
          canPoses.add(this.positionsOnCard[i]);
        }
      }
      for (int i = 0; i < this.positionsOffCard.length; i++) {
        if (this.positionsOffCard[i].getPlayer() == null && player.getRank() >= this.positionsOffCard[i].getRank()) {
          canPoses.add(this.positionsOffCard[i]);
        }
      }
      Position[] canPoses2 = new Position[canPoses.size()];
      canPoses.toArray(canPoses2);
      return canPoses2;
    }

    public void addPlayer(Player player){
      this.currentPlayers.add(player);
    }

    public String getName() {
      return this.name;
    }
    public Position[] getpositionsOnCard() {
      return this.positionsOnCard;
    }
    public Position[] getpositionsOffCard() {
      return this.positionsOffCard;
    }
    public int getSceneNum() {
      return this.sceneNum;
    }
    public String getDescription() {
      return this.description;
    }
    public int getBudget() {
      return this.budget;
    }
    public void setOffCard(Position[] positions){
      this.positionsOffCard = positions;
    }
    public void setRoom(Room room){
      this.room = room;
    }
}
