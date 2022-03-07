package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertor;
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
		
		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String url;
	    int nurl = 0;
	    while ((url = br.readLine()) != null) {
	       String wurl = BASE_WIKIPEDIA_URL + url; 
	       System.out.println("Wikipedia url: " + wurl);
	       // TODO: do something with the Wikipedia URL 
	       // (ie extract relevant tables for correct URL, with the two extractors)
			Extractor extractor = new WikipediaHTMLExtractor();
			Document doc = null;
			try {
				doc = extractor.getDocument(wurl);
			} catch (IOException e) {
				e.printStackTrace();
				String message = "Unable to connect to the URL: " + wurl;
				App.LOGGER.log(Level.ERROR, message);
				fail("Verify that you have internet access or try an other URL");
			}

			Element table = extractor.getTable(doc);

			Convertor convertor = new WikipediaHTMLConvertor();
			String[][] string_table = convertor.toStringTable(table);
	       
	       // for exporting to CSV files, we will use mkCSVFileName 
	       // example: for https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels
	       // the *first* extracted table will be exported to a CSV file called 
	       // "Comparison_of_operating_system_kernels-1.csv"
	       String csvFileName = mkCSVFileName(url, 1);
	       System.out.println("CSV file name: " + csvFileName);
	       // the *second* (if any) will be exported to a CSV file called
	       // "Comparison_of_operating_system_kernels-2.csv"

	       
	       // TODO: the HTML extractor should save CSV files into output/HTML
	       // see outputDirHtml 
		   Serializer serializer = new WikipediaHTMLSerializer();
		   serializer.saveToCSV(string_table, csvFileName);
	       
	       // TODO: the Wikitext extractor should save CSV files into output/wikitext
	       // see outputDirWikitext      
	       
	       nurl++;	       
	    }
	    
	    br.close();	    
	    assertEquals(nurl, 336);
	    



	}

	private String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

}
