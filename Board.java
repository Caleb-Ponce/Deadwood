import java.util.*;

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
    public void movePiece(){
      // future used with gui can be used with terminal based
    }

    public void resetBoard(){
      // return players to trailer
      // trailer is rooms[10]
      // set out new scenes for each room
      // remove players from remaining scenes
    }

    public void checkSceneCount(){
      //TODO!!!
      /* logic
      for each room
        check if scene is null
        if scene is null add 1 to count
      */
    }

    public void placeNewScenes(){
      //TODO!!!
      /* logic
      for each room
        randomScene = getRandomScene (scenes is 40 scenes big)
        room.addScene(randomScene)
        remove scene from list of usable scenes
      */
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
