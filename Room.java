public class Room{
    private String name;
    private String[] neighbors;
    private String[] area;
    private int shotCounters;
    private Position[] positions;
    public Scene scene;

    public Room(String name, String[] neighbors, String[] area, int shotCounters, Position[] positions){
        this.name = name;
        this.neighbors = neighbors;
        this.area = area;
        this.shotCounters = shotCounters;
        this.positions = positions;
    }
    public int removeShotCounter() {
        this.shotCounters--;
        return this.shotCounters;
    }
    public void addScene(Scene scene){
        this.scene = scene;
    }
    public void removeScene(){
      this.scene = null;
    }

    public String getName(){
      return this.name;
    }

    public Position[] getPositions(){
      return this.positions;
    }

    public int getShotCounters(){
      return this.shotCounters;
    }

    public String[] getNeighbors(){
      return this.neighbors;
    }
}
