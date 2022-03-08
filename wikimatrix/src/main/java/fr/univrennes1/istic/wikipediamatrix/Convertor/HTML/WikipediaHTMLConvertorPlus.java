package fr.univrennes1.istic.wikipediamatrix.Convertor.HTML;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import fr.univrennes1.istic.wikipediamatrix.Convertor.Convertor;

public class WikipediaHTMLConvertorPlus implements Convertor {

    @Override
    public String[][] toStringTable(Element table) {
        List<List<String>> string_list = new ArrayList<List<String>>();
        int max_size = 0;

        Elements rows = table.select("tr");
        for (Element row : rows) {
            Elements cols = row.select("td");
            ArrayList<String> col_list = new ArrayList<String>();
            for (Element col : cols) {
                col_list.add(col.text());
            }
            max_size = Math.max(max_size, col_list.size());
            string_list.add(col_list);
        }
        int x_size = string_list.size();
        int y_size = max_size;
        String[][] string_table = new String[x_size][y_size];

        for (int i = 0; i < x_size; i++) {
            List<String> row = string_list.get(i);
            for (int j = 0; j < row.size(); j++) {
                string_table[i][j] = row.get(j);
            }
        }

        return string_table;
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
