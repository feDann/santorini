package it.polimi.ingsw.PSP11.utils;

import it.polimi.ingsw.PSP11.model.Deck;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class for parsing XML file containing god cards
 */
public class XMLParser {

    /**
     * Method that deserializes XML file
     * @param xmlFileName path to the XML file
     * @return the card deck created from the XML file
     * @throws IOException if can't get the XML file
     */
    public static Deck deserializeDeckFromXML(String xmlFileName) throws IOException {
        ClassLoader cl = XMLParser.class.getClassLoader();
        InputStream fis = cl.getResourceAsStream(xmlFileName);
        if(fis != null) {
            XMLDecoder decoder = new XMLDecoder(fis);
            Deck decodedDeck = (Deck) decoder.readObject();
            decoder.close();
            fis.close();
            return decodedDeck;
        }
        else{
            throw new IOException("Null pointer received");
        }
    }
}