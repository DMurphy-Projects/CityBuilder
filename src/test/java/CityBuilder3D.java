import IO.SchematicFileHandler;
import IO.SchematicReader;
import Model.SchematicArea;
import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.io.CompressionType;
import dev.dewy.nbt.tags.collection.CompoundTag;
import dev.dewy.nbt.tags.collection.ListTag;
import io.TileXmlReader;
import io.TileXmlWriter;
import model.*;
import org.xml.sax.SAXException;
import tile.TileHandler;
import view.FragmentPanel3D;
import view.examples.CityFragmentPanel;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CityBuilder3D {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        String fileName = "src/main/resources/cityTiles3D.xml";
        TileHandler tileHandler = TileXmlReader.readFromFile(fileName);

        int w = 25, h = 2, l = 25;
        CityGrid3D cityGrid = new CityGrid3D(tileHandler, w, l, h);
        cityGrid.setPosition(0, "BLANK", 0);

        //constraints
        final Constraint<CityTilePosition, CityGrid3D> tileConstraints = new Constraint<CityTilePosition, CityGrid3D>(cityGrid) {
            @Override
            protected void updateProbability(State<CityTilePosition> state) {
                int pos = state.getModelState().getPosition();
                String id = state.getModelState().getTile().getId();

                CityTile[] surrounding = model.getSurrounding(pos);

                for (int i=0;i<6;i++)
                {
                    if (surrounding[i] == null) continue;

                    HashSet valid = tileHandler.lookupTile(id).getValid(i);
                    if (!valid.contains(surrounding[i].getId())) {
                        state.updateWeight(0);
                    }
                }
            }
        };

        ArrayList<SuperPosition<CityTile, CityGrid>> superPositionGrid = new ArrayList<SuperPosition<CityTile, CityGrid>>();

        int middle = (cityGrid.getWidth()/2) + (cityGrid.getHeight()/2) * cityGrid.getWidth(), mX = middle % cityGrid.getWidth(), mY = middle / cityGrid.getWidth();
        double maxDist = Math.pow(cityGrid.getWidth() - mX, 2) + Math.pow(cityGrid.getHeight() - mY, 2);
        maxDist /= 2;

        //setup super positions
        for (int i=0;i<w*h*l;i++)
        {
            SuperPosition<CityTile, CityGrid> sp = new SuperPosition<>();
            sp.addConstraint(tileConstraints);

            CityTile _t = cityGrid.getPosition(i);
            if (_t == null) {
                for (CityTile t : tileHandler.getOrderedUniqueTiles()) {
                    if (t.getId().startsWith("BUILDING")) {
                        int iX = i % cityGrid.getWidth(), iY = i / cityGrid.getWidth();
                        double dist = Math.pow(iX - mX, 2) + Math.pow(iY - mY, 2);
                        double weight = Math.max(t.getWeight() * (1d - (dist / maxDist)), 0);

                        sp.addState(new State<CityTilePosition>(new CityTilePosition(t, i), (int) weight));
                    } else {
                        sp.addState(new State<CityTilePosition>(new CityTilePosition(t, i), t.getWeight()));
                    }
                }
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
        FragmentPanel3D panel = CityFragmentPanel.create(cityGrid);
        frame.add(panel);

        int SCROLL_POSITION = 0;
        final int[] listenerVariables = {0};
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int x = panel.getSelectedX(), z = panel.getSelectedY();
                int y = panel.getSelectedHeight();

                switch(e.getKeyCode())
                {
                    case KeyEvent.VK_LEFT:
                        //cycle tile forward
                        listenerVariables[SCROLL_POSITION] = (listenerVariables[0] + 1) % tileHandler.getUniqueTiles().size();

                        cityGrid.setPosition(x, y, z, tileHandler.lookupOrder(listenerVariables[SCROLL_POSITION]));
                        break;
                    case KeyEvent.VK_RIGHT:
                        //cycle tile back
                        listenerVariables[SCROLL_POSITION] = Math.floorMod(listenerVariables[0] - 1, tileHandler.getUniqueTiles().size());

                        cityGrid.setPosition(x, y, z, tileHandler.lookupOrder(listenerVariables[SCROLL_POSITION]));
                        break;
                    case KeyEvent.VK_UP:
                        panel.setSelectedHeight(Math.min(h-1, panel.getSelectedHeight() + 1));
                        System.out.println("Layer " + panel.getSelectedHeight());
                        break;
                    case KeyEvent.VK_DOWN:
                        panel.setSelectedHeight(Math.max(0, panel.getSelectedHeight() - 1));
                        System.out.println("Layer " + panel.getSelectedHeight());
                        break;
                    case KeyEvent.VK_ENTER:
                        //update validity
                        System.out.println("Updating Validity");
                        tileHandler.addValidFromGrid(cityGrid);
                        tileHandler.addValidFromGrid(cityGrid.rotate90());
                        tileHandler.addValidFromGrid(cityGrid.rotate180());
                        tileHandler.addValidFromGrid(cityGrid.rotate270());
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
                panel.repaint();
            }
        });

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton())
                {
                    case 1:
                        listenerVariables[SCROLL_POSITION] = 0;
                        panel.updateSelected(e);
                        CityTile selectedTile = cityGrid.getPosition(panel.getSelectedX(), panel.getSelectedHeight(), panel.getSelectedY());
                        if (selectedTile != null) {
                            System.out.println(selectedTile.getId());
                            System.out.println(Arrays.toString(selectedTile.getValid()));
                        }
                        break;
                }
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

            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
//
//        String inFolder = "C:\\Users\\Dean\\AppData\\Roaming\\.minecraft\\installations\\1.18.1\\schematics\\City Generator\\City Tiles\\";
//        String outFolder = "C:\\Users\\Dean\\AppData\\Roaming\\.minecraft\\installations\\1.18.1\\schematics\\City Generator\\City Examples\\";
//
//        Collection<CityTile> tiles = tileHandler.getOrderedUniqueTiles();
//        HashMap<CityTile, SchematicArea> tileMap = new HashMap<>();
//
//        for (CityTile t: tiles)
//        {
//            tileMap.put(tileHandler.lookupTile(t.getId()), createFromFile(String.format("%s%s.litematic", inFolder, t.getId())));
//        }
//
//        int sSize = 9, sHeight = 10;
//        SchematicArea schematicArea = new SchematicArea(cityGrid.getWidth() * sSize, sHeight, cityGrid.getHeight() * sSize);
//        schematicArea.addPalette("minecraft:air");
//
//        for (int i=0;i<cityGrid.getWidth();i++)
//        for (int j=0;j<cityGrid.getHeight();j++)
//        {
//            CityTile tile = cityGrid.getPosition(i, j);
//            SchematicArea copyArea = tileMap.get(tile);
//            if (copyArea == null) continue;
//
//            schematicArea.addArea(copyArea,
//                    0, 0, 0,
//                    sSize, copyArea.getHeight(), sSize,
//                    i * sSize, 0, j * sSize);
//        }
//
//        write(String.format("%s%s", outFolder, "generationTest.litematic"), new Nbt(), schematicArea);
    }

    public static SchematicArea createFromFile(String file) throws IOException {
        Nbt nbt = new Nbt();

        CompoundTag root = nbt.fromFile(new File(file));

        SchematicFileHandler fileHelper = new SchematicFileHandler(root);
        SchematicArea area = SchematicReader.read(fileHelper);

        return area;
    }

    public static void write(String name, Nbt nbt, SchematicArea area) throws IOException {
        SchematicFileHandler fileHelper = new SchematicFileHandler();
        fileHelper.createRoot();

        fileHelper.modifyDataVersion(2865);
        fileHelper.modifyVersion(6);
        //metadata
        fileHelper.modifyEnclosingSize(area.getWidth(), area.getHeight(), area.getDepth());
        fileHelper.modifyAuthor("Dean");
        fileHelper.modifyDescription("Test File");
        fileHelper.modifyName("Test Area");
        fileHelper.modifyRegionCount(1);
        fileHelper.modifyTimeCreated(System.currentTimeMillis());
        fileHelper.modifyTimeModified(System.currentTimeMillis());
        fileHelper.modifyTotalBlocks(area.getWidth() * area.getHeight() * area.getDepth());
        fileHelper.modifyTotalVolume(area.getWidth() * area.getHeight() * area.getDepth());

        //region
        fileHelper.modifyPosition(0, 0, 0);
        fileHelper.modifySize(area.getWidth(), area.getHeight(), area.getDepth());
        fileHelper.modifyBlockStatePalette(area.createBlockStatePalette());
        fileHelper.modifyEntities(new ListTag<CompoundTag>());
        fileHelper.modifyPendingBlockTicks(new ListTag<CompoundTag>());
        fileHelper.modifyPendingFluidTickss(new ListTag<CompoundTag>());
        fileHelper.modifyTileEntities(new ListTag<CompoundTag>());
        fileHelper.modifyBlockStates(area.createLongArray());

        nbt.toFile(fileHelper.root, new File(name), CompressionType.GZIP);
    }
}
