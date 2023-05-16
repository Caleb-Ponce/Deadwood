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

    public void act(Player player) {
      Controller controller = new Controller();
      boolean playerCounters = this.checkPlayerCounters();
      if (playerCounters) {
        System.out.println("Looks like you have rehearsed enough to already pass the scene");
        payForWork(player);
        this.room.removeShotCounter();
      } else {
        String message = "Looks like you are tied up on a scene would you like to: "
        String[] actRehearse = new String[]{"Act", "Rehearse"};
        int option = controller.getInputInt(message, actRehearse);
        if (option == 0) {
          int roll = (int)((Math.random() * (6 - 1)) + 1) + player.getRehearsalCounters();
          if (roll >= this.budget) {
            // Print success
            System.out.println("Your roll: " + String.valueOf(roll) + ", Success!!!");
            this.payForWork(player);
          } else {
            //printFail
            this.payForWork(player);
          }
        } else if (option == 1) {
          this.addRehearsalCounter();
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
      payBonus(currentPlayers);
      while (!this.currentPlayers.isEmpty()) {
        this.currentPlayers.get(0).setOnCard(false);
        this.currentPlayers.remove(0);
      }
      //pay out players and remove them from the scene
      this.room.removeScene();
    }
    public void payBonus(){
        //probably should check all of the players on the different positions based on positions
        //doesnt need to take in players
        // redo not how to pay out needs to roll dice up to the budget then pay highest to lowest in oder on card.
        // also you only want to pay out players that are on positions that are on the cards
        // player.onCard only lets you know that they are on scene not wether their position is on the card
        // check if there is a player on card.
        boolean playerOnCard = false;
        for (Position pos : this.positionsOnCard) {
          if (pos.getPlayer() != null) {
            playerOnCard = true;
          }
        }
        if (!playerOnCard) {
          System.out.println("no one on the card no payments made");
          return;
        }
        int[] payments = new int[this.budget];
        Random random = new Random();
        for(int i = 0; i < this.budget; i++){
          payments[i] = random.nextInt(6 - 1) + 1;
        }
        Arrays.sort(payments);
        int j = this.positionsOnCard.length() - 1;
        for(int i = 0; i < this.budget; i++) {
          if (j < 0) {
            j = this.positionsOnCard.length() - 1;
          }
          if (this.positionsOnCard[j].getPlayer() == null) {
            i--;
          } else {
            this.positionsOnCard[j].getPlayer().addMoney(payments[i]);
          }
          j--;
        }
    }

    public void payForWork(Player player){
      // not sure this is how people get paid
      // pos has a boolean for whether or not it is on card
      for(int i = 0; i< player.len(); i++){
        this.player.money += this.position.rank;
      }

    }

    public ArrayList<Position> checkCanJoin(Player player) {
      ArrayList<Position> canPoses = new ArrayList<Position>();
      for (int i = 0; i < this.positionsOnCard.getLength(); i++) {
        if (this.positionsOnCard[i].getPlayer() != null && player.getRank() >= this.positionsOnCard[i].getRank()) {
          canPoses.add(this.positionsOnCard[i]);
        }
      }
      for (int i = 0; i < this.positionsOffCard.getLength(); i++) {
        if (this.positionsOffCard[i].getPlayer() != null && player.getRank() >= this.positionsOffCard[i].getRank()) {
          canPoses.add(this.positionsOffCard[i]);
        }
      }
    }


    public void rehearse(Player player){
      player.addRehearsalCounter();
    }
    public void addPlayer(){
      this.currentPlayers.add(this.player);
    }

    public String getName() {
      return this.name;
    }
    public Position[] getpositions() {
      return this.positions;
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
}
