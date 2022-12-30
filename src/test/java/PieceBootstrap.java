import io.TileXmlWriter;
import model.CityTile;
import tile.TileHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class PieceBootstrap {

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
        TileHandler tileHandler = new TileHandler(4);

        CityTile[] tiles = new CityTile[]{
                new CityTile("ROAD", 2, 4, 100),
                new CityTile("TURN", 4, 4, 10),
                new CityTile("JUNCTION", 4, 4, 10),
                new CityTile("INTERSECTION", 1, 4, 10),
                new CityTile("BLANK", 1, 4, 100),
                new CityTile("BUILDING", 1, 4, 100),
        };

        for (CityTile tile: tiles)
        {
            //co-opting rotation function for the bootstrap
            for (int i=0;i<4;i++) {
                tileHandler.add(tile.getBaseId(), i, i % tile.getRotation(), tile.getWeight());
            }
        }

        TileXmlWriter.writeToFile("src/main/resources/cityTiles.xml", tileHandler);
    }
}
