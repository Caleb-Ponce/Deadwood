import java.util.*;

public class CastingOffice{
    private String[] upgrades;
    private List<Integer> showRankCosts;

    public CastingOffice() {
      XMLParser Xml = new XMLParser();
      this.upgrades = Xml.parseUpgrades();
    }

    public void checkCanUpgrade(int player){

    }
    public void chargeForUpgrade(int rank, int player){

    }
    public void promptPlayer(int price){

    }
    public void showRankCosts(){

    }

}
