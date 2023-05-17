import java.util.*;
public class GameManager{
  // probably make this singleton
  public Player[] players;
  public Board board;
  public int numbPlayers;
  public int days;
  public int currentRound;
  public boolean endGame;
  private static GameManager manager = null;

  public static GameManager getGameManager() {
    if (manager == null) {
      manager = new GameManager();
    } else {
      return manager;
    }
    return manager;
  }

  public void startGame(String numPlayers) {
    Controller control = new Controller();
    this.board = new Board();
    // get how many players
    this.numbPlayers = Integer.parseInt(numPlayers);
    if (this.numbPlayers < 2 || this.numbPlayers > 8) {
      System.out.println("number of players out of bounds");
      String message = "please enter the number of players: ";
      String[] testOptions = new String[]{"CANCEL", "2 Players", "3 Players", "4 Players", "5 Players", "6 Players", "7 Players", "8 Players"};
      this.numbPlayers = control.getInputInt(message, testOptions);
      this.numbPlayers++;
      if (this.numbPlayers == 0) {
        return;
      }
    }

    if (numbPlayers == 2 || numbPlayers == 3) {
      this.days = 3;
    } else {
      this.days = 4;
    }
    this.players = new Player[numbPlayers];
    this.currentRound = 1;
    //create players
    for (int i = 0; i < numbPlayers; i++) {
      if (numbPlayers == 5) {
        Player player = new Player(0, 2, this.board.getRoom("trailer"));
        this.players[i] = player;
      } else if (numbPlayers == 6) {
        Player player = new Player(0, 4, this.board.getRoom("trailer"));
        this.players[i] = player;
      } else if (numbPlayers == 7 || numbPlayers == 8) {
        Player player = new Player(2, this.board.getRoom("trailer"));
        this.players[i] = player;
      } else {
        Player player = new Player(this.board.getRoom("trailer"));
        this.players[i] = player;
      }
    }
    // set up Board
    this.resetBoard();
    // go to game loop
    this.gameLoop();
  }

  public void gameLoop(){
    // constant loop while the game is going
    // loops from one player to the next

    //print game information here.
    System.out.println("Welcome to DeadWood");
    System.out.println("We are playing with " + this.numbPlayers + " players");
    System.out.println("We will play " + this.days + " days");
    System.out.println("The current day is " + this.currentRound);

    //print player locations

    Controller control = new Controller();
    int i = 0;
    while(!this.endGame){
      // if in casting office
      // this.player makes no sense
      if (i >= this.players.length) {
        i = 0;
      }
      Player curPlayer = this.players[i];
      System.out.println();
      System.out.println("Player " + curPlayer.getName() + "'s turn!");
      System.out.println("You have; dollars: " + curPlayer.getMoney() + ", Credits: "+ curPlayer.getCredits() + ", Rank: " + curPlayer.getRank());
      System.out.println();
      if (curPlayer.getOnCard()) {
        curPlayer.act();
      } else {
        boolean move = true;
        String message = "What would you like to do: ";
        String[] options = new String[]{"Don't Move", "Move", "End Turn", "Display rank prices", "Print Player locations", "End Game"};
        while (true) {
          // change some options based on the room

          if (curPlayer.getRoom().getScene() == null) {
            options[0] = "Don't Move";
          }
          if (curPlayer.getRoom().getScene() != null) {
            options[0] = "Try to Join Scene";
          }
          if (curPlayer.getRoom().getName().equals("office")) {
            options[0] = "Try to Upgrade";
          }
          if (curPlayer.getOnCard()) {
            options[0] = "Get Scene Info";
          }

          int action = control.getInputInt(message, options);
          if (action == 0) {
            // dont move
            if (curPlayer.getOnCard()){
              Scene scene = curPlayer.getRoom().getScene();
              System.out.println("Scene Name: "  + scene.getName() + ", Description: ");
              System.out.println(scene.getDescription());
              System.out.println("This scene has a budget of " + scene.getBudget() + " and " + curPlayer.getRoom().getShotCounters() + " shot counters left");
              System.out.println(curPlayer.getName() + " currently has " + curPlayer.getRehearsalCounters() + " Rehearsal counters");
            } else if (curPlayer.getRoom().getName().equals("office")) {
              // in the casting office
              System.out.println("Your current rank is: " + curPlayer.getRank());
              this.board.getCastingOffice().promptPlayer(curPlayer);
            } else if (curPlayer.getRoom().getScene() != null) {
              // in a room with a scene
              curPlayer.getRoom().getScene().joinScene(curPlayer);
              if (curPlayer.getOnCard()) {
                move = false;
              }
            }
          } else if (action == 1) {
            // move
            if (move) {
              curPlayer.move();
              move = false;
            } else {
              System.out.println();
              System.out.println("Sorry no moves left");
              System.out.println();
            }
          } else if (action == 2) {
            // end turn
            break;
          } else if (action == 3) {
            // get upgrade costs
            System.out.println("Your current rank is: " + curPlayer.getRank());
            this.board.getCastingOffice().printRanks();
          } else if (action == 4) {
            // get player locations
            this.printPlayerLocations();
          } else if (action == 5) {
            // get player locations
            this.endGame();
            break;
          }
        }
      }
      i++;
    }
  }

  public void printPlayerLocations() {
    System.out.println();
    System.out.println("Scenes left on the board: " + this.board.getSceneCount());
    for (Player player : this.players) {
      System.out.println(player.getName() + " is in the " + player.getRoom().getName());
    }
    System.out.println();
  }

  public void resetBoard() {
    String trailer = "trailer";
    for (Player player : players) {
      this.getBoard().movePiece(trailer, player);
      player.rehearsalZero();
      player.setOnCard(false);
    }
    board.clearScenes();
    board.placeNewScenes();
  }

  public void endDay() {
    this.resetBoard();
    this.currentRound++;
    if (this.currentRound > this.days) {
      this.endGame();
    }
    System.out.println();
    System.out.println("The Day has ended");
    System.out.println("It is now day number " + this.currentRound);
    System.out.println("All players are now in the trailer");
    System.out.println();
  }

  public Player[] getPlayers() {
      return this.players;
  }

  public void endGame() {
    Integer[] finalScores = new Integer[numbPlayers];
    int biggest = 0;
    int winner = 0;
    for (int i = 0; i < players.length; i++){
      finalScores[i] = this.players[i].getMoney() + this.players[i].getCredits() + (this.players[i].getRank()*5);
      System.out.println(this.players[i].getName() + ": " + finalScores[i]);
      if (finalScores[i] > biggest) {
        biggest = finalScores[i];
        winner = i;
      }
    }
    //check for tie
    boolean tie = false;
    ArrayList<Player> tiers = new ArrayList<Player>();
    for (int i = 0; i < players.length; i++){
      if (i != winner) {
        if (finalScores[i] == biggest){
          tiers.add(this.players[i]);
          tie = true;
        }
      }
    }
    if (tie) {
      System.out.println("There is a TIE!!!");
      System.out.println("The winners are");
      for (int i = 0; i < tiers.size(); i++){
        System.out.println(tiers.get(i).getName());
      }
      System.out.println("with " + biggest + " points!!!");
    } else {
      System.out.println("Player " + winner + ": wins with " + finalScores[winner] + " points");
    }
    this.endGame = true;
  }

  public Board getBoard() {
    return this.board;
  }



}
