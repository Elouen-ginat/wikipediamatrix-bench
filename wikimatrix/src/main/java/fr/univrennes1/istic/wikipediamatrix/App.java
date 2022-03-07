package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertor;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;
import fr.univrennes1.istic.wikipediamatrix.Serializer.Serializer;
import fr.univrennes1.istic.wikipediamatrix.Serializer.HTML.WikipediaHTMLSerializer;

/**
 * Hello world!
 *
 */
public class App 
{

    // Get Logger
    public static final Logger LOGGER =  LogManager.getLogger(App.class);
    public static void main( String[] args ) throws IOException
    {
        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		
		String url = "Comparison_of_Afrikaans_and_Dutch";
        
        String wurl = BASE_WIKIPEDIA_URL + url; 
        App.LOGGER.info("Wikipedia url: " + wurl);
        
        Extractor extractor = new WikipediaHTMLExtractor();
        Document doc = null;
        try {
            doc = extractor.getDocument(wurl);
        } catch (Exception e) {
            String message = "Unable to connect to the URL: " + wurl;
            App.LOGGER.error(message, e);
        }

        Element table = null;
        try {
            table = extractor.getTable(doc);
        } catch (NoTableException e) {
            App.LOGGER.info(e.getMessage());
            return;
        }

        Convertor convertor = new WikipediaHTMLConvertor();
        String[][] string_table = convertor.toStringTable(table);

        String csvFileName = App.mkCSVFileName(url, 1);
        App.LOGGER.log(Level.INFO, "CSV file name: " + csvFileName);

        Serializer serializer = new WikipediaHTMLSerializer();
        serializer.saveToCSV(string_table, csvFileName);
	    
    }
    
    private static String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}
}
