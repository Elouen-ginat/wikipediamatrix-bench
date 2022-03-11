package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertorPlus;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;
import fr.univrennes1.istic.wikipediamatrix.Serializer.Serializer;
import fr.univrennes1.istic.wikipediamatrix.Serializer.HTML.WikipediaHTMLSerializer;

public class BenchTest {
	
	/*
	*  the challenge is to extract as many relevant tables as possible
	*  and save them into CSV files  
	*  from the 300+ Wikipedia URLs given
	*  see below for more details
	**/
	@Test
	public void testBenchExtractors() throws Exception {
		
		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		// directory where CSV files are exported (HTML extractor) 
		String outputDirHtml = "output" + File.separator + "html" + File.separator;
		assertTrue(new File(outputDirHtml).isDirectory());
		// directory where CSV files are exported (Wikitext extractor) 
		String outputDirWikitext = "output" + File.separator + "wikitext" + File.separator;
		assertTrue(new File(outputDirWikitext).isDirectory());
		
		File file = new File("inputdata" + File.separator + "wikiurls_new.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String url;
	    int nurl = 0;
	    while ((url = br.readLine()) != null) {
	       String wurl = BASE_WIKIPEDIA_URL + url; 
	       System.out.println("Wikipedia url: " + wurl);
	       // (ie extract relevant tables for correct URL, with the two extractors)
			Extractor extractor = new WikipediaHTMLExtractor();
			Document doc = null;
			try {
				doc = extractor.getDocument(wurl);
			} catch (IOException e) {
				e.printStackTrace();
				String message = "Unable to connect to the URL: " + wurl;
				App.LOGGER.error(message);
				//fail("Verify that you have internet access or try an other URL");
				continue;
			}

			Elements tables = null;
			try {
				tables = extractor.getAllFirstTable(doc);
			}catch (NoTableException e) {
				App.LOGGER.info(e.getMessage());
				continue;
			}
			Convertor convertor = new WikipediaHTMLConvertorPlus();
			List<String[][]> string_tables = convertor.toStringTables(tables);
	       
			// for exporting to CSV files, we will use mkCSVFileName 
			// example: for https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels
			// the *first* extracted table will be exported to a CSV file called 
			// "Comparison_of_operating_system_kernels-1.csv"
	    	Serializer serializer = new WikipediaHTMLSerializer();
			for (int i = 0; i < string_tables.size(); i++) {
				String csvFileName = mkCSVFileName(url, i);
				App.LOGGER.info("CSV file name: " + csvFileName);

				String[][] string_table = string_tables.get(i);

				serializer.saveToCSV(string_table, csvFileName);
			}
	       
	       // the Wikitext extractor should save CSV files into output/wikitext
	       // see outputDirWikitext      
	       
	       nurl++;	       
	    }
	    
	    br.close();	    
	    assertEquals(300, nurl);
	}

	private String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

}
