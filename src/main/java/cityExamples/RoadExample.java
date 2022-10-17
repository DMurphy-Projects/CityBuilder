package cityExamples;

import model.CityGrid2D;
import tile.TileHandler;

public class RoadExample {

    public static CityGrid2D[] createGrids(TileHandler tileHandler)
    {
        //roads connect to roads
        CityGrid2D grid0 = new CityGrid2D(tileHandler, 2, 1);
        grid0.setPosition(0, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);
        grid0.setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        //roads can be adjacent
        CityGrid2D grid1 = new CityGrid2D(tileHandler, 2, 1);
        grid1.setPosition(0, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[1]);
        grid1.setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[1]);

        return new CityGrid2D[]{
                grid0, grid0.rotate90(),
                grid1, grid1.rotate90(),
        };
    }
}
