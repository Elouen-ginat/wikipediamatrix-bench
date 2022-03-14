package fr.univrennes1.istic.wikipediamatrix.Web;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.Convertor.HTML.WikipediaHTMLConvertorPlus;
import fr.univrennes1.istic.wikipediamatrix.Exception.NoTableException;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTML.WikipediaHTMLExtractor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.univrennes1.istic.wikipediamatrix.App;

@RestController
public class FromHTML {

    String table_style = "<style>"
                +       "table,th,td {"
                +           "padding: 10px;"
                +           "border: 1px solid black;"
                +           "border-collapse: collapse;"
                +       "}"
                +   "</style>";

    @RequestMapping(value="/table_index", method = RequestMethod.GET)
    @ResponseBody
    public List<String[]> getTable(@RequestParam("name") String url, @RequestParam("index") int index){

        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
        
        String wurl = BASE_WIKIPEDIA_URL + url; 
        App.LOGGER.info("Wikipedia url: " + wurl);
        
        Extractor extractor = new WikipediaHTMLExtractor();
        Document doc = null;
        try {
            doc = extractor.getDocument(wurl);
        } catch (Exception e) {
            String message = "Unable to connect to the URL: " + wurl;
            App.LOGGER.error(message, e);
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, message, e);
        }

        Element table = null;
        try {
            table = extractor.getWikiTable(doc, index);
        } catch (NoTableException e) {
            App.LOGGER.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }

        Convertor convertor = new WikipediaHTMLConvertorPlus();
        String[][] string_table = convertor.toStringTable(table);

        return Arrays.asList(string_table);
    }

    @RequestMapping(value="/table_index/reformat", method = RequestMethod.GET)
    @ResponseBody
    public String getTableHTML(@RequestParam("name") String url, @RequestParam("index") int index){

        List<String[]> string_array = this.getTable(url, index);

        StringBuilder html = new StringBuilder();
        html.append(table_style);
        html.append("<table>");

        for (String[] row : string_array) {
            html.append("<tr>");
            for (String value : row) {
                String escaped_value = StringEscapeUtils.escapeHtml4(value);
                html.append("<td>"+escaped_value+"</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");

        return html.toString();
    }

    @RequestMapping(value="/table_all", method = RequestMethod.GET)
    @ResponseBody
    public List<String[][]> getTable(@RequestParam("name") String url){

        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
        
        String wurl = BASE_WIKIPEDIA_URL + url; 
        App.LOGGER.info("Wikipedia url: " + wurl);
        
        Extractor extractor = new WikipediaHTMLExtractor();
        Document doc = null;
        try {
            doc = extractor.getDocument(wurl);
        } catch (Exception e) {
            String message = "Unable to connect to the URL: " + wurl;
            App.LOGGER.error(message, e);
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, message, e);
        }

        Elements tables = null;
        try {
            tables = extractor.getAllFirstWikiTable(doc);
        } catch (NoTableException e) {
            App.LOGGER.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }

        Convertor convertor = new WikipediaHTMLConvertorPlus();
        List<String[][]> string_table = convertor.toStringTables(tables);

        return string_table;
    }

    @RequestMapping(value="/table_all/reformat", method = RequestMethod.GET)
    @ResponseBody
    public String getTableHTML(@RequestParam("name") String url){

        List<String[][]> string_array = this.getTable(url);

        StringBuilder html = new StringBuilder();
        html.append(table_style);

        int count = 0;
        for (String[][] table : string_array) {
            html.append("<p><strong> Table number "+ count + "</strong></p><br>");
            html.append("<table>");
            for (String[] row : table) {
                html.append("<tr>");
                for (String value : row) {
                    String escaped_value = StringEscapeUtils.escapeHtml4(value);
                    html.append("<td>"+escaped_value+"</td>");
                }
                html.append("</tr>");
            }
            html.append("</table>");
            html.append("<hr>");
            count++;
        }
        return html.toString();
    }
    
}
