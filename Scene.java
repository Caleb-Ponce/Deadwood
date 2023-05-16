import java.util.*;

public class Scene{
    private String name;
    private Position[] positions;
    private String image;
    private int sceneNum; // switch to int
    private String description;
    private int budget; //switch to int
    private Room room;
    private ArrayList<Player> currentPlayers = new ArrayList<Player>();

    public Scene(String name, Position[] positions, String image, String sceneNum, String description, String budget){
        this.name = name;
        this.positions = positions;
        this.image = image;
        this.sceneNum = Integer.valueOf(sceneNum);
        this.description = description;
        this.budget = Integer.valueOf(budget);
    }

    public boolean checkPlayerCounters(Player player) {
      // returns true if player rehearsalCounters would auto complete a scene
      if (player.getRehearsalCounters() >= this.budget - 1) {
        return true;
      }
      return false;
    }

    public void endScene(){
      payForWork(currentPlayers);
      payBonus(currentPlayers);
      //pay out players and remove them from the scene


    }
    public void payBonus(Player[] players){
        //probably should check all of the players on the different positions based on positions
        //doesnt need to take in players
        for(int i = 0; i < budget; i++){
          if(player.onCard){
            players[i%players.len()].money += Math.random()*6+1;
          }
        }
    }
    public void payForWork(Player[] player){
      for(int i = 0; i< player.len(); i++){
        this.player.money += this.position.rank;
      }
      
    }
    public void rehearse(Player player){
      player.addRehearsalCounter();
    }
    public void addPlayer(){
      this.currentPlayers.add(this.player)
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
}
