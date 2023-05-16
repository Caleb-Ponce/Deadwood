import java.util.*;

public class CastingOffice{
    private String[] upgrades;
    private List<Integer> showRankCosts;
    public String Type;
    Controller control;

    public CastingOffice() {
      XMLParser Xml = new XMLParser();
      this.upgrades = Xml.parseUpgrades();
    }

    public boolean checkCanUpgrade(String type){
      if(showRankCosts[player.rank -1] < player.money && type == "Money"){
        return true;
      }else if(showRankCosts[player.rank + 5] < player.credits && type == "Credits"){
        return true;
      }else{
        return false;
      }
    }
    public void chargeForUpgrade(int rank, String type, Player player) {
      if( type == "Money"){
        player.money -= showRankCosts[rank];
      }else if(typer == "Credits"){
        player.Credit -= showRankCosts[rank+6];
      }
    }
    public void promptPlayer(Player player){
        showRankCosts();
        String message = "Welcome to the Casting Office! What type of curentcy will you be using?";
        String[] curencies = new String[]{"money", "credits"};
        this.Type = this.control.getInputInt(message, currencies);
        if(checkCanUpgrade(Type)){
          chargeForUpgrade(player.rank, Type);
          System.out.print("Congratulations, you arr now rank " +  this.player.rank + " now back to work!");
        }else{
          System.out.print("You don't have enough to upgrade yet, come back after you've done a few more scenes");
        }

    }
    public void showRankCosts(){
      System.out.print("Money: " + upgrades[0-5] + "Credit: " + upgrades[6-11]);

    }

}
