package fr.univrennes1.istic.wikipediamatrix.Convertor.HTML;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;

public class WikipediaHTMLConvertorPlus implements Convertor {

    @Override
    public String[][] toStringTable(Element table) {
        
        Table first_table = new Table(table);

        return null;

    }

    @Override
    public ArrayList<String[][]> toStringTables(Elements tables) {

        ArrayList<String[][]> string_tables = new ArrayList<String[][]>();
        for (Element table : tables) {
            String[][] string_table = this.toStringTable(table);
            string_tables.add(string_table);
        }
        
        return string_tables;
    }

    
}
