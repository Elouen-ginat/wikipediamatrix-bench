package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;

public class WikipediaHTMLExtractorTest {

    @Test
    public void getDocument() {

        // Arrange
        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";

        Extractor extractor = new WikipediaHTMLExtractor();

        // Act
        Document doc = null;
        try {
            doc = extractor.getDocument(BASE_WIKIPEDIA_URL);
        } catch (Exception e) {
            String message = "Unable to connect to wikipedia";
			App.LOGGER.error(message);
            fail(message);
        }

        // Assert
        assertNotNull(doc);

    }

    @Test
    public void getAllWikiTable() {

        // Arrange
        String html =   "<table></table>"
                    +   "<table class=\"testwikitable test\"></table>"
                    +   "<table class=\"wikitable\"></table>";
        Document doc = Jsoup.parse(html);
        Extractor extractor = new WikipediaHTMLExtractor();

        //Act
        Elements tables = null;
        try {
            tables = extractor.getAllWikiTable(doc);
        } catch (NoTableException e) {
            App.LOGGER.error(e.getMessage());
            fail(e.getMessage());
        }

        //Assert
        assertEquals(2, tables.size());

    }

    @Test
    public void getWikiTable() {

        // Arrange
        String class_name = "wikitable";
        String html =   "<table></table>"
                    +   "<table class=\"testwikitable test\"></table>"
                    +   "<table class=\""+class_name+"\"></table>";
        Document doc = Jsoup.parse(html);
        Extractor extractor = new WikipediaHTMLExtractor();

        //Act
        Element table = null;
        try {
            table = extractor.getWikiTable(doc, 1);
        } catch (NoTableException e_nt) {
            App.LOGGER.error(e_nt.getMessage());
            fail(e_nt.getMessage());
        } catch (IndexOutOfBoundsException e_iob) {
            App.LOGGER.error(e_iob.getMessage());
            fail(e_iob.getMessage());
        }

        //Assert
        assertEquals(class_name, table.attr("class"));

    }

    @Test
    public void getAllFirstTable() {
        
        // Arrange
        String html =   "<table><tr><td>"
                    +       "<table class=\"testwikitable test1\"><tr><td>"
                    +           "<table class=\"wikitable\"></table>"
                    +       "</td></tr></table>"
                    +   "</td></tr></table>"
                    +   "<table class=\"testwikitable test2\"><tr><td>"
                    +       "<table class=\"wikitable\"></table>"
                    +   "</td></tr></table>"
                    +   "<div>"
                    +       "<table class=\"wikitable2\"></table>"
                    +   "</div>";
        Document doc = Jsoup.parseBodyFragment(html);
        Extractor extractor = new WikipediaHTMLExtractor();

        //Act
        Elements tables = null;
        try {
            tables = extractor.getAllFirstWikiTable(doc);
        } catch (NoTableException e) {
            App.LOGGER.error(e.getMessage());
            fail(e.getMessage());
        }

        //Assert
        assertEquals(3, tables.size());
    }
}
