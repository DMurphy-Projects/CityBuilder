package model;

import tile.TileHandler;

public class CityGrid {

    CityTile[] grid;

    TileHandler handler;

    public CityGrid(TileHandler h, int size)
    {
        grid = new CityTile[size];
        handler = h;
    }

    public void setPosition(int pos, CityTile tile)
    {
        setPosition(pos, tile.id, tile.rotation);
    }
    public void setPosition(int pos, String id, int rot)
    {
        grid[pos] = handler.lookupTile(id + rot);
    }

    public CityTile getPosition(int pos)
    {
        return grid[pos];
    }

    public CityGrid(TileHandler handler, CityTile[] grid)
    {
        this.grid = grid;
        this.handler = handler;
    }

    public int getLength()
    {
        return grid.length;
    }
}
