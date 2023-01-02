package tile;

import model.CityGrid2D;
import model.CityTile;

import java.util.*;

public class TileHandler {

    HashSet<String> baseIds = new HashSet<>();

    HashSet<CityTile> uniqueTiles = new HashSet<>();
    ArrayList<CityTile> orderedUniqueTiles = new ArrayList<>();

    HashMap<String, String> equivalentMap = new HashMap<>();
    HashMap<String, CityTile> idLookup = new HashMap<>();

    int sides;

    public TileHandler(int s)
    {
        sides = s;
    }

    public CityTile lookupTile(String id)
    {
        String eq = equivalentMap.get(id);
        return idLookup.get(eq);
    }

    public CityTile lookupOrder(int index)
    {
        return orderedUniqueTiles.get(index);
    }

    public HashSet<CityTile> getUniqueTiles()
    {
        return uniqueTiles;
    }

    public Collection<CityTile> getOrderedUniqueTiles()
    {
        return orderedUniqueTiles;
    }

    public HashSet<String> getBaseIds() {
        return baseIds;
    }

    public Set<Map.Entry<String, String>> getEquivalentSet() {
        return equivalentMap.entrySet();
    }

    public void addTile(String id, CityTile tile)
    {
        baseIds.add(tile.getBaseId());

        if (!uniqueTiles.contains(tile))
        {
            uniqueTiles.add(tile);
            orderedUniqueTiles.add(tile);

            idLookup.put(id, tile);
        }
    }

    public void addEquivalent(String id, String equalTo)
    {
        equivalentMap.put(id, equalTo);
    }

    public void addValid(String id, int side, String validId)
    {
        idLookup.get(id).addValid(validId, side);
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
