import java.util.*;

public class Board{
    private Room[] rooms;            // trailer = rooms[10], office = rooms[11]
    private Scene[] scenes;
    private int sceneCount;
    public CastingOffice castingOffice = new CastingOffice();

    public Board(){
        XMLParser Xml = new XMLParser();
        this.rooms = Xml.parseBoard();
        this.scenes = Xml.parseCards();
    }

    public boolean movePiece(String location, Player player) {
      // future, used with gui can be used with terminal based
        for (Room curRoom : this.rooms) {
          if (curRoom.getName().equals(location)){
            player.setRoom(curRoom);
            return true;
          }
        }
        return false;
    }

    public void placeNewScenes(){
      Random rand = new Random();
      Scene[] curScenes = this.scenes;
      for(int i = 0; i < 10; i++){
        int randomScene = rand.nextInt(curScenes.length); //get index or frandom scene
        this.rooms[i].addScene(curScenes[randomScene]);
        curScenes[randomScene].setRoom(this.rooms[i]);
        this.rooms[i].scene.setOffCard(this.rooms[i].getPositions());
        //remove scene from list of usable scenes
        Scene[] copy = new Scene[curScenes.length - 1];
        for (int n = 0, j = 0; n < curScenes.length; n++) {
            if (n < randomScene) {
                copy[j] = curScenes[n];
                j++;
            } else if (n > randomScene) {
                copy[j] = curScenes[n];
                j++;
            }
        }
        curScenes = copy;
      }
      this.scenes = curScenes;
      this.sceneCount = 10;
    }

    public void clearScenes(){
      for (Room room : this.rooms) {
        room.removeScene();
      }
      this.sceneCount = 0;
    }

    public Room getRoom(String name) {
      for (Room curRoom : this.rooms) {
        String curName = curRoom.getName();
        if (name.equals(curName)){
          return curRoom;
        }
      }
      return null;
    }

    public int getSceneCount() {
      return this.sceneCount;
    }

    public void lowerSceneCount() {
      this.sceneCount--;
      if (this.sceneCount <= 1) {
        GameManager.getGameManager().endDay();
      }
    }

    public Room[] getRooms(){
        return this.rooms;
    }
    public CastingOffice getCastingOffice(){
        return this.castingOffice;
    }
    public Scene[] getScenes(){
      return this.scenes;
    }
}
