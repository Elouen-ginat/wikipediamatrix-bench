package fr.univrennes1.istic.wikipediamatrix.Extractor.HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.App;
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
    public Element getTable(Document doc, int index) throws NoTableException, IndexOutOfBoundsException  {
        Elements tables = doc.select("table");
        if (tables.isEmpty()) {
            throw new NoTableException(doc.location());
        }
        // Get the table in the list of tables
        Element table = tables.get(index);
        return table;
    }

    @Override
    public Elements getAllTable(Document doc) throws NoTableException {
        Elements tables = doc.select("table");
        if (tables.isEmpty()) {
            throw new NoTableException(doc.location());
        }
        return tables;
    }

    @Override
    public Elements getAllFirstTable(Document doc) throws NoTableException {
        Elements tables = this.getAllTable(doc);

        // List of table that a nested in another table (we need to remove them from starting tables)
        Elements tables_to_remove = new Elements();
        for (Element table : tables) {
            // Get all child table
            Elements child_tables = table.select("table");
            // Remove itself from the list
            child_tables.remove(table);
            if (!child_tables.isEmpty()) {
                tables_to_remove.addAll(child_tables);
            }
        }
        if (!tables_to_remove.isEmpty()) {
            // Remove tables that are nested in another table
            String message = "Removed " + tables_to_remove.size() + " nested tables amoung " + tables.size();
            tables.removeAll(tables_to_remove);
            App.LOGGER.info(message);
        }

        return tables;
    }
    
}
