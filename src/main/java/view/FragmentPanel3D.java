package view;

import model.CityGrid;

import java.awt.*;

public class FragmentPanel3D extends FragmentPanel {

    int selectedHeight = 0, height;

    public FragmentPanel3D(CityGrid g, int w, int l, int h) {
        super(g, w, l);

        height = h;
    }

    public void setSelectedHeight(int h)
    {
        selectedHeight = h;
    }

    public int getSelectedHeight() {
        return selectedHeight;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < grid.getGridLength(); i++)
        {
            int x = i % gridW, z = i / (gridW * height), y = (i / gridW) % height;
            if (y != selectedHeight) continue;

            if (grid.getPosition(i) == null)
            {
                g.setColor(Color.red);
                nullFragment.paint(g, x * cellW, z * cellH, cellW, cellH);

                continue;
            }

            String id = grid.getPosition(i).getId();

            g.setColor(Color.black);
            if (fragments.containsKey(id))
            {
                fragments.get(id).paint(g, x * cellW, z * cellH, cellW, cellH);
            }

            g.setColor(Color.black);
            g.drawRect(x * cellW, z * cellH, cellW, cellH);
        }

        g.setColor(Color.GREEN);
        g.drawRect(selectedX * cellW, selectedY * cellH, cellW, cellH);
    }
}
