public class Player{
    private int money;
    private int credits;
    private int rank;
    private int rehearsalCounters;

    public void Player(){

    }
    public void Player(int Money, int Credits){

    }
    public void Player(int rank){

    }
    public void move(){

    }
    public void act(){

    }
    public void upgrade(){

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
}
