package it.polimi.ingsw.PSP11.utils;

import it.polimi.ingsw.PSP11.model.Deck;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.IOException;

public class XMLParser {


    public static Deck deserializeDeckFromXML(String xmlFileName) throws IOException {
        FileInputStream fis = new FileInputStream(xmlFileName);
        XMLDecoder decoder = new XMLDecoder(fis);
        Deck decodedSettings = (Deck) decoder.readObject();
        decoder.close();
        fis.close();
        return decodedSettings;
    }

}