package view.examples;

import cityExamples.CityPieces;
import model.CityGrid;
import model.CityGrid2D;
import model.CityGrid3D;
import view.Fragment;
import view.FragmentPanel;
import view.FragmentPanel3D;

import java.awt.*;

public class CityFragmentPanel {

    public static FragmentPanel create(CityGrid2D cityGrid)
    {
        FragmentPanel panel = new FragmentPanel(cityGrid, cityGrid.getWidth(), cityGrid.getHeight());
        panel.setPreferredSize(new Dimension(500, 500));

        addFragments(panel);

        return panel;
    }

    public static FragmentPanel3D create(CityGrid3D cityGrid)
    {
        FragmentPanel3D panel = new FragmentPanel3D(cityGrid, cityGrid.getWidth(), cityGrid.getLength(), cityGrid.getHeight());
        panel.setPreferredSize(new Dimension(500, 500));

        addFragments(panel);

        return panel;
    }

    private static void addFragments(FragmentPanel panel)
    {
        panel.addFragment(CityPieces.ROAD + CityPieces.ROAD_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), w, h / 2);
            }
        });

        panel.addFragment(CityPieces.ROAD + CityPieces.ROAD_ROT[1], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x + (w / 4), y, w / 2, h);
            }
        });

        panel.addFragment(CityPieces.INTERSECTION + CityPieces.INTERSECTION_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), w, h / 2);
                g.fillRect(x + (w / 4), y, w / 2, h);
            }
        });

        panel.addFragment(CityPieces.TURN + CityPieces.TURN_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x + (w / 4), y + (h / 4), (3 * w) / 4, h / 2);
                g.fillRect(x + (w / 4), y + (h / 4), w / 2, (3 * h) / 4);
            }
        });

        panel.addFragment(CityPieces.TURN + CityPieces.TURN_ROT[1], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), (3 * w) / 4, h / 2);
                g.fillRect(x + (w / 4), y + (h / 4), w / 2, (3 * h) / 4);
            }
        });

        panel.addFragment(CityPieces.TURN + CityPieces.TURN_ROT[2], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), (3 * w) / 4, h / 2);
                g.fillRect(x + (w / 4), y, w / 2, (3 * h) / 4);
            }
        });

        panel.addFragment(CityPieces.TURN + CityPieces.TURN_ROT[3], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x + (w / 4), y + (h / 4), (3 * w) / 4, h / 2);
                g.fillRect(x + (w / 4), y, w / 2, (3 * h) / 4);
            }
        });

        panel.addFragment(CityPieces.JUNCTION + CityPieces.JUNCTION_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), w, h / 2);
                g.fillRect(x + (w / 4), y + (h / 2), w / 2, h / 2);
            }
        });

        panel.addFragment(CityPieces.JUNCTION + CityPieces.JUNCTION_ROT[1], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), (3 * w) / 4, h / 2);
                g.fillRect(x + (w / 4), y, w / 2, h);
            }
        });

        panel.addFragment(CityPieces.JUNCTION + CityPieces.JUNCTION_ROT[2], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x, y + (h / 4), w, h / 2);
                g.fillRect(x + (w / 4), y, w / 2, (3 * h) / 4);
            }
        });

        panel.addFragment(CityPieces.JUNCTION + CityPieces.JUNCTION_ROT[3], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x + (w / 4), y + (h / 4), (3 * w) / 4, h / 2);
                g.fillRect(x + (w / 4), y, w / 2, h);
            }
        });

        panel.addFragment(CityPieces.BLANK + CityPieces.BLANK_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, w, h);
            }
        });

        panel.addFragment("AIR" + CityPieces.BLANK_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {

            }
        });

        panel.addFragment(CityPieces.BUILDING + CityPieces.BUILDING_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x + (w / 4), y + (h / 4), w / 2, h / 2);
            }
        });
    }
}
