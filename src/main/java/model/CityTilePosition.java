package model;

public class CityTilePosition {

    CityTile tile;
    int position;

    public CityTilePosition(CityTile t, int p)
    {
        tile = t;
        position = p;
    }

    public CityTile getTile() {
        return tile;
    }

    public int getPosition() {
        return position;
    }
}
