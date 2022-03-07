package fr.univrennes1.istic.wikipediamatrix.Extractor.HTML;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;

public class WikipediaHTMLExtractor implements Extractor{

    @Override
    public Document getDocument(String url) throws Exception {
        Document doc = null;
        doc = Jsoup.connect(url).get();
        return doc;
    }

    @Override
    public Element getTable(Document doc) throws NoTableException {
        Elements tables = doc.select("table");
        if (tables.isEmpty()) {
            throw new NoTableException(doc.location());
        }
        Element table = tables.get(0);
        return table;
    }
    
}
