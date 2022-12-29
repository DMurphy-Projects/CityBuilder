import IO.SchematicFileHandler;
import IO.SchematicReader;
import Model.SchematicArea;
import cityExamples.*;
import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.io.CompressionType;
import dev.dewy.nbt.tags.collection.CompoundTag;
import dev.dewy.nbt.tags.collection.ListTag;
import io.TileXmlReader;
import io.TileXmlWriter;
import model.*;
import org.xml.sax.SAXException;
import tile.TileHandler;
import view.Fragment;
import view.FragmentPanel;
import view.examples.CityFragmentPanel;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CityBuilder {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        String fileName = "src/main/resources/cityTiles.xml";
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
        FragmentPanel panel = CityFragmentPanel.create(cityGrid);
        frame.add(panel);

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

        String inFolder = "C:\\Users\\Dean\\AppData\\Roaming\\.minecraft\\installations\\1.18.1\\schematics\\City Generator\\City Tiles\\";
        String outFolder = "C:\\Users\\Dean\\AppData\\Roaming\\.minecraft\\installations\\1.18.1\\schematics\\City Generator\\City Examples\\";

        Collection<CityTile> tiles = tileHandler.lookupRotationAll();
        HashMap<CityTile, SchematicArea> tileMap = new HashMap<>();

        for (CityTile t: tiles)
        {
            tileMap.put(tileHandler.lookupRotation(t.getId()), createFromFile(String.format("%s%s.litematic", inFolder, t.getId())));
        }

        int sSize = 9, sHeight = 10;
        SchematicArea schematicArea = new SchematicArea(cityGrid.getWidth() * sSize, sHeight, cityGrid.getHeight() * sSize);
        schematicArea.addPalette("minecraft:air");

        for (int i=0;i<cityGrid.getWidth();i++)
        for (int j=0;j<cityGrid.getHeight();j++)
        {
            CityTile tile = cityGrid.getPosition(i, j);
            SchematicArea copyArea = tileMap.get(tile);
            schematicArea.addArea(copyArea,
                    0, 0, 0,
                    sSize, copyArea.getHeight(), sSize,
                    i * sSize, 0, j * sSize);
        }

        write(String.format("%s%s", outFolder, "generationTest.litematic"), new Nbt(), schematicArea);
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
