package view;

import model.CityGrid;

import java.awt.*;

public class FragmentPanel3D extends FragmentPanel {

    int selectedHeight = 0;

    public FragmentPanel3D(CityGrid g, int w, int h) {
        super(g, w, h);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < grid.getGridLength(); i++)
        {
            int x = i % gridW, z = i / (gridW * gridH), y = (i / gridW) % gridH;
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
