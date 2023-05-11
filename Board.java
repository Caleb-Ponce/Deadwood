import java.util.*;
import java.util.Random;

public class Board{
    private Room[] rooms;            // trailer = rooms[10], office = rooms[11]
    private Scene[] scenes;
    private int sceneCount;
    public CastingOffice castingOffice;

    public Board(){
        XMLParser Xml = new XMLParser();
        this.rooms = Xml.parseBoard();
        this.scenes = Xml.parseCards();
    }
    public boolean movePiece(String location, Player player){
      // future used with gui can be used with terminal based
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
      for(int i = 0; i < 10; i++){
        int randomScene = rand.nextInt(scenes.length); //get index or frandom scene
        this.rooms[i].addScene(scenes[randomScene]);

        //remove scene from list of usable scenes
        //maybe switch to storing scenes in linked list for better optimization
        Scene[] copy = new Scene[scenes.length - 1];
        for (int i = 0, j = 0; i < scenes.length; i++) {
            if (i != randomScene) {
                copy[j++] = scenes[i];
            }
        }
        this.scenes = copy;
      }
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
    }

    public int getSceneCount() {
      return this.sceneCount;
    }

    public void lowerSceneCount() {
      this.sceneCount--;
    }

    public Room[] getRooms(){
        return this.rooms;
    }
}
