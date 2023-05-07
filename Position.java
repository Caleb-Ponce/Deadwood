public class Position{
    private String name;
    private int rank;
    private String[] area;
    private String line;
    private boolean onCard;
    private Player player = null;

    public Position(String name, String rank, String[] area, String line, boolean onCard) {
      this.name = name;
      this.rank = Integer.valueOf(rank);
      this.area = area;
      this.line = line;
      this.onCard = onCard;
    }

    public String getName() {
      return this.name;
    }

    public int getRank() {
      return this.rank;
    }

    public String getline() {
      return this.line;
    }

    public boolean getOnCard() {
      return this.onCard;
    }

    public Player getPlayer() {
      if (this.player != null) {
        return this.player;
      }
      return null;
    }
}
