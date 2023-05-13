public class Player{
    private int money = 0;
    private int credits = 0;
    private int rank = 1;
    private int rehearsalCounters = 0;
    private Room room = null;
    private boolean onCard = false;

    public Player(){
    }

    public Player(int money, int credits){
      this.money = money;
      this.credits = credits;
    }

    public Player(int rank){
      this.rank = rank;
    }

    public void move(){

    }
    public void act(){

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
    public int getRehearsalCounters() {
      return this.rehearsalCounters;
    }
    public void addRehearsalCounter() {
      this.rehearsalCounters += 1;
    }
    public void setRoom(Room room) {
      this.room = room;
    }
    public Room getRoom(Room room) {
      return this.room;
    }
    public void rehearsalZero(){
      this.rehearsalCounters = 0;
    }
    public void setOnCard(boolean bool) {
      this.onCard = bool;
    }
}
