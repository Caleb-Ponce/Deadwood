import java.util.*;

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
        System.out.println();
        System.out.println("You have; dollars: " + player.getMoney() + " Credits: "+ player.getCredits() + " Rank: " + player.getRank());
        System.out.println();
        this.printRanks();
        Controller control = new Controller();
        String[] options = this.checkCanUpgrade(player);
        if (options.length == 0) {
          // add more logic to show you how much you have and the rank prices
          System.out.println("you cannot upgrade at this time");
          return;
        }

        String message = "Please choose a rank option or choose none";
        System.out.println("Your current rank is: " + player.getRank());
        int optNum = control.getInputInt(message, options);
        if (optNum == 0) {
          return;
        }
        String parts[] = options[optNum].split(" ");
        if (parts[1].equals("dollar")) {
          this.chargeForUpgrade(Integer.parseInt(parts[0]), 0, Integer.parseInt(parts[2]), player);
        } else if (parts[1].equals("credit")) {
          this.chargeForUpgrade(Integer.parseInt(parts[0]), 1, Integer.parseInt(parts[2]), player);
        }
        player.setRank(Integer.parseInt(parts[0]));
        System.out.println("your rank is now: " + parts[0]);
    }

    public void printRanks() {
      System.out.println();
      System.out.println("rank costs");
      for (int i = 0; i < 5; i++) {
        System.out.println("rank " + (i+2) + ":");
        System.out.println(this.upgrades[i]);
        System.out.println(this.upgrades[i+5]);
      }
      System.out.println();
    }

    public void setCosts(int[] costs) {
      this.costs = costs;
    }

}
