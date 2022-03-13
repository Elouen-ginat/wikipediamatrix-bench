package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertorPlus;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;
import fr.univrennes1.istic.wikipediamatrix.Serializer.Serializer;
import fr.univrennes1.istic.wikipediamatrix.Serializer.HTML.WikipediaHTMLSerializer;


public class App 
{

    // Get Logger
    public static final Logger LOGGER = LogManager.getLogger(App.class);
    public static void main( String[] args ) throws IOException
    {

        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		
		String url = "Help:Table";
        // String url = "List_of_Nvidia_graphics_processing_units";
        
        String wurl = BASE_WIKIPEDIA_URL + url; 
        App.LOGGER.info("Wikipedia url: " + wurl);
        
        Extractor extractor = new WikipediaHTMLExtractor();
        Document doc = null;
        try {
            doc = extractor.getDocument(wurl);
        } catch (Exception e) {
            String message = "Unable to connect to the URL: " + wurl;
            App.LOGGER.error(message, e);
            return;
        }

        Elements tables = null;
        try {
            tables = extractor.getAllFirstWikiTable(doc);
        } catch (NoTableException e) {
            App.LOGGER.info(e.getMessage());
            return;
        }

        Convertor convertor = new WikipediaHTMLConvertorPlus();
        ArrayList<String[][]> string_tables = convertor.toStringTables(tables);
        //String[][] string_table = convertor.toStringTable(tables.get(56));

        Serializer serializer = new WikipediaHTMLSerializer();
        for (int i = 0; i < string_tables.size(); i++) {
            String csvFileName = Serializer.mkCSVFileName(url, i);
            App.LOGGER.info("CSV file name: " + csvFileName);

            String[][] string_table = string_tables.get(i);

            try {
                serializer.saveToCSV(string_table, csvFileName);
            } catch (IOException e) {
                App.LOGGER.error(e.getMessage());
            }
        }
	    
    }
}
