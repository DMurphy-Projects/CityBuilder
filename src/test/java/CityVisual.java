import cityExamples.*;
import io.TileXmlReader;
import model.CityGrid2D;
import org.xml.sax.SAXException;
import tile.TileHandler;
import view.Fragment;
import view.FragmentPanel;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

public class CityVisual {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        TileHandler tileHandler = TileXmlReader.readFromFile("src/main/resources/cityTiles.xml");

        CityGrid2D[] grids = TurnExample.createGrids(tileHandler);

        JFrame frame = new JFrame("");
        CityGrid2D cityGrid = grids[(4 * 1) + 3];
        FragmentPanel panel = new FragmentPanel(cityGrid);
        panel.setPreferredSize(new Dimension(500, 500));
        frame.add(panel);

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

        final int[] scrollPos = {0};
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton())
                {
                    case 1:
                        scrollPos[0] = 0;
                        panel.updateSelected(e);
                        break;
                    case 3: break;
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int x = panel.getSelectedX(), y = panel.getSelectedY();
                if (e.getWheelRotation() > 0)
                {
                    scrollPos[0] = (scrollPos[0] + 1) % tileHandler.getUniqueTiles().size();

                    cityGrid.setPosition(x, y, tileHandler.lookupOrder(scrollPos[0]));
                }
                else
                {
                    scrollPos[0] = Math.floorMod(scrollPos[0] - 1, tileHandler.getUniqueTiles().size());

                    cityGrid.setPosition(x, y, tileHandler.lookupOrder(scrollPos[0]));
                }
                panel.repaint();
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
