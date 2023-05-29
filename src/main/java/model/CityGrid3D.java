package model;

import tile.TileHandler;

public class CityGrid3D extends CityGrid {

    int width, length, height;

    public CityGrid3D(TileHandler handler, int w, int l, int h) {
        super(handler, w * l * h);

        width = w;
        length = l;
        height = h;
    }

    public CityGrid3D(TileHandler handler, CityTile[] grid, int w, int l, int h) {
        super(handler, grid);

        width = w;
        length = l;
        height = h;
    }

    public void setPosition(int x, int y, int z, String id, int rot)
    {
        setPosition(x + ((z * height + y) * width), id, rot);
    }
    public void setPosition(int x, int y, int z, CityTile tile)
    {
        setPosition(x + ((z * height + y) * width), tile);
    }

    public CityTile getPosition(int x, int y, int z)
    {
        return getPosition(x + ((z * height + y) * width));
    }

    private CityTile getPositionChecked(int x, int y, int z)
    {
        if (x < 0 || x >= width || y < 0 || y >= height || z < 0|| z >= length) return null;

        return getPosition(x, y, z);
    }

    public CityTile[] getSurrounding(int pos)
    {
        return getSurrounding(pos % width, (pos / width) % height, pos / (width * height));
    }

    public CityTile[] getSurrounding(int x, int y, int z)
    {
        return new CityTile[]{
                getPositionChecked(x, y-1, z),
                getPositionChecked(x+1, y, z),
                getPositionChecked(x, y+1, z),
                getPositionChecked(x-1, y, z),
                getPositionChecked(x, y, z-1),
                getPositionChecked(x, y, z+1),
        };
    }

    //only want lateral rotations, so same as 2D
    private void rotatePieces(CityTile[] grid, int rot)
    {
        for (int i=0;i<grid.length;i++)
        {
            //empty grids don't need rotated
            if (grid[i] == null) continue;

            try {
                CityTile rotated = handler.lookupTile(grid[i].id + (grid[i].rotation + rot) % 4);
                grid[i] = rotated;
            }
            catch (NullPointerException e)
            {
                System.out.println(grid[i].id + (grid[i].rotation + rot) % 4);
            }
        }
    }

    //transpose -> reverse row
    // i + (w * (j + k * h)) = (l-k-1) + (l * (j + i * h))
    public CityGrid3D rotate90()
    {
        CityTile[] rotated = new CityTile[grid.length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < length; k++) {
                    rotated[(length-k-1) + (length * (j + i * height))] = grid[i + (width * (j + k * height))];
                }
            }
        }

        rotatePieces(rotated, 1);

        return new CityGrid3D(handler, rotated, length, height, width);
    }

    //reverse column -> reverse row (order irrelevant)
    //i + (w * (j + k * h)) = (w-i-1) + (w * (j + (l-k-1) * h)))
    public CityGrid3D rotate180()
    {
        CityTile[] rotated = new CityTile[grid.length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < length; k++) {
                    rotated[(width-i-1) + (width * (j + (length-k-1) * height))] = grid[i + (width * (j + k * height))];
                }
            }
        }

        rotatePieces(rotated, 2);

        return new CityGrid3D(handler, rotated, length, height, width);
    }

    //transpose -> reverse column
    //i + (w * (j + k * h)) = k + (l * (j + (w-i-1) * h))
    public CityGrid3D rotate270()
    {
        CityTile[] rotated = new CityTile[grid.length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < length; k++) {
                    rotated[ k + (length * (j + (width-i-1) * height))] = grid[i + (width * (j + k * height))];
                }
            }
        }

        rotatePieces(rotated, 3);

        return new CityGrid3D(handler, rotated, length, height, width);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getLength() {
        return length;
    }
}
