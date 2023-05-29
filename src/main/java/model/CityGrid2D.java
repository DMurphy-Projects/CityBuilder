package model;

import tile.TileHandler;

public class CityGrid2D extends CityGrid {

    int width, height;

    public CityGrid2D(TileHandler handler, int w, int h) {
        super(handler, w * h);

        width = w;
        height = h;
    }

    public CityGrid2D(TileHandler handler, CityTile[] grid, int w, int h)
    {
        super(handler, grid);

        width = w;
        height = h;
    }

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


    public CityGrid2D rotate90()
    {
        CityTile[] rotated = new CityTile[grid.length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rotated[(height - j - 1) + i * height] = grid[i + j * width];
            }
        }

        rotatePieces(rotated, 1);

        return new CityGrid2D(handler, rotated, height, width);
    }

    public CityGrid2D rotate180()
    {
        CityTile[] rotated = new CityTile[grid.length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rotated[(width - i - 1) + (height - j - 1) * width] = grid[i + j * width];
            }
        }

        rotatePieces(rotated, 2);

        return new CityGrid2D(handler, rotated, width, height);
    }

    public CityGrid2D rotate270()
    {
        CityTile[] rotated = new CityTile[grid.length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rotated[j + (width - i - 1) * height] = grid[i + j * width];
            }
        }

        rotatePieces(rotated, 3);

        return new CityGrid2D(handler, rotated, height, width);
    }

    public void setPosition(int x, int y, String id, int rot)
    {
        setPosition(x + y * width, id, rot);
    }
    public void setPosition(int x, int y, CityTile tile)
    {
        setPosition(x + y * width, tile);
    }

    public CityTile getPosition(int x, int y)
    {
        return getPosition(x + y * width);
    }

    private CityTile getPositionChecked(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height) return null;

        return getPosition(x, y);
    }

    public CityTile[] getSurrounding(int pos)
    {
        return getSurrounding(pos % width, pos / width);
    }

    public CityTile[] getSurrounding(int x, int y)
    {
        return new CityTile[]{
                getPositionChecked(x, y-1),
                getPositionChecked(x+1, y),
                getPositionChecked(x, y+1),
                getPositionChecked(x-1, y),
        };
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
