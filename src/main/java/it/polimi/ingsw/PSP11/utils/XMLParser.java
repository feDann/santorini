package it.polimi.ingsw.PSP11.utils;

import it.polimi.ingsw.PSP11.model.Deck;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;

public class XMLParser {


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