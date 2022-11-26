import cityExamples.*;
import io.TileXmlReader;
import io.TileXmlWriter;
import model.*;
import org.xml.sax.SAXException;
import tile.TileHandler;
import view.Fragment;
import view.FragmentPanel;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.*;

public class CityBuilder {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        String fileName = "src/main/resources/cityTiles2.xml";
        TileHandler tileHandler = TileXmlReader.readFromFile(fileName);

        int w = 20, h = 20;
        CityGrid2D cityGrid = new CityGrid2D(tileHandler, w, h);

        //constraints
        final Constraint<CityTilePosition, CityGrid2D> tileConstraints = new Constraint<CityTilePosition, CityGrid2D>(cityGrid) {
            @Override
            protected boolean allowed(State<CityTilePosition> state) {
                int pos = state.getModelState().getPosition();
                String id = state.getModelState().getTile().getId();

                CityTile[] surrounding = model.getSurrounding(pos);

                for (int i=0;i<4;i++)
                {
                    if (surrounding[i] == null) continue;

                    HashSet valid = tileHandler.lookupIndex(id).getValid(i);
                    if (!valid.contains(surrounding[i].getId()))
                    {
                        return false;
                    }
                }
                return true;
            }
        };

        ArrayList<SuperPosition<CityTile, CityGrid>> superPositionGrid = new ArrayList<SuperPosition<CityTile, CityGrid>>();

        //setup super positions
        for (int i=0;i<w*h;i++)
        {
            SuperPosition<CityTile, CityGrid> sp = new SuperPosition<>();
            sp.addConstraint(tileConstraints);

            for (CityTile t: tileHandler.lookupIndexAll())
            {
                sp.addState(new State<CityTilePosition>(new CityTilePosition(t, i)));
            }

            superPositionGrid.add(sp);
        }

        Comparator<SuperPosition> comparator = new Comparator<SuperPosition>() {
            public int compare(SuperPosition o1, SuperPosition o2) {
                return o1.getEntropy() - o2.getEntropy();
            }
        };

        //collapse into concrete positions
        for (int i=0;i<superPositionGrid.size();i++)
        {
            for (SuperPosition s: superPositionGrid)
            {
                s.update();
            }
            Collections.sort(superPositionGrid, comparator);

            for (SuperPosition s: superPositionGrid)
            {
                if (!s.hasCollapsed())
                {
                    State<CityTilePosition> state = s.collapse();
                    cityGrid.setPosition(state.getModelState().getPosition(), state.getModelState().getTile());
                    break;
                }
            }
        }

        JFrame frame = new JFrame("");
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

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton())
                {
                    case 1:
                        panel.updateSelected(e);
                        break;
                    case 3:
                        tileHandler.addValidFromGrid(cityGrid);
                        try {
                            TileXmlWriter.writeToFile(fileName, tileHandler);
                            System.out.println("File Saved");
                        } catch (ParserConfigurationException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (TransformerException e1) {
                            e1.printStackTrace();
                        }
                        break;
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
                    cityGrid.setPosition(x, y, tileHandler.getNext(cityGrid.getPosition(x, y)));
                }
                else
                {
                    cityGrid.setPosition(x, y, tileHandler.getPrevious(cityGrid.getPosition(x, y)));
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
