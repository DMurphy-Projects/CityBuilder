package cityExamples;

import model.CityGrid;
import model.CityGrid2D;
import tile.TileHandler;

public class AllPiecesExample {
    public static CityGrid2D[] createGrids(TileHandler tileHandler) {

        String[] pieces = {
                CityPieces.ROAD,
                CityPieces.INTERSECTION,
                CityPieces.TURN,
                CityPieces.JUNCTION,
                CityPieces.BLANK,
                CityPieces.BUILDING,
        };

        CityGrid2D[] grids = new CityGrid2D[pieces.length];

        for (int j=0;j<pieces.length;j++) {
            grids[j] = new CityGrid2D(tileHandler, 1, 1);
            grids[j].setPosition(0, 0, pieces[j],0);
        }

        CityGrid2D[] rotations = new CityGrid2D[grids.length * 4];
        int i = 0;
        for (CityGrid2D cg: grids)
        {
            rotations[i++] = cg;
            rotations[i++] = cg.rotate90();
            rotations[i++] = cg.rotate180();
            rotations[i++] = cg.rotate270();
        }
        return rotations;
    }
}
