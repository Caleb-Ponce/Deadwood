import java.util.Scanner;

public class Player{
    private int money = 0;
    private int credits = 0;
    private int rank = 1;
    private int rehearsalCounters = 0;
    private Room room = null;
    private boolean onCard = false;
    private Position pose = null;
    private String name;

    public Player(Room trailer){
      Scanner sc = new Scanner(System.in);
      System.out.println();
      System.out.print("Enter name for player: ");
      this.name = sc.nextLine();
      this.room = trailer;
    }

    public Player(int money, int credits, Room trailer){
      Scanner sc = new Scanner(System.in);
      System.out.println();
      System.out.print("Enter name for player: ");
      this.name = sc.nextLine();
      this.money = money;
      this.credits = credits;
      this.room = trailer;
    }

    public Player(int rank, Room trailer){
      Scanner sc = new Scanner(System.in);
      System.out.println();
      System.out.print("Enter name for player: ");
      this.name = sc.nextLine();
      this.rank = rank;
      this.room = trailer;
    }

    public void move(){

      Controller controller = new Controller();
      String[] neighbors = this.room.getNeighbors();
      // prompt move to new room
      System.out.println();
      System.out.println("You are currently in the " + this.room.getName());
      String message = "choose where to go: ";
      int responce = controller.getInputInt(message, neighbors);
      Room room = GameManager.getGameManager().getBoard().getRoom(neighbors[responce]);
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
}
