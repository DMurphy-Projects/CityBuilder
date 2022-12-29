package view.examples;

import cityExamples.CityPieces;
import io.TileXmlWriter;
import model.CityGrid;
import model.CityGrid2D;
import view.Fragment;
import view.FragmentPanel;
import java.awt.*;

public class CityFragmentPanel {

    public static FragmentPanel create(CityGrid2D cityGrid)
    {
        FragmentPanel panel = new FragmentPanel(cityGrid);
        panel.setPreferredSize(new Dimension(500, 500));

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

            }
        });

        panel.addFragment(CityPieces.BUILDING + CityPieces.BUILDING_ROT[0], new Fragment() {
            @Override
            public void paint(Graphics g, int x, int y, int w, int h) {
                g.fillRect(x + (w / 4), y + (h / 4), w / 2, h / 2);
            }
        });

        return panel;
    }
}
