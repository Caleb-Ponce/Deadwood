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
      Controller controller = new Controller();
      Room[] neighbors = this.room.getNeighbors();
      String message = "choose where to go.";
      int responce = controller.getInputInt(message, neighbors);
      if( responce == 0){
        //needs logic for in a room with a scene or in the casting office!!!!!
        continue;
      }else{
        Room newRoom = board.getRoom(neighbors[responce]);
        this.setRoom(newRoom);
        if(this.room.getScene() == Null){
          System.out.print("You have entered a room, there is no scene in this room.");
        }else if(this.room.getScene() != Null){
          // make this its own command
          ArrayList<Position> canPoses = this.room.getScene().checkCanJoin;
          if (canPoses.isEmpty()) {
            System.out.println("sorry no open positions that you can join in this room");
          } else {
            String[] poseOptions = new String[canPoses.size() + 1];
            poseOptions[0] = "CANCEL";
            for (int i = ; i <= canPoses.size(); i++) {
              String name = canPoses.get(i).getName();
              String rank = String.valueOf(canPoses.get(i).getRank());
              String oncard = "";
              if (canPoses.get(i).getOnCard) {
                oncard = " on Scene";
              } else {
                oncard = " off Scene";
              }
              poseOptions[i] = name + " " + rank + oncard;
            }
            String message2 = "There is a Scene you can join, Please choose a Position";
            int join = controller.getInputInt(message2, poseOptions);
            if (join != 0) {
              canPoses.get(join).setPlayer(this);
              this.onCard = true;
              System.out.println("successfully joined scene with position" + poseOptions[join]);
            }
          }
        }
      }
    }


    public void act(){
      // calls the scene to act
      this.room.getScene().act(this);
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
    public void addMoney(int amount){
      this.money += amount;
    }
}
