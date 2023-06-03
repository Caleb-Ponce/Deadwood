import java.awt.geom.Area;

public class Room{
    private String name;
    private String[] neighbors;
    private String[] area;
    private int shotCounters;
    private int shotsComplete = 0;
    private Position[] positions;
    public Scene scene = null;

    public Room(String name, String[] neighbors, String[] area, int shotCounters, Position[] positions){
        this.name = name;
        this.neighbors = neighbors;
        this.area = area;
        this.shotCounters = shotCounters;
        this.positions = positions;
    }
    public void removeShotCounter() {
        this.shotsComplete++;
        if (this.shotsComplete == this.shotCounters){
          this.scene.endScene();
          this.shotsComplete = 0;
        }
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
      return this.shotCounters - this.shotsComplete;
    }

    public String[] getNeighbors(){
      return this.neighbors;
    }

    public void clearPoses() {
      for (Position pos : this.positions) {
        pos.clearPose();
      }
    }
    public Scene getScene(){
      return this.scene;
    }
    public String[] getArea(){
      return this.area;
    }
}
