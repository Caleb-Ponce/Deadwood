import java.util.*;

import javax.swing.*;

public class CastingOffice{
    private String[] upgrades;
    //format: name + " " + currency + " " + amt
    private int[] costs;

    public CastingOffice() {
      XMLParser Xml = new XMLParser();
      this.upgrades = Xml.parseUpgrades(this);
    }

    public String[] checkCanUpgrade(Player player) {
      ArrayList<String> options = new ArrayList<String>();
      //dollars
      options.add("CANCEL");
      for (int i = 0; i < 5; i++) {
        if (this.costs[i] < player.getMoney() && i + 2 > player.getRank()) {
          options.add(this.upgrades[i]);
        }
      }
      // credits
      for (int i = 5; i < 10; i++) {
        if (this.costs[i] < player.getCredits() && i - 3 > player.getRank()) {
          options.add(this.upgrades[i]);
        }
      }
      String[] a = new String[options.size()];
      options.toArray(a);
      return a;
    }

    public void chargeForUpgrade(int rank, int type, int cost, Player player) {
      if( type == 0){
        player.subMoney(cost);
      }else if(type == 1){
        player.subCredits(cost);
      }
    }

    public void promptPlayer(Player player) {
      JFrame popup = new JFrame("popup");
        String[] options = this.checkCanUpgrade(player);
        if (options.length == 0) {
          // add more logic to show you how much you have and the rank prices
          JOptionPane.showMessageDialog(popup, "you cannot upgrade at this time",
      "Back to work!", JOptionPane.ERROR_MESSAGE);
          return;
        }
        
        int optNum = JOptionPane.showOptionDialog(popup,
          "What would you like to do: ",
          "Please choose a rank option or choose none",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[0]);
        if (optNum == 0) {
          return;
        }
        
        String choice = JOptionPane.showInputDialog(popup, "would you like to use dollors or creddits:");
        String parts[] = options[optNum].split(" ");
        if (choice == "dollar" || choice == "dollars") {
          this.chargeForUpgrade(Integer.parseInt(parts[0]), 0, Integer.parseInt(parts[2]), player);
        } else if (choice == "credit" || choice == "creddits") {
          this.chargeForUpgrade(Integer.parseInt(parts[0]), 1, Integer.parseInt(parts[2]), player);
        }
        player.setRank(Integer.parseInt(parts[0]));
        BoardLayersListener.rankUp(player.getRank(), player.getColor());
        JOptionPane.showMessageDialog(popup, "your rank is now: " + parts[0]);
        
    }

    public void setCosts(int[] costs) {
      this.costs = costs;
    }

}
