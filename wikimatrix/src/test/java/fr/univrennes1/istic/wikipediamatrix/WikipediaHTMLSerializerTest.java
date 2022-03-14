package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertorPlus;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;
import fr.univrennes1.istic.wikipediamatrix.Serializer.Serializer;
import fr.univrennes1.istic.wikipediamatrix.Serializer.HTML.WikipediaHTMLSerializer;

public class WikipediaHTMLSerializerTest {

    private String project_path = Paths.get("").toAbsolutePath().toString();
    private String inputs_rel_path = "src/test/java/fr/univrennes1/istic/wikipediamatrix/inputs";
    private String inputs_path = Paths.get(project_path, inputs_rel_path).toString();
    
    private int[] getLength(List<CSVRecord> csv_rows) {
        int size_row = 0;
        int size_col = csv_rows.get(0).size();
        for (CSVRecord row : csv_rows) {
            size_col = Math.min(size_col, row.size());
            size_row += 1;
        }
        return new int[]{size_row, size_col};
    }

    @Test public void saveToCSV() {
        
        // Arrange
        File html = Paths.get(inputs_path, "saveToCSV.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int[] expected_size = new int[]{2,3};
        String file_name = "test1";
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        // Act
            // Extract table
        Element table = null;
        try {
            table = extractor.getWikiTable(doc, 0);
        } catch (NoTableException e_nt) {
            App.LOGGER.error(e_nt.getMessage());
            fail(e_nt.getMessage());
        } catch (IndexOutOfBoundsException e_iob) {
            App.LOGGER.error(e_iob.getMessage());
            fail(e_iob.getMessage());
        }
            // Convert table
        String[][] string_table = convertor.toStringTable(table);

            // Serialize object table
        Serializer serializer = new WikipediaHTMLSerializer();
        String csvFileName = Serializer.mkCSVFileName(file_name, 0);
        App.LOGGER.info("CSV file name: " + csvFileName);

        try {
            serializer.saveToCSV(string_table, csvFileName);
        } catch (IOException e) {
            App.LOGGER.error(e.getMessage());
            fail(e.getMessage());
        }

            // Open CSV
        Reader csvFile = null;
        try {
            csvFile = new FileReader(serializer.getPath(csvFileName).toString());
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.parse(csvFile);
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
            // Convert iterable to list
        List<CSVRecord> csv_rows = new ArrayList<CSVRecord>();
        for (CSVRecord record : records) {
            csv_rows.add(record);
        }
        // Assert
            // Test size
        int[] actual_size = getLength(csv_rows);
        assertArrayEquals(expected_size, actual_size);
            // Test values
        for (CSVRecord row : csv_rows) {
            for (String value : row) {
                if (value.equals("")) fail("Wrong value in CSV");
            }
        }
    }
    
    
}
