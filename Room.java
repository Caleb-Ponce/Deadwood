public class Room{
    private String name;
    private String[] neighbors;
    private String[] area;
    private int shotCounters;
    public Position[] positions;
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
}
