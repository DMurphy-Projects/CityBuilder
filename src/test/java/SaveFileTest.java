import cityExamples.*;
import io.TileXmlWriter;
import tile.TileHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.IOException;

public class SaveFileTest {
    //create xml file from existing tileHandler
    public static void main(String[] args) {
        TileHandler tileHandler = new TileHandler(4);

        tileHandler.addAll(CityPieces.ROAD, CityPieces.ROAD_ROT);
        tileHandler.addAll(CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT);
        tileHandler.addAll(CityPieces.TURN, CityPieces.TURN_ROT);
        tileHandler.addAll(CityPieces.JUNCTION, CityPieces.JUNCTION_ROT);

        tileHandler.addAllValidFromGrid(RoadExample.createGrids(tileHandler));
        tileHandler.addAllValidFromGrid(IntersectionExample.createGrids(tileHandler));
        tileHandler.addAllValidFromGrid(TurnExample.createGrids(tileHandler));
        tileHandler.addAllValidFromGrid(JunctionExample.createGrids(tileHandler));

        try {
            TileXmlWriter.writeToFile("src/main/resources/cityTiles.xml", tileHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
