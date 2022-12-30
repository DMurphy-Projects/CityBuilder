package model;

import java.util.HashSet;

public class CityTile {

    String id;
    int rotation;
    int weight;

    //[direction][list of valid id]
    HashSet<String>[] valid;

    public CityTile(String id, int r, int sides, int w)
    {
        this.id = id;
        this.rotation = r;
        this.weight = w;

        this.valid = new HashSet[sides];
        for (int i=0;i<valid.length;i++)
        {
            valid[i] = new HashSet<>();
        }
    }

    public String getId() {
        return id + rotation;
    }
    public String getBaseId() { return id; }

    public int getRotation() {
        return rotation;
    }

    public int getWeight() {
        return weight;
    }

    public HashSet<String>[] getValid() {
        return valid;
    }

    public HashSet<String> getValid(int d) {
        return valid[d];
    }

    public void addValid(String index, int side)
    {
        valid[side].add(index);
    }
}
