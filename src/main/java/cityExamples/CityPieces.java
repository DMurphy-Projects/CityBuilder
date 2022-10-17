package cityExamples;

public class CityPieces {

    public static String
            ROAD = "R",
            INTERSECTION = "I",
            TURN = "T",
            JUNCTION = "J";

    public static int[]
            ROAD_ROT = {0, 1, 0, 1},
            INTERSECTION_ROT = {0, 0, 0, 0},
            TURN_ROT = {0, 1, 2, 3},
            JUNCTION_ROT = {0, 1, 2, 3}
    ;
}
