package model;

import java.util.HashSet;

public class CityTile {

    String id;
    int rotation;

    //[direction][list of valid id]
    HashSet<String>[] valid;

    public CityTile(String id, int r, int sides)
    {
        this.id = id;
        this.rotation = r;

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
