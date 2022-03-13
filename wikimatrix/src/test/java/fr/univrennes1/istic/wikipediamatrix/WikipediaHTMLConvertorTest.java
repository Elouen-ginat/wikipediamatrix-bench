package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        String html =   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>a</td>"
                    +           "<td>b</td>"
                    +           "<td>c</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>d</td>"
                    +           "<td>e</td>"
                    +       "</tr>"
                    +   "</table>";
        int[] expected_size = new int[]{2,3};
        Document doc = Jsoup.parseBodyFragment(html);
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
        String html =   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>a</td>"
                    +           "<td rowspan=2>b</td>"
                    +           "<td>c</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>d</td>"
                    +           "<td>e</td>"
                    +       "</tr>"
                    +   "</table>";
        int[] expected_size = new int[]{2,3};
        Document doc = Jsoup.parseBodyFragment(html);
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
        String html =   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>a</td>"
                    +           "<td>b</td>"
                    +           "<td>c</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>d</td>"
                    +           "<td colspan=2>e</td>"
                    +       "</tr>"
                    +   "</table>";
        int[] expected_size = new int[]{2,3};
        Document doc = Jsoup.parseBodyFragment(html);
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
        String html =   "<table class=\"wikitable\">"
                    +       "<tbody><tr>"
                    +           "<th>Column 1</th>"
                    +           "<th>Column 2</th>"
                    +           "<th>Column 3"
                    +       "</th></tr>"
                    +       "<tr>"
                    +           "<td rowspan=2>A"
                    +           "</td>"
                    +           "<td colspan=2>B"
                    +       "</td></tr>"
                    +       "<tr>"
                    +           "<td>C"
                    +           "</td>"
                    +           "<td>D"
                    +       "</td></tr>"
                    +       "<tr>"
                    +           "<td>E"
                    +           "</td>"
                    +           "<td rowspan=2 colspan=2>F"
                    +       "</td></tr>"
                    +       "<tr>"
                    +           "<td>G"
                    +       "</td></tr>"
                    +       "<tr>"
                    +           "<td colspan=3>H"
                    +   "</td></tr></tbody></table>";
        int[] expected_size = new int[]{6,3};
        Document doc = Jsoup.parseBodyFragment(html);
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
        String html =   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>a</td>"
                    +           "<td>b</td>"
                    +           "<td>c</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>d</td>"
                    +           "<td colspan=2>e"
                    +              "<table class=\"wikitable\">"
                    +                   "<tr>"
                    +                       "<td>a1</td>"
                    +                       "<td>b1</td>"
                    +                   "</tr>"
                    +                   "<tr>"
                    +                       "<td colspan=2>c1</td>"
                    +                   "</tr>"
                    +               "</table>"
                    +           "</td>"
                    +       "</tr>"
                    +   "</table>";
        int[] expected_size = new int[]{3,5};
        Document doc = Jsoup.parseBodyFragment(html);
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
        String html =   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>a</td>"
                    +           "<td>b</td>"
                    +           "<td>c</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>d</td>"
                    +           "<td colspan=2>e"
                    +              "<table>"
                    +                   "<tr>"
                    +                       "<td rowspan=2>a1</td>"
                    +                       "<td>b1</td>"
                    +                   "</tr>"
                    +                   "<tr>"
                    +                       "<td>"
                    +                           "<table class=\"wikitable\">"
                    +                               "<tr>"
                    +                                   "<td>a2</td>"
                    +                                   "<td>b2</td>"
                    +                               "</tr>"
                    +                               "<tr>"
                    +                                   "<td>c2</td>"
                    +                                   "<td>d2</td>"
                    +                               "</tr>"
                    +                           "</table>"
                    +                       "</td>"
                    +                   "</tr>"
                    +               "</table>"
                    +           "</td>"
                    +       "</tr>"
                    +   "</table>";
        int[] expected_size = new int[]{4,7};
        Document doc = Jsoup.parseBodyFragment(html);
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
        String html =   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>a</td>"
                    +           "<td>b</td>"
                    +           "<td>c</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>d</td>"
                    +           "<td>e</td>"
                    +       "</tr>"
                    +   "</table>"
                    +   "<table class=\"wikitable\">"
                    +       "<tr>"
                    +           "<td>f</td>"
                    +       "</tr>"
                    +       "<tr>"
                    +           "<td>g</td>"
                    +           "<td>h</td>"
                    +       "</tr>"
                    +   "</table>";
        Document doc = Jsoup.parseBodyFragment(html);
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
