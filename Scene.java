import java.util.*;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
      JFrame popup = new JFrame("popup");
      JOptionPane.showMessageDialog(popup, "This scene has a budget of " + this.budget + " and " + this.room.getShotCounters() + " shot counters left\n" +
                                            player.getName() + " currently has " + player.getRehearsalCounters() + " Rehearsal counters\nYour character is: " 
                                            + pos.getName() + ", your line is: " + pos.getLine());
      boolean playerCounters = this.checkPlayerCounters(player);
      if (playerCounters) {
        JOptionPane.showMessageDialog(popup, "Looks like you have rehearsed enough to already pass the roll on the scene");
        this.payForWork(player, true, pos.getOnCard());
        this.room.removeShotCounter();
      } else {
        String[] actRehearse = new String[]{"Act", "Rehearse"};
        int option = JOptionPane.showOptionDialog(popup,
      "Act or Reherse ",
      "Looks like you are tied up on a scene would you like to: ",
      JOptionPane.YES_NO_CANCEL_OPTION,
      JOptionPane.QUESTION_MESSAGE,
      null,
      actRehearse,
      actRehearse[1]);


        if (option == 0) {
          int roll = (int)((Math.random() * (6 - 1)) + 1) + player.getRehearsalCounters();
          if (roll >= this.budget) {
            // Print success
            JOptionPane.showMessageDialog(popup, "Your roll: " + String.valueOf(roll) + ", Success!!!");
            this.payForWork(player, true, pos.getOnCard());
            this.room.removeShotCounter();
            BoardLayersListener.placeShot(this.room.getName());
          } else {
            //printFail
            JOptionPane.showMessageDialog(popup, "Your roll: " + String.valueOf(roll) + ", Whoops");
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
        JFrame popup = new JFrame("popup");
        for (Position pos : this.positionsOnCard) {
          if (pos.getPlayer() != null) {
            playerOnCard = true;
          }
        }
        if (!playerOnCard) {
          JOptionPane.showMessageDialog(popup, "Scene: " + this.name + " has ended players have been returned to the room\nNo one on the card, no payments made");
          
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
        JOptionPane.showMessageDialog(popup, "Scene: " + this.name + " has ended players have been returned to the room\nPlayers on card paid bonus money");
    }

    public void payForWork(Player player, boolean success, boolean onCard){
      // get paid based on if you were on the card or off and if it was successfull
      JFrame popup = new JFrame("popup");
      if (onCard) {
        if (success) {
          player.addCredits(2);
          JOptionPane.showMessageDialog(popup, "You were paid 2 credits");
        }
      } else {
        if (success) {
          player.addMoney(1);
          player.addCredits(1);
          JOptionPane.showMessageDialog(popup, "You were paid $1 and 1 credit");
        } else {
          player.addMoney(1);
          JOptionPane.showMessageDialog(popup, "You were paid 1 dollar");
        }
      }
    }

    public void joinScene(Player player){

      JFrame popup = new JFrame("popup");
      Position[] openPoses = this.checkCanJoin(player);
      if (openPoses.length == 0) {
        // maybe add logic for the next available position
        JOptionPane.showMessageDialog(popup, "Looks like there isn't a spot on the scene in this room that you can join.");
        return;
      }
      JOptionPane.showMessageDialog(popup, "Scene Name: "  + this.name + ", Description: \n" + this.description + "\nThis scene has a budget of " + this.budget + 
                                           " and " + this.room.getShotCounters() + " shot counters left");
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
      int choice = JOptionPane.showOptionDialog(popup,
      "What Role? ",
      "Please choose a Position to join the scene: ",
      JOptionPane.YES_NO_CANCEL_OPTION,
      JOptionPane.QUESTION_MESSAGE,
      null,
      posenames,
      posenames[1]);
      if (choice == 0) {
        return;
      }
      player.setPose(openPoses[choice - 1]);
      openPoses[choice - 1].setPlayer(player);
      player.setOnCard(true);
      JOptionPane.showMessageDialog(popup, "You have successfully joined the scene!");
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
