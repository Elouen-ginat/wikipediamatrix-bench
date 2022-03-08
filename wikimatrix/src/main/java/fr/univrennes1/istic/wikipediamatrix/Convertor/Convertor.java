package fr.univrennes1.istic.wikipediamatrix.Convertor;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface Convertor {

    String[][] toStringTable(Element table);

    ArrayList<String[][]> toStringTables(Elements tables);
}
