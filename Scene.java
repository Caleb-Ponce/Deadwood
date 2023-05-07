import java.util.*;

public class Scene{
    private String name;
    private Position[] positions;
    private String image;
    private int sceneNum; // switch to int
    private String description;
    private int budget; //switch to int
    private Room room;

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
      //pay out players and remove them from the scene

    }
    public void payBonus(Player[] players){
        //probably should check all of the players on the different positions based on positions
        //doesnt need to take in players
    }
    public void payForWork(Player player){

    }
    public void act(int player, String position){

    }
    public void rehearse(Player player){
      player.addRehearsalCounter();
    }
    public void checkRoll(List<Integer> rolls){

    }
    public void addPlayer(int player, String position){

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
