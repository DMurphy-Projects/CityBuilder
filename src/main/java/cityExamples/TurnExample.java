package cityExamples;

import model.CityGrid2D;
import tile.TileHandler;

public class TurnExample {
    public static CityGrid2D[] createGrids(TileHandler tileHandler) {

        CityGrid2D[] grids = new CityGrid2D[]{
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
                new CityGrid2D(tileHandler, 2, 1),
        };
        int i = 0;
        //turns connect to turns
        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[0]);
        grids[i++].setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);

        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[0]);
        grids[i++].setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[2]);

        //turns connect to roads
        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[0]);
        grids[i++].setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[3]);
        grids[i++].setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        //turns connect to intersections
        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[0]);
        grids[i++].setPosition(1, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);

        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[3]);
        grids[i++].setPosition(1, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);

        //turns can be adjacent to roads
        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);
        grids[i++].setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[1]);

        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[2]);
        grids[i++].setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[1]);

        //turns can be adjacent to turns
        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);
        grids[i++].setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[0]);

        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[2]);
        grids[i++].setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[0]);

        grids[i].setPosition(0, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);
        grids[i++].setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[3]);

        i = 0;
        return new CityGrid2D[]{
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
                grids[i], grids[i].rotate90(), grids[i].rotate180(), grids[i++].rotate270(),
        };
    }
}
