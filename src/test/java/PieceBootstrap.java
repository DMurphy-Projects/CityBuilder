import io.TileXmlWriter;
import model.CityTile;
import tile.TileHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.HashMap;

public class PieceBootstrap {

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
        TileHandler tileHandler = new TileHandler(4);

        CityTile[] tiles = new CityTile[]{
                new CityTile("ROAD", 0, 4, 100),
                new CityTile("ROAD", 1, 4, 100),

                new CityTile("TURN", 0, 4, 10),
                new CityTile("TURN", 1, 4, 10),
                new CityTile("TURN", 2, 4, 10),
                new CityTile("TURN", 3, 4, 10),

                new CityTile("JUNCTION", 0, 4, 10),
                new CityTile("JUNCTION", 1, 4, 10),
                new CityTile("JUNCTION", 2, 4, 10),
                new CityTile("JUNCTION", 3, 4, 10),

                new CityTile("INTERSECTION", 0, 4, 10),
                new CityTile("BLANK", 0, 4, 100),
                new CityTile("BUILDING", 0, 4, 100),
        };

        for (CityTile tile: tiles)
        {
            tileHandler.addTile(tile.getId(), tile);
        }

        String[] symmetry_1 = new String[]{"INTERSECTION", "BLANK", "BUILDING"};
        String[] symmetry_2 = new String[]{"ROAD"};
        String[] symmetry_4 = new String[]{"TURN", "JUNCTION"};

        for (String s: symmetry_1) {
            for (int i = 0; i < 4; i++) {
                tileHandler.addEquivalent(s + i, s + 0);
            }
        }

        for (String s: symmetry_2)
        {
            for (int i=0;i<4;i++)
            {
                tileHandler.addEquivalent(s + i, s + (i % 2));
            }
        }

        for (String s: symmetry_4)
        {
            for (int i=0;i<4;i++)
            {
                tileHandler.addEquivalent(s + i, s + i);
            }
        }

        TileXmlWriter.writeToFile("src/main/resources/cityTiles.xml", tileHandler);
    }
}
