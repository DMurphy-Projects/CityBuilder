package view;

import model.CityGrid;
import model.CityGrid2D;
import model.CityGrid3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class FragmentPanel extends JPanel {
    HashMap<String, Fragment> fragments = new HashMap<>();
    Fragment nullFragment = new Fragment() {
        @Override
        public void paint(Graphics g, int x, int y, int w, int h) {
            g.fillRect(x + (w / 4), y + (h / 4), w / 2, h / 2);
        }
    };

    CityGrid grid;
    protected int gridW, gridH, cellW, cellH, selectedX, selectedY;

    public FragmentPanel(CityGrid g, int w, int h)
    {
        grid = g;

        gridW = w;
        gridH = h;

        selectedX = 0;
        selectedY = 0;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);

        cellW = preferredSize.width / gridW;
        cellH = preferredSize.height / gridH;
    }

    public void addFragment(String id, Fragment f)
    {
        fragments.put(id, f);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i<grid.getGridLength(); i++)
        {
            int x = i / gridW, y = i % gridW;

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

        g.setColor(Color.GREEN);
        g.drawRect(selectedX * cellW, selectedY * cellH, cellW, cellH);
    }


    public void updateSelected(MouseEvent e)
    {
        selectedX = e.getX() / cellW;
        selectedY = e.getY() / cellH;
        repaint();
    }

    public int getSelectedX()
    {
        return selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }
}
