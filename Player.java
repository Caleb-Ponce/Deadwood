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
      Room[] neighbors = this.room.getNeighbors();
      String message = "choose where to go.";
      int responce = controller.getInputInt(message, neighbors);
      if( responce == 1){
        continue;
      }else{
        Board newRoom = board.getRoom(neighbors[responce]);
        setRoom(newRoom);
        if(Room.getScene() == Null){
          System.out.print("You have entered a room, there is no scene in this room.")
        }else if(Room.getScene != Null){
          Position[] choices = Room.getPositions()
          String message = "You have entered a room, choose a position.";
          int responce = Controller.getInputInt(message, choices);
          if(/*position chosen is avalable*/){
            /*take position */
            onCard = true;
          }else(
            continue;
          )
        }
      }
    }
    public void act(){
      int roll = (int)(Math.random()*6+1);
      if(roll > this.scene.getBudget()){
        this.Room.removeShotCounter();
      }
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
