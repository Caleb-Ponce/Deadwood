import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XMLParser{

  private static final String BOARDFILE = "XMLFiles/board.xml";
  private static final String CARDSFILE = "XMLFiles/cards.xml";

  public Room[] parseBoard(){
    // positions created by the board are not on card
    // position defined as part in the xml
    // TODO parse Trailer and office
    try {
      File inputFile = new File(BOARDFILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      // parse xml into rooms
      Room[] rooms = new Room[12];
      NodeList list = doc.getElementsByTagName("set");

      for (int temp = 0; temp < list.getLength(); temp++) {
        Node nNode = list.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) nNode;
          // get set name
          String name = elem.getAttribute("name");
          // parse neighbors
          NodeList neighbors = elem.getElementsByTagName("neighbor");
          String[] neighborsList = new String[neighbors.getLength()];

          for (int i = 0; i < neighbors.getLength(); i++) {
            Element elementAttribute = (Element) neighbors.item(i);
            neighborsList[i] = elementAttribute.getAttribute("name");
          }
          // get area
          Element areaElem = (Element) elem.getElementsByTagName("area").item(0);
          String[] roomArea = new String[4];
          roomArea[0] = areaElem.getAttribute("x");
          roomArea[1] = areaElem.getAttribute("y");
          roomArea[2] = areaElem.getAttribute("h");
          roomArea[3] = areaElem.getAttribute("w");

          //parse takes
          Element takesElem = (Element) elem.getElementsByTagName("takes").item(0);
          NodeList takes = takesElem.getElementsByTagName("take");
          // create a list of takes here
          for (int i = 0; i < takes.getLength(); i++) {
            Element takeEle = (Element) takes.item(i);
            takeEle.getAttribute("number");
            String[] takeArea = new String[4];
            takeArea [0] = takeEle.getAttribute("x");
            takeArea [1] = takeEle.getAttribute("y");
            takeArea [2] = takeEle.getAttribute("h");
            takeArea [3] = takeEle.getAttribute("w");
            // create new take here
            // add new take to take list here
          }
          int numbTakes = takes.getLength();
          // parse positions
          Element partsElem = (Element) elem.getElementsByTagName("parts").item(0);
          NodeList parts = partsElem.getElementsByTagName("part");
          Position[] positions = new Position[parts.getLength()];
          for (int i = 0; i < parts.getLength(); i++) {
            Element partEle = (Element) parts.item(i);
            String partName = partEle.getAttribute("name");
            String partRank = partEle.getAttribute("level");
            Element areaElem2 = (Element) partEle.getElementsByTagName("area").item(0);
            // future use
            String[] partArea = new String[4];
            partArea[0] = areaElem2.getAttribute("x");
            partArea[1] = areaElem2.getAttribute("y");
            partArea[2] = areaElem2.getAttribute("h");
            partArea[3] = areaElem2.getAttribute("w");
            Node lineNode = partEle.getElementsByTagName("line").item(0);
            String partLine = lineNode.getTextContent();
            //set pos in array
            Position newPos = new Position(partName, partRank, partArea, partLine, false);
            positions[i] = newPos;
          }
          Room newRoom = new Room(name, neighborsList, roomArea, numbTakes, positions);
          rooms[temp] = newRoom;
        }
      }

      // parse trailer
      Node trailerNode = doc.getElementsByTagName("trailer").item(0);
      Element trailerElem = (Element) trailerNode;

      NodeList trailerNeighbors = trailerElem.getElementsByTagName("neighbor");
      String[] trailerNeighborsList = new String[trailerNeighbors.getLength()];

      for (int i = 0; i < trailerNeighbors.getLength(); i++) {
        Element elementAttribute = (Element) trailerNeighbors.item(i);
        trailerNeighborsList[i] = elementAttribute.getAttribute("name");
      }
      Element areatrailer = (Element) trailerElem.getElementsByTagName("area").item(0);
      String[] trailerArea = new String[4];
      trailerArea[0] = areatrailer.getAttribute("x");
      trailerArea[1] = areatrailer.getAttribute("y");
      trailerArea[2] = areatrailer.getAttribute("h");
      trailerArea[3] = areatrailer.getAttribute("w");
      Room trailer = new Room("trailer", trailerNeighborsList, trailerArea, 0, null);
      rooms[10] = trailer;

      Node officeNode = doc.getElementsByTagName("office").item(0);
      Element officeElem = (Element) officeNode;

      NodeList officeNeighbors = officeElem.getElementsByTagName("neighbor");
      String[] officeNeighborsList = new String[trailerNeighbors.getLength()];

      for (int i = 0; i < officeNeighbors.getLength(); i++) {
        Element elementAttribute = (Element) trailerNeighbors.item(i);
        officeNeighborsList[i] = elementAttribute.getAttribute("name");
      }
      Element areaoffice = (Element) officeElem.getElementsByTagName("area").item(0);
      String[] officeArea = new String[4];
      officeArea[0] = areaoffice.getAttribute("x");
      officeArea[1] = areaoffice.getAttribute("y");
      officeArea[2] = areaoffice.getAttribute("h");
      officeArea[3] = areaoffice.getAttribute("w");
      Room office = new Room("office", officeNeighborsList, officeArea, 0, null);
      rooms[11] = office;

      return rooms;
    }
    catch (Exception e) {
       e.printStackTrace();
    }
    return null;
  }

  public Scene[] parseCards(){
    // positions created by the scene are on card
    try {
      File inputFile = new File(CARDSFILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      Scene[] scenes = new Scene[40];

      NodeList list = doc.getElementsByTagName("card");

      for (int temp = 0; temp < list.getLength(); temp++) {
        Node nNode = list.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) nNode;
          // get set name and info
          String name = elem.getAttribute("name");
          String image = elem.getAttribute("img");
          String budget = elem.getAttribute("budget");

          // get scene number and description
          Node sceneNode = elem.getElementsByTagName("scene").item(0);
          String sceneDesc = sceneNode.getTextContent();
          Element sceneElem = (Element) sceneNode;
          String sceneNum = sceneElem.getAttribute("number");

          // get parts
          NodeList parts = elem.getElementsByTagName("part");
          Position[] positions = new Position[parts.getLength()];
          for (int i = 0; i < parts.getLength(); i++) {
            Element partEle = (Element) parts.item(i);
            String partName = partEle.getAttribute("name");
            String partRank = partEle.getAttribute("level");
            Element areaElem2 = (Element) partEle.getElementsByTagName("area").item(0);
            // future use
            String[] partArea = new String[4];
            partArea[0] = areaElem2.getAttribute("x");
            partArea[1] = areaElem2.getAttribute("y");
            partArea[2] = areaElem2.getAttribute("h");
            partArea[3]= areaElem2.getAttribute("w");
            Node lineNode = partEle.getElementsByTagName("line").item(0);
            String partLine = lineNode.getTextContent();

            Position newPos = new Position(partName, partRank, partArea, partLine, true);
            positions[i] = newPos;
          }
          Scene newScene = new Scene(name, positions, image, sceneNum, sceneDesc, budget);
          scenes[temp] = newScene;
        }
      }
      return scenes;
    }
    catch (Exception e) {
       e.printStackTrace();
    }
    return null;
  }
}
