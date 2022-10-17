package io;

import model.CityTile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import tile.TileHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class TileXmlReader {

    public static TileHandler readFromFile(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList meta = doc.getElementsByTagName("meta");

        TileHandler tileHandler = new TileHandler(Integer.parseInt(meta.item(0).getAttributes().getNamedItem("sides").getNodeValue()));

        Element tiles = doc.getDocumentElement();
        NodeList tileList = tiles.getElementsByTagName("tile");
        for (int i=0;i<tileList.getLength();i++)
        {
            Element tile = (Element) tileList.item(i);
            String tileBaseId = tile.getAttributes().getNamedItem("id").getNodeValue();

            NodeList subTileList = tile.getElementsByTagName("subTile");
            for (int j=0;j<subTileList.getLength();j++)
            {
                Element subTile = (Element) subTileList.item(j);

                String id = subTile.getAttribute("id");
                int r = Integer.parseInt(id.split(tileBaseId)[1]);
                CityTile subTileOb = new CityTile(tileBaseId, r, tileHandler.getSides());

                NodeList sideList = subTile.getElementsByTagName("side");
                for (int k=0;k<sideList.getLength();k++)
                {
                    Element side = (Element) sideList.item(k);
                    int sideIndex = Integer.parseInt(side.getAttribute("index"));

                    NodeList validList = side.getElementsByTagName("valid");
                    for (int l=0;l<validList.getLength();l++)
                    {
                        Element valid = (Element) validList.item(l);
                        String validId = valid.getAttribute("id");

                        subTileOb.addValid(validId, sideIndex);
                    }
                }
                //rotation is same as index in this case
                tileHandler.addBoth(subTileOb, r, r);
            }

            NodeList rotationList = tile.getElementsByTagName("rotation");
            for (int j=0;j<rotationList.getLength();j++)
            {
                Element rotation = (Element) rotationList.item(j);

                String id = rotation.getAttribute("id");
                String rotId = rotation.getTextContent();

                if (tileHandler.lookupIndex(id) == null)
                {
                    int index = Integer.parseInt(id.split(tileBaseId)[1]);
                    int r = Integer.parseInt(rotId.split(tileBaseId)[1]);

                    //rotation is shared
                    CityTile subTileOb = tileHandler.lookupIndex(rotId);
                    tileHandler.addBoth(subTileOb, index, r);
                }
            }
        }

        return tileHandler;
    }
}
