import javax.swing.*;
import javax.swing.JOptionPane;

public class Player{
    private int money = 0;
    private int credits = 0;
    private int rank = 1;
    private int rehearsalCounters = 0;
    private Room room = null;
    private boolean onCard = false;
    private Position pose = null;
    private String name;
    private BoardLayersListener board;
    private char color;
    private int num; 
    

    public Player(Room trailer){
      this.name = BoardLayersListener.getPlayerName(board);
      num = BoardLayersListener.getPlayersNum();
      color = BoardLayersListener.addDice();
      this.room = trailer;
    }

    public Player(int money, int credits, Room trailer){
      this.name = BoardLayersListener.getPlayerName(board);
      BoardLayersListener.addDice();
      this.money = money;
      this.credits = credits;
      this.room = trailer;
    }

    public Player(int rank, Room trailer){
      this.name = BoardLayersListener.getPlayerName(board);
      BoardLayersListener.addDice();
      this.rank = rank;
      this.room = trailer;
    }

    public void move(){
      JFrame popup = new JFrame("popup");
      String[] neighbors = this.room.getNeighbors();
      // prompt move to new room
      int responce = JOptionPane.showOptionDialog(popup,
          "Where would you like to go: ",
          "You are currently in the " + this.room.getName(),
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          neighbors,
          neighbors[2]);
      Room room = GameManager.getGameManager().getBoard().getRoom(neighbors[responce]);
      BoardLayersListener.movePlayer(getnum(), room.getName(), getRank(), getColor());
      if(room.getName() != "office" && room.getName() != "trailer"){
        BoardLayersListener.flipCard(room.getScene().getSceneNum(), room.getName());
      }
      this.room.visit();
      this.room = room;
    }

    public void act(){
      // calls the scene to act
      this.room.getScene().act(this, this.pose);
    }
    public boolean getOnCard() {
      return this.onCard;
    }
    public int getMoney(){
      return this.money;
    }
    public int getCredits(){
      return this.credits;
    }
    public int getRank(){
      return this.rank;
    }
    public void setRank(int rank){
      this.rank = rank;
    }
    public int getRehearsalCounters() {
      return this.rehearsalCounters;
    }
    public void addRehearsalCounter() {
      this.rehearsalCounters += 1;
    }
    public void setRoom(Room room) {
      this.room = room;
    }
    public Room getRoom() {
      return this.room;
    }
    public void rehearsalZero(){
      this.rehearsalCounters = 0;
    }
    public void setPose(Position pos) {
      this.pose = pos;
    }
    public void setOnCard(boolean bool) {
      this.onCard = bool;
    }
    public void addMoney(int amount){
      this.money += amount;
    }
    public void addCredits(int amount){
      this.credits += amount;
    }
    public void subMoney(int amount){
      this.money -= amount;
    }
    public void subCredits(int amount){
      this.credits -= amount;
    }
    public String getName() {
      return this.name;
    }
    public char getColor() {
      return this.color;
    }
    public int getnum() {
      return this.num;
    }
}
