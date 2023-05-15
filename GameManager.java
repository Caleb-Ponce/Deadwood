public class GameManager{
  Controller control;
  Player[] players;
  Board board;
  int numbPlayers;
  int days;
  int currentRound;

  public void startGame() {
    this.board = new Board();
    // get how many players
    String message = "please enter the number of players: ";
    String[] testOptions = new String[]{"CANCEL", "2 Players", "3 Players", "4 Players", "5 Players", "6 Players", "7 Players", "8 Players"};
    this.numbPlayers = this.control.getInputInt(message, testOptions);
    if (this.numbPlayers == 0) {
      return;
    }
    this.numbPlayers++;
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
        Player player = new Player(0, 2);
        this.players[i] = player;
      } else if (numbPlayers == 6) {
        Player player = new Player(0, 4);
        this.players[i] = player;
      } else if (numbPlayers == 7 || numbPlayers == 8) {
        Player player = new Player(2);
        this.players[i] = player;
      } else {
        Player player = new Player();
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
    for(int i = 0; i< players.len(); i++){
      move()
      
    }

  }

  public void resetBoard() {
    String trailer = "Trailer";
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
  }

  public Player[] getPlayers() {
      return this.players;
  }

  public void endGame(){
    //TODO
    //calc player scores decide winner
    // 1 point for each $, 1 point for each credit
    //5 points x rank
    List<Integer> finalScores;
    for (int i = 0; i<players.len(); i++){
      finalScores[i] = players[i].Money + players[i].Credits + (players[i].Rank*5);
    }


  }

  public void setController(Controller control) {
    this.control = control;
  }

  public Board getBoard() {
    return this.board;
  }



}
