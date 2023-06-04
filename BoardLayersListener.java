/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class BoardLayersListener extends JFrame {

  // JLabels
  JLabel boardlabel;
  JLabel cardlabel;
  //JLabel playerlabel;
  JLabel mLabel;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  
  // JLayered Pane
  static JLayeredPane bPane;
  
   static int playerNum = 1;
   static int cardNum = 1;
   static JLabel[] playerJLabels = new JLabel[10];
   static JLabel[] cardJLabels = new JLabel[11];
   static int up = 0;
   static int right = 0;
   static int shotRight = 0;

  // Constructor
  
  public BoardLayersListener() {
      
       // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
    
       // Create the deadwood board
       boardlabel = new JLabel();
       ImageIcon icon =  new ImageIcon("images/board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
      
       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight());
       
       // Add a scene card to this room
       cardlabel = new JLabel();
       ImageIcon cIcon =  new ImageIcon("images/cards/cardback.png");
       cardlabel.setIcon(cIcon); 
       cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
       cardlabel.setVisible(true);
      
       // Add the card to the lower layer
       bPane.add(cardlabel, new Integer(1));
  }
  


   public static char addDice(){
      char c = 'b';
      if(playerNum == 1){
         c = 'b';
      }else if(playerNum == 2){
         c = 'g';
      }else if(playerNum == 3){
         c = 'o';
      }else if(playerNum == 4){
         c = 'p';
      }else if(playerNum == 5){
         c = 'r';
      }else if(playerNum == 6){
         c = 'v';
      }else if(playerNum == 7){
         c = 'w';
      }else if(playerNum == 8){
         c = 'y';
      }
       // Add a dice to represent a player. 
       // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
       
       ImageIcon pIcon = new ImageIcon("images/dice/" + c + "1.png");
       JLabel playerlabel = new JLabel(pIcon);
       playerlabel.setBounds(Integer.valueOf(Board.getRoom("trailer").getArea()[0])+ 49*right,Integer.valueOf(Board.getRoom("trailer").getArea()[1]) + 49*up,
                              pIcon.getIconWidth(),pIcon.getIconHeight());
       //add buffer to make sure the players dont overlap
       if (up == 0){
         up++;
       }else if(right == 4 && up == 1){
         up = 0;
         right = 0;
       }else{
         up = 0;
         right++;
       }
       
       playerlabel.setVisible(true);
       bPane.add(playerlabel,new Integer(3));
       playerJLabels[playerNum] = playerlabel;
       playerNum++;
       return c;

   }
   public static int getPlayersNum(){
      //saved in player to remember which dice attaches to which player
      return playerNum;
   }

   public static void addCard(String roomName){
      cardNum = getCardNum(roomName);
   if(cardJLabels[cardNum] != null){
      cardJLabels[cardNum].setVisible(false);
   }
      ImageIcon cIcon = new ImageIcon("images/cards/cardBack.jpg");
      JLabel cardlabel = new JLabel(cIcon);
      cardlabel.setBounds(Integer.valueOf(Board.getRoom(roomName).getArea()[0]),Integer.valueOf(Board.getRoom(roomName).getArea()[1]),
                              cIcon.getIconWidth(),cIcon.getIconHeight());


      cardlabel.setVisible(true);
      bPane.add(cardlabel,new Integer(2));
      cardJLabels[cardNum] = cardlabel;
   }
   public static void flipCard(int sceneNum, String roomName){
      cardNum = getCardNum(roomName);
      cardJLabels[cardNum].setVisible(false);
      ImageIcon cIcon = new ImageIcon("images/cards/" + sceneNum +".png");
      JLabel cardlabel = new JLabel(cIcon);
      cardlabel.setBounds(Integer.valueOf(Board.getRoom(roomName).getArea()[0]),Integer.valueOf(Board.getRoom(roomName).getArea()[1]),
                              cIcon.getIconWidth(),cIcon.getIconHeight());


      cardlabel.setVisible(true);
      bPane.add(cardlabel,new Integer(2));
      cardJLabels[cardNum] = cardlabel;
   }
   public static int getCardNum(String roomName){
      
      if(roomName.equals("Train Station")){
         return 1;
      }else if(roomName.equals("Secret Hideout")){
         return 2;
      }else if(roomName.equals("Church")){
         return 3;
      }else if(roomName.equals("Hotel")){
         return 4;
      }else if(roomName.equals("Main Street")){
         return 5;
      }else if(roomName.equals("Jail")){
         return 6;
      }else if(roomName.equals("General Store")){
         return 7;
      }else if(roomName.equals("Ranch")){
         return 8;
      }else if(roomName.equals("Bank")){
         return 9;
      }else if(roomName.equals("Saloon")){
         return 10;
      }else{
         return 0;
      }
   }

   public static void placeShot(String roomName){
      System.out.print("did we make it?");
      ImageIcon cIcon = new ImageIcon("images/shot.png");
      JLabel cardlabel = new JLabel(cIcon);
      
      Board.getRoom(roomName).getTakes(0);
      cardlabel.setBounds(Integer.valueOf(Board.getRoom(roomName).getArea()[0]) + shotRight*50,Integer.valueOf(Board.getRoom(roomName).getArea()[1]),
                              cIcon.getIconWidth(),cIcon.getIconHeight());

      if (shotRight <= 5){
         shotRight++;
      }else{
         shotRight=0;
      }
       
      
      cardlabel.setVisible(true);
      bPane.add(cardlabel,new Integer(3));
   }
   public static void movePlayer(int playerNum, String roomName, int rank, char c){

      playerJLabels[playerNum].setVisible(false);
      ImageIcon pIcon = new ImageIcon("images/dice/" + c + rank + ".png");
      JLabel playerlabel = new JLabel(pIcon);
      playerlabel.setBounds(Integer.valueOf(Board.getRoom(roomName).getArea()[0])+ 49*right,Integer.valueOf(Board.getRoom(roomName).getArea()[1]) + 49*up,
                              pIcon.getIconWidth(),pIcon.getIconHeight());
                              if (up == 0){
                                 up++;
                               }else if(right == 4 && up == 1){
                                 up = 0;
                                 right = 0;
                               }else{
                                 up = 0;
                                 right++;
                               }

      playerlabel.setVisible(true);
      bPane.add(playerlabel,new Integer(3));
      playerJLabels[playerNum] = playerlabel;
   }

   public static void rankUp(int playerNum, int rank, char c){
      playerJLabels[playerNum].setVisible(false);
      ImageIcon pIcon = new ImageIcon("images/dice/" + c + rank + ".png");
      JLabel playerlabel = new JLabel(pIcon);
      playerlabel.setBounds(Integer.valueOf(Board.getRoom("office").getArea()[0])+ 49*right,Integer.valueOf(Board.getRoom("office").getArea()[1]) + 49*up,
                              pIcon.getIconWidth(),pIcon.getIconHeight());

      if (up == 0){
         up++;
       }else if(right == 4 && up == 1){
         up = 0;
         right = 0;
       }else{
         up = 0;
         right++;
       }

      playerlabel.setVisible(true);
      bPane.add(playerlabel,new Integer(3));
      playerJLabels[playerNum] = playerlabel;
      
   }



   public static int getPlayerNum(BoardLayersListener board){
    // Take input from the user about number of players
    int playerNum = Integer.valueOf(JOptionPane.showInputDialog(board, "How many players?"));
    return(playerNum);
   }

   public static String getPlayerName(BoardLayersListener board){
   // Take input from the user about number of players
   String playerName = JOptionPane.showInputDialog(board, "Enter name for player:");
   return(playerName);
   }

   public static void errorMessage(BoardLayersListener board){

      
      JOptionPane.showMessageDialog(board, "please enter a number between 2 and 8",
      "Swing Tester", JOptionPane.ERROR_MESSAGE);
      
     }
  public static BoardLayersListener createBoard() {
  
    BoardLayersListener board = new BoardLayersListener();
    board.setVisible(true);
    
    return(board);
  }
}