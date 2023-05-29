import io.TileXmlWriter;
import model.CityTile;
import tile.TileHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class PieceBootstrap3D {
    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
        TileHandler tileHandler = new TileHandler(6);


        CityTile[] tiles = new CityTile[]{
                new CityTile("ROAD", 0, 6, 100),
                new CityTile("ROAD", 1, 6, 100),
        };

        for (CityTile tile: tiles)
        {
            tileHandler.addTile(tile.getId(), tile);
        }

        String[] symmetry_1 = new String[]{};
        String[] symmetry_2 = new String[]{"ROAD"};
        String[] symmetry_4 = new String[]{};

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

        TileXmlWriter.writeToFile("src/main/resources/cityTiles3D.xml", tileHandler);
    }
}
