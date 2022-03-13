package fr.univrennes1.istic.wikipediamatrix.Extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;

public interface Extractor {

    Document getDocument(String url) throws Exception;

    Elements getAllWikiTable(Document doc)  throws NoTableException;

    Element getWikiTable(Document doc, int index) throws NoTableException, IndexOutOfBoundsException;
    
    Elements getAllFirstWikiTable(Document doc) throws NoTableException;
}
