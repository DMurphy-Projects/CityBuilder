package cityExamples;

import model.CityGrid2D;
import tile.TileHandler;

public class IntersectionExample {

    public static CityGrid2D[] createGrids(TileHandler tileHandler) {
        //intersections connect to roads
        CityGrid2D grid0 = new CityGrid2D(tileHandler, 2, 1);
        grid0.setPosition(0, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);
        grid0.setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        //intersections connect to intersections
        CityGrid2D grid1 = new CityGrid2D(tileHandler, 2, 1);
        grid1.setPosition(0, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);
        grid1.setPosition(1, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);

        return new CityGrid2D[]{
                grid0, grid0.rotate90(), grid0.rotate180(), grid0.rotate270(),
                grid1, grid1.rotate90(), grid1.rotate180(), grid1.rotate270(),
        };
    }
}
