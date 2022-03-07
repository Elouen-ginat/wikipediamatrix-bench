package fr.univrennes1.istic.wikipediamatrix.Convertor;

import org.jsoup.nodes.Element;

public interface Convertor {

    String[][] toStringTable(Element table);
}
