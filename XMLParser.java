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

  public static void main(String argv[]) {
    XMLParser parser = new XMLParser();
    parser.parseBoard();
    parser.parseCards();
  }

  public void parseBoard(){
    // positions created by the board are not on card
    // position defined as part in the xml
    try {
      File inputFile = new File(BOARDFILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      System.out.print("Root element: ");
      System.out.println(doc.getDocumentElement().getNodeName());

      NodeList list = doc.getElementsByTagName("set");

      for (int temp = 0; temp < list.getLength(); temp++) {
        Node nNode = list.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) nNode;
          // get set name
          String name = elem.getAttribute("name");
          //tests
          System.out.println();
          System.out.println(name);
          System.out.println();

          // parse neighbors
          NodeList neighbors = elem.getElementsByTagName("neighbor");
          String[] neighborsList = new String[neighbors.getLength()];

          for (int i = 0; i < neighbors.getLength(); i++) {
            Element elementAttribute = (Element) neighbors.item(i);
            neighborsList[i] = elementAttribute.getAttribute("name");
            //test
            System.out.println(neighborsList[i]);
          }
          // get area
          Element areaElem = (Element) elem.getElementsByTagName("area").item(0);
          String x = areaElem.getAttribute("x");
          String y = areaElem.getAttribute("y");
          String h = areaElem.getAttribute("h");
          String w = areaElem.getAttribute("w");
          System.out.println(x + " " + y + " " + h + " " + w);

          //parse takes
          Element takesElem = (Element) elem.getElementsByTagName("takes").item(0);
          NodeList takes = takesElem.getElementsByTagName("take");
          int numbTakes = takes.getLength();
          //test
          System.out.println(numbTakes);

          // parse positions
          Element partsElem = (Element) elem.getElementsByTagName("parts").item(0);
          NodeList parts = partsElem.getElementsByTagName("part");

          for (int i = 0; i < parts.getLength(); i++) {
            Element partEle = (Element) parts.item(i);
            String partName = partEle.getAttribute("name");
            String partRank = partEle.getAttribute("level");
            Element areaElem2 = (Element) partEle.getElementsByTagName("area").item(0);
            // future use
            String xPart = areaElem2.getAttribute("x");
            String yPart = areaElem2.getAttribute("y");
            String hPart = areaElem2.getAttribute("h");
            String wPart = areaElem2.getAttribute("w");
            Node lineNode = partEle.getElementsByTagName("line").item(0);
            String partLine = lineNode.getTextContent();
            //test
            System.out.println(partName);
            System.out.println(partLine);
          }
        }
      }

    }
    catch (Exception e) {
       e.printStackTrace();
    }
  }


  public void parseCards(){
    // positions created by the scene are on card
    try {
      File inputFile = new File(CARDSFILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      System.out.print("Root element: ");
      System.out.println(doc.getDocumentElement().getNodeName());
      NodeList list = doc.getElementsByTagName("card");

      for (int temp = 0; temp < list.getLength(); temp++) {
        Node nNode = list.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) nNode;
          // get set name and info
          String name = elem.getAttribute("name");
          String image = elem.getAttribute("img");
          String budget = elem.getAttribute("budget");
          //test
          System.out.println(name + " " + image + " " + budget);
          System.out.println();

          // get scene number and description
          Node sceneNode = elem.getElementsByTagName("scene").item(0);
          String sceneDesc = sceneNode.getTextContent();
          Element sceneElem = (Element) sceneNode;
          String sceneNum = sceneElem.getAttribute("number");
          System.out.println(sceneDesc);
          System.out.println(sceneNum);

          // get parts
          NodeList parts = elem.getElementsByTagName("part");
          for (int i = 0; i < parts.getLength(); i++) {
            Element partEle = (Element) parts.item(i);
            String partName = partEle.getAttribute("name");
            String partRank = partEle.getAttribute("level");
            Element areaElem2 = (Element) partEle.getElementsByTagName("area").item(0);
            // future use
            String xPart = areaElem2.getAttribute("x");
            String yPart = areaElem2.getAttribute("y");
            String hPart = areaElem2.getAttribute("h");
            String wPart = areaElem2.getAttribute("w");
            Node lineNode = partEle.getElementsByTagName("line").item(0);
            String partLine = lineNode.getTextContent();
            //test
            System.out.println(partName);
            System.out.println(partLine);
          }
        }
        System.out.println();
      }



    }
    catch (Exception e) {
       e.printStackTrace();
    }
  }

  public Scene createScene(){
    Scene newScene = new Scene();
    return newScene;
  }
  public Position createPosition(){
    Position newPosition = new Position();
    return newPosition;
  }


}
