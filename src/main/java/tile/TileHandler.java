package tile;

import model.CityGrid2D;
import model.CityTile;

import java.util.*;

public class TileHandler {

    HashMap<String, CityTile> tilesIndex = new HashMap<>(), tilesRotation = new HashMap<>();
    ArrayList<CityTile> tilesList = new ArrayList<>();
    int sides;

    public TileHandler(int s)
    {
        sides = s;
    }

    public CityTile getNext(CityTile tile)
    {
        int index = tilesList.lastIndexOf(tile);
        return tilesList.get((index + 1) % tilesList.size());
    }

    public CityTile getPrevious(CityTile tile)
    {
        int index = tilesList.indexOf(tile);
        return tilesList.get(Math.floorMod(index - 1, tilesList.size()));
    }

    public CityTile lookupIndex(String id)
    {
        return tilesIndex.get(id);
    }
    public CityTile lookupRotation(String id)
    {
        return tilesRotation.get(id);
    }

    public Set<Map.Entry<String, CityTile>> lookupIndexSet()
    {
        return tilesIndex.entrySet();
    }

    public Collection<CityTile> lookupIndexAll()
    {
        return tilesIndex.values();
    }
    public Collection<CityTile> lookupRotationAll()
    {
        return tilesRotation.values();
    }

    public void addAll(String id, int[] rotation)
    {
        for (int i=0;i<rotation.length;i++)
        {
            add(id, i, rotation[i]);
        }
    }

    public void add(String id, int index, int rot)
    {
        CityTile tile;
        if (tilesRotation.containsKey(id + rot)) {
            tile = tilesIndex.get(id + rot);
        }
        else
        {
            tile = new CityTile(id, rot, sides);
            tilesRotation.put(id + rot, tile);
        }

        tilesIndex.put(id + index, tile);

        tilesList.add(tile);
    }

    public void addBoth(CityTile tile, int index, int rot)
    {
        addIndex(tile, index);
        addRotation(tile, rot);

        tilesList.add(tile);
    }
    public void addIndex(CityTile tile, int index)
    {
        tilesIndex.put(tile.getBaseId() + index, tile);
    }
    public void addRotation(CityTile tile, int rot)
    {
        tilesRotation.put(tile.getBaseId() + rot, tile);
    }

    public void addValid(String id, int side, String validId)
    {
        tilesIndex.get(id).addValid(validId, side);
    }

    public void addAllValidFromGrid(CityGrid2D[] grids)
    {
        for (CityGrid2D grid: grids)
        {
            addValidFromGrid(grid);
        }
    }
    public void addValidFromGrid(CityGrid2D grid)
    {
        for (int i=0;i<grid.getLength();i++)
        {
            CityTile current = grid.getPosition(i);
            if (current == null) continue;

            CityTile[] surrounding = grid.getSurrounding(i);
            for (int ii=0;ii<surrounding.length;ii++)
            {
                if (surrounding[ii] == null) continue;
                current.addValid(surrounding[ii].getId(), ii);
            }
        }
    }

    public int getSides() {
        return sides;
    }
}
