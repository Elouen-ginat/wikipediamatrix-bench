package fr.univrennes1.istic.wikipediamatrix.Extractor.HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.App;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;

public class WikipediaHTMLExtractor implements Extractor{

    // Get the tag of the table balise
    String table_tag = new Table().getTag();
    // Wiki table class
    String wikitable_class = "wikitable";

    @Override
    public Document getDocument(String url) throws Exception {
        Document doc = null;
        doc = Jsoup.connect(url).get();
        return doc;
    }

    @Override
    public Elements getAllWikiTable(Document doc) throws NoTableException {
        Elements tables = doc.select(this.table_tag);
        Elements wikitables = new Elements();
        for (Element table : tables) {
            // If the table has a class attribute that contains "wikitable" we keep it
            if (table.attr("class").contains(this.wikitable_class)) {
                wikitables.add(table);
            }
        }
        if (wikitables.isEmpty()) {
            throw new NoTableException(doc.location());
        }
        return wikitables;
    }

    @Override
    public Element getWikiTable(Document doc, int index) throws NoTableException, IndexOutOfBoundsException  {
        // Get all elements wikitable in the dom
        Elements tables = this.getAllWikiTable(doc);
        // Throw execption if no tables in the DOM
        if (tables.isEmpty()) {
            throw new NoTableException(doc.location());
        }
        // Get the table in the list of tables
        Element table = tables.get(index);
        return table;
    }

    @Override
    public Elements getAllFirstWikiTable(Document doc) throws NoTableException {
        Elements tables = this.getAllWikiTable(doc);

        // List of table that are nested in another table (we need to remove them from starting tables)
        Elements tables_to_remove = new Elements();
        for (Element table : tables) {
            // Get all child table
            Elements child_tables = table.select(this.table_tag);
            // Remove itself from the list
            child_tables.remove(table);
            // Add all remaining children to the remove list
            tables_to_remove.addAll(child_tables);
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
