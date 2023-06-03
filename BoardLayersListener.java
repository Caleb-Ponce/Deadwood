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
   static JLabel[] playerJLabels = new JLabel[10];
   static int up = 0;
   static int right = 0;
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
       
       if (up == 0){
         up++;
       }else{
         up = 0;
         right++;
       }
       
       playerlabel.setVisible(true);
       bPane.add(playerlabel,new Integer(3));
       playerJLabels[playerNum] = playerlabel;
       //System.out.println("RIGHTHERE\n" + playerJLabels[1]);
       playerNum++;
       return c;

   }

   public static void movePlayer(int temp, String roomName){
      //System.out.println("RIGHTHERE\n" + temp + playerJLabels[temp]);
      playerJLabels[temp].setLocation(Integer.valueOf(Board.getRoom(roomName).getArea()[0]), Integer.valueOf(Board.getRoom(roomName).getArea()[1]));
   }

   public static void rankUp(int rank, char c){
      ImageIcon pIcon = new ImageIcon("images/dice/" + c + rank + ".png");
       JLabel playerlabel = new JLabel(pIcon);
       playerlabel.setBounds(Integer.valueOf(Board.getRoom("office").getArea()[0])+ 49*right,Integer.valueOf(Board.getRoom("office").getArea()[1]) + 49*up,
                              pIcon.getIconWidth(),pIcon.getIconHeight());
      
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