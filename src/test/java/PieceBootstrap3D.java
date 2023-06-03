import io.TileXmlWriter;
import model.CityGrid3D;
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

                new CityTile("TURN", 0, 6, 10),
                new CityTile("TURN", 1, 6, 10),
                new CityTile("TURN", 2, 6, 10),
                new CityTile("TURN", 3, 6, 10),

                new CityTile("JUNCTION", 0, 6, 10),
                new CityTile("JUNCTION", 1, 6, 10),
                new CityTile("JUNCTION", 2, 6, 10),
                new CityTile("JUNCTION", 3, 6, 10),

                new CityTile("INTERSECTION", 0, 6, 10),
                new CityTile("BLANK", 0, 6, 100),
                new CityTile("AIR", 0, 6, 100),
                new CityTile("BUILDING", 0, 6, 100),
        };

        for (CityTile tile: tiles)
        {
            tileHandler.addTile(tile.getId(), tile);
        }

        String[] symmetry_1 = new String[]{"INTERSECTION", "BLANK", "AIR", "BUILDING"};
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

        int w = 2, h = 2, l = 2;
        CityGrid3D base = new CityGrid3D(tileHandler, w, l, h);
        for (int x=0;x<w;x++)
        {
            for (int y=0;y<h;y++) {
                for (int z = 0; z < l; z++) {
                    int pos = x + ((z * h + y) * w);
                    if (y == 0)
                    {
                        base.setPosition(pos, "BLANK", 0);
                    }
                    else
                    {
                        base.setPosition(pos, "AIR", 0);
                    }
                }
            }
        }

//        TileXmlWriter.writeToFile("src/main/resources/cityTiles3D.xml", tileHandler);

        tileHandler.addValidFromGrid(base);
        tileHandler.addValidFromGrid(base.rotate90());
        tileHandler.addValidFromGrid(base.rotate180());
        tileHandler.addValidFromGrid(base.rotate270());

        TileXmlWriter.writeToFile("src/main/resources/cityTiles3D.xml", tileHandler);
    }
}
