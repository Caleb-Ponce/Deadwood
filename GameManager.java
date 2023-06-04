import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class GameManager{
  // probably make this singleton
  public Player[] players;
  public Board board;
  public BoardLayersListener boardLayersListener;
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

  public void startGame(int numPlayers) {
    
    this.board = new Board();
    //set up board
    BoardLayersListener gameBoard = BoardLayersListener.createBoard();
    // get how many players
    //this.numbPlayers = Integer.valueOf(JOptionPane.showInputDialog(board, "How many players?"));
    numbPlayers = BoardLayersListener.getPlayerNum(gameBoard);
    if (this.numbPlayers < 2 || this.numbPlayers > 8) {
      boardLayersListener.errorMessage(gameBoard);
      this.numbPlayers = BoardLayersListener.getPlayerNum(gameBoard);
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
    
    JFrame popup = new JFrame("popup");

    JOptionPane.showMessageDialog(popup, "Welcome to DeadWood\nWe are playing with " + this.numbPlayers + 
                                         " players\nWe will play " + this.days + " days\nThe current day is " + this.currentRound);

    //print player locations

   
    int i = 0;
    while(!this.endGame){
      // if in casting office
      // this.player makes no sense
      if (i >= this.players.length) {
        i = 0;
      }
      Player curPlayer = this.players[i];

      JOptionPane.showMessageDialog(popup, "Player " + curPlayer.getName() + "'s turn! \nYou have; \nDollars: "+
                                           curPlayer.getMoney() + " \nCredits: "+ curPlayer.getCredits() + "\nRank: " + curPlayer.getRank());


      if (curPlayer.getOnCard()) {
        curPlayer.act();
      } else {
        boolean move = true;
        String[] options = new String[]{"Don't Move", "Move", "End Turn", "End Game"};
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


          int action = JOptionPane.showOptionDialog(popup,
          "What would you like to do: ",
          curPlayer.getName() + "'s Turn",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[2]);

          if (action == 0) {
            // dont move
            if (curPlayer.getOnCard()){
              Scene scene = curPlayer.getRoom().getScene();
              JOptionPane.showMessageDialog(popup, "Scene Name: "  + scene.getName() + ", Description: \n" + scene.getDescription() + "\nThis scene has a budget of " + scene.getBudget() + 
                                           " and " + curPlayer.getRoom().getShotCounters() + " shot counters left");
            } else if (curPlayer.getRoom().getName().equals("office")) {
              // in the casting office
              JOptionPane.showMessageDialog(popup, "Your current rank is: " + curPlayer.getRank());
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
              JOptionPane.showMessageDialog(popup, "No Moves Left.",
        "No More Moves :(", JOptionPane.ERROR_MESSAGE);
            }
          } else if (action == 2) {
            // end turn
            break;
          } else if (action == 3) {
            // end game
            this.endGame();
            break;
          }
        }
      }
      i++;
    }
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
    JFrame popup = new JFrame("popup");
    this.resetBoard();
    this.currentRound++;
    if (this.currentRound > this.days) {
      this.endGame();
    }
    JOptionPane.showMessageDialog(popup, "The Day has ended\nIt is now day number " + this.currentRound + "All players are now in the trailer");
  }

  public Player[] getPlayers() {
      return this.players;
  }

  public void endGame() {
    JFrame popup = new JFrame("popup");
    Integer[] finalScores = new Integer[numbPlayers];
    int biggest = 0;
    int winner = 0;
    for (int i = 0; i < players.length; i++){
      finalScores[i] = this.players[i].getMoney() + this.players[i].getCredits() + (this.players[i].getRank()*5);
      //System.out.println(this.players[i].getName() + ": " + finalScores[i]);
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
      String[] winners = new String[9];
      for (int i = 0; i < tiers.size(); i++){
        winners[i]=(tiers.get(i).getName());
      }
      JOptionPane.showMessageDialog(popup, "There is a TIE!!!\nThe winners are "+ winners[0] + " with " + biggest + " points!!!");

    } else {
      JOptionPane.showMessageDialog(popup, "Player " + (winner+1) + ": wins with " + finalScores[winner] + " points");
    }
    this.endGame = true;
  }

  public Board getBoard() {
    return this.board;
  }




}
