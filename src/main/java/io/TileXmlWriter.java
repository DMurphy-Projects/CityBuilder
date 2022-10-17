package io;

import model.CityTile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tile.TileHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TileXmlWriter {

    public static void writeToFile(String fileName, TileHandler tileHandler) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element tiles = doc.createElement("tiles");
        doc.appendChild(tiles);

        Element meta = doc.createElement("meta");
        meta.setAttribute("sides", tileHandler.getSides()+"");
        tiles.appendChild(meta);

        //easy access of info
        HashSet<String> idSet = new HashSet<>();
        HashMap<String, String> subIdMap = new HashMap<>();
        HashMap<String, String> rotationMap = new HashMap<>();

        for (Map.Entry<String, CityTile> entry: tileHandler.lookupIndexSet())
        {
            idSet.add(entry.getValue().getBaseId());
            rotationMap.put(entry.getKey(), entry.getValue().getId());
        }
        for (CityTile tile: tileHandler.lookupRotationAll())
        {
            subIdMap.put(tile.getId(), tile.getBaseId());
        }

        //retrieval of xml nodes
        HashMap<String, Element> tileElementMap = new HashMap<>();
        HashMap<String, Element> rotationElementMap = new HashMap<>();
        HashMap<String, Element> subTileElementMap = new HashMap<>();

        for (String id: idSet)
        {
            Element tile = doc.createElement("tile");
            tile.setAttribute("id", id);
            tiles.appendChild(tile);
            tileElementMap.put(id, tile);

            Element rotations = doc.createElement("rotations");
            tile.appendChild(rotations);
            rotationElementMap.put(id, rotations);

            Element subtiles = doc.createElement("subTiles");
            tile.appendChild(subtiles);
            subTileElementMap.put(id, subtiles);
        }

        for (Map.Entry<String, String> entry: subIdMap.entrySet())
        {
            String id = entry.getKey();
            String baseId = entry.getValue();
            CityTile tile = tileHandler.lookupIndex(id);

            Element subTiles = subTileElementMap.get(baseId);

            Element subTile = doc.createElement("subTile");
            subTile.setAttribute("id", id);
            subTiles.appendChild(subTile);

            Element sides = doc.createElement("sides");
            subTile.appendChild(sides);

            HashSet<String>[] validSet = tile.getValid();
            for (int i=0;i<validSet.length;i++)
            {
                Element side = doc.createElement("side");
                side.setAttribute("index", i+"");
                sides.appendChild(side);

                for (String s: validSet[i])
                {
                    Element valid = doc.createElement("valid");
                    valid.setAttribute("id", s);
                    side.appendChild(valid);
                }
            }
        }

        for (Map.Entry<String, String> entry: rotationMap.entrySet())
        {
            String r = entry.getKey();
            CityTile tile = tileHandler.lookupIndex(r);

            Element rotation = doc.createElement("rotation");
            rotation.setAttribute("id", r);
            rotation.setTextContent(tile.getId());
            rotationElementMap.get(tile.getBaseId()).appendChild(rotation);
        }

        try (FileOutputStream output = new FileOutputStream(fileName)) {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);
        }
    }
}
