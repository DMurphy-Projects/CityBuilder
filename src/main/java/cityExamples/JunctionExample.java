package cityExamples;

import model.CityGrid2D;
import tile.TileHandler;

public class JunctionExample {

    public static CityGrid2D[] createGrids(TileHandler tileHandler) {
        //junctions connect to junctions
        CityGrid2D grid0 = new CityGrid2D(tileHandler, 2, 1);
        grid0.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid0.setPosition(1, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);

        CityGrid2D grid1 = new CityGrid2D(tileHandler, 2, 1);
        grid1.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid1.setPosition(1, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[1]);

        CityGrid2D grid2 = new CityGrid2D(tileHandler, 2, 1);
        grid2.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid2.setPosition(1, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[2]);

        //junctions connect to roads
        CityGrid2D grid3 = new CityGrid2D(tileHandler, 2, 1);
        grid3.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid3.setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        CityGrid2D grid4 = new CityGrid2D(tileHandler, 2, 1);
        grid4.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[2]);
        grid4.setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        CityGrid2D grid5 = new CityGrid2D(tileHandler, 2, 1);
        grid5.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[3]);
        grid5.setPosition(1, 0, CityPieces.ROAD, CityPieces.ROAD_ROT[0]);

        //junctions connect to turns
        CityGrid2D grid6 = new CityGrid2D(tileHandler, 2, 1);
        grid6.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid6.setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);

        CityGrid2D grid7 = new CityGrid2D(tileHandler, 2, 1);
        grid7.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[2]);
        grid7.setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);

        CityGrid2D grid8 = new CityGrid2D(tileHandler, 2, 1);
        grid8.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[3]);
        grid8.setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[1]);

        CityGrid2D grid9 = new CityGrid2D(tileHandler, 2, 1);
        grid9.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid9.setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[2]);

        CityGrid2D grid10 = new CityGrid2D(tileHandler, 2, 1);
        grid10.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[2]);
        grid10.setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[2]);

        CityGrid2D grid11 = new CityGrid2D(tileHandler, 2, 1);
        grid11.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[3]);
        grid11.setPosition(1, 0, CityPieces.TURN, CityPieces.TURN_ROT[2]);

        //junctions adjacent to junctions
        CityGrid2D grid12 = new CityGrid2D(tileHandler, 2, 1);
        grid12.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[1]);
        grid12.setPosition(1, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[3]);

        //junctions connect to intersections
        CityGrid2D grid13 = new CityGrid2D(tileHandler, 2, 1);
        grid13.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[0]);
        grid13.setPosition(1, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);

        CityGrid2D grid14 = new CityGrid2D(tileHandler, 2, 1);
        grid14.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[2]);
        grid14.setPosition(1, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);

        CityGrid2D grid15 = new CityGrid2D(tileHandler, 2, 1);
        grid15.setPosition(0, 0, CityPieces.JUNCTION, CityPieces.JUNCTION_ROT[3]);
        grid15.setPosition(1, 0, CityPieces.INTERSECTION, CityPieces.INTERSECTION_ROT[0]);

        return new CityGrid2D[]{
                grid0, grid0.rotate90(), grid0.rotate180(), grid0.rotate270(),
                grid1, grid1.rotate90(), grid1.rotate180(), grid1.rotate270(),
                grid2, grid2.rotate90(), grid2.rotate180(), grid2.rotate270(),
                grid3, grid3.rotate90(), grid3.rotate180(), grid3.rotate270(),
                grid4, grid4.rotate90(), grid4.rotate180(), grid4.rotate270(),
                grid5, grid5.rotate90(), grid5.rotate180(), grid5.rotate270(),
                grid6, grid6.rotate90(), grid6.rotate180(), grid6.rotate270(),
                grid7, grid7.rotate90(), grid7.rotate180(), grid7.rotate270(),
                grid8, grid8.rotate90(), grid8.rotate180(), grid8.rotate270(),
                grid9, grid9.rotate90(), grid9.rotate180(), grid9.rotate270(),
                grid10, grid10.rotate90(), grid10.rotate180(), grid10.rotate270(),
                grid11, grid11.rotate90(), grid11.rotate180(), grid11.rotate270(),
                grid12, grid12.rotate90(), grid12.rotate180(), grid12.rotate270(),
                grid13, grid13.rotate90(), grid13.rotate180(), grid13.rotate270(),
                grid14, grid14.rotate90(), grid14.rotate180(), grid14.rotate270(),
                grid15, grid15.rotate90(), grid15.rotate180(), grid15.rotate270(),
        };
    }

}
