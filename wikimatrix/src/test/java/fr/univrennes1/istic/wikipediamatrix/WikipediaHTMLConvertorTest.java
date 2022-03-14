package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertorPlus;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;

public class WikipediaHTMLConvertorTest {

    private String project_path = Paths.get("").toAbsolutePath().toString();
    private String inputs_rel_path = "src/test/java/fr/univrennes1/istic/wikipediamatrix/inputs";
    private String inputs_path = Paths.get(project_path, inputs_rel_path).toString();

    private int[] getLength(String[][] string_table) {
        int size_row = string_table.length;
        int size_col = 0;
        for (String[] row : string_table) {
            size_col = Math.max(size_col, row.length);
        }
        return new int[]{size_row, size_col};
    }

    @Test
    public void toStringTableTestNormal() {

        // Arrange
        File html = Paths.get(inputs_path, "toStringTableTestNormal.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int[] expected_size = new int[]{2,3};
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        //Act
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
        String[][] string_table = convertor.toStringTable(table);

        //Assert
        int[] actual_size = getLength(string_table);
        assertArrayEquals(expected_size, actual_size);
    }

    @Test
    public void toStringTableTestSpanRow() {
        // Arrange
        File html = Paths.get(inputs_path, "toStringTableTestSpanRow.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }   
        int[] expected_size = new int[]{2,3};
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        //Act
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
        String[][] string_table = convertor.toStringTable(table);

        //Assert
        // Test size
        int[] actual_size = getLength(string_table);
        assertArrayEquals(expected_size, actual_size);
        // Test Value
        for (String[] row : string_table) {
            for (String value : row) {
                if (value.equals("")) fail("Wrong value in table");
            }
        }
    }

    @Test
    public void toStringTableTestSpanCol() {
        // Arrange
        File html = Paths.get(inputs_path, "toStringTableTestSpanCol.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int[] expected_size = new int[]{2,3};
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        // Act
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
        String[][] string_table = convertor.toStringTable(table);

        // Assert
        // Test size
        int[] actual_size = getLength(string_table);
        assertArrayEquals(expected_size, actual_size);
        // Test Value
        for (String[] row : string_table) {
            for (String st : row) {
                if (st == "") fail("Wrong value in table");
            }
        }

    }

    @Test
    public void toStringTableTestSpanRowCol() {
        // Arrange
        File html = Paths.get(inputs_path, "toStringTableTestSpanRowCol.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int[] expected_size = new int[]{6,3};
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        //Act
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
        String[][] string_table = convertor.toStringTable(table);

        //Assert
        // Test size
        int[] actual_size = getLength(string_table);
        assertArrayEquals(expected_size, actual_size);
        // Test Value
        for (String[] row : string_table) {
            for (String value : row) {
                if (value.equals("")) fail("Wrong value in table");
            }
        }
    }

    @Test
    public void toStringTableTestNested() {
        // Arrange
        File html = Paths.get(inputs_path, "toStringTableTestNested.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int[] expected_size = new int[]{4,5};
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        //Act
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
        String[][] string_table = convertor.toStringTable(table);

        //Assert
        // Test size
        int[] actual_size = getLength(string_table);
        assertArrayEquals(expected_size, actual_size);
        // Test Value
        for (String[] row : string_table) {
            for (String value : row) {
                if (value.equals("")) fail("Wrong value in table");
            }
        }
    }

    @Test
    public void toStringTableTestNestedNested() {
        // Arrange
        File html = Paths.get(inputs_path, "toStringTableTestNestedNested.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int[] expected_size = new int[]{4,7};
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        //Act
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
        String[][] string_table = convertor.toStringTable(table);

        //Assert
        // Test size
        int[] actual_size = getLength(string_table);
        assertArrayEquals(expected_size, actual_size);
        // Test Value
        for (String[] row : string_table) {
            for (String value : row) {
                if (value.equals("")) fail("Wrong value in table");
            }
        }
    }

    @Test
    public void toStringTables() {
        // Arrange
        File html = Paths.get(inputs_path, "toStringTables.html").toFile();
        Document doc = null;
        try {
            doc = Jsoup.parse(html, "UTF-8");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        Extractor extractor = new WikipediaHTMLExtractor();
        Convertor convertor = new WikipediaHTMLConvertorPlus();

        //Act
        Elements tables = null;
        try {
            tables = extractor.getAllFirstWikiTable(doc);
        } catch (NoTableException e_nt) {
            App.LOGGER.error(e_nt.getMessage());
            fail(e_nt.getMessage());
        }
        List<String[][]> string_tables = convertor.toStringTables(tables);

        //Assert
        assertEquals(2, string_tables.size());
    }
    
}
