import io.TileXmlReader;
import model.CityTile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import tile.TileHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ReadFileTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        TileHandler tileHandler = TileXmlReader.readFromFile("src/main/resources/cityTiles.xml");
    }
}
