package fr.univrennes1.istic.wikipediamatrix.Extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;

public interface Extractor {

    Document getDocument(String url) throws Exception;

    Element getTable(Document doc) throws NoTableException;
    
}
