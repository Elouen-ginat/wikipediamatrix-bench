package fr.univrennes1.istic.wikipediamatrix.Extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;

public interface Extractor {

    Document getDocument(String url) throws Exception;

    Element getTable(Document doc, int index) throws NoTableException, IndexOutOfBoundsException;

    Elements getAllTable(Document doc)  throws NoTableException;
    
    Elements getAllFirstTable(Document doc) throws NoTableException;
}
