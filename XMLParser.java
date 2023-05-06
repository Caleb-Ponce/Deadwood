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
