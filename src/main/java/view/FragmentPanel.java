package view;

import model.CityGrid;
import model.CityGrid2D;
import model.CityTile;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class FragmentPanel extends JPanel{
    HashMap<String, Fragment> fragments = new HashMap<>();
    Fragment nullFragment = new Fragment() {
        @Override
        public void paint(Graphics g, int x, int y, int w, int h) {
            g.fillRect(x + (w / 4), y + (h / 4), w / 2, h / 2);
        }
    };

    CityGrid2D grid;
    int gridW, gridH;

    public FragmentPanel(CityGrid2D g)
    {
        grid = g;

        gridW = g.getWidth();
        gridH = g.getHeight();
    }

    public void addFragment(String id, Fragment f)
    {
        fragments.put(id, f);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        int cellW = getWidth() / gridW;
        int cellH = getHeight() / gridH;

        for (int i=0;i<grid.getLength();i++)
        {
            int x = i % gridW, y = i / gridW;

            if (grid.getPosition(i) == null)
            {
                g.setColor(Color.red);
                nullFragment.paint(g, x * cellW, y * cellH, cellW, cellH);

                continue;
            }

            String id = grid.getPosition(i).getId();

            g.setColor(Color.black);
            if (fragments.containsKey(id))
            {
                fragments.get(id).paint(g, x * cellW, y * cellH, cellW, cellH);
            }

            g.setColor(Color.black);
            g.drawRect(x * cellW, y * cellH, cellW, cellH);
        }
    }
}
