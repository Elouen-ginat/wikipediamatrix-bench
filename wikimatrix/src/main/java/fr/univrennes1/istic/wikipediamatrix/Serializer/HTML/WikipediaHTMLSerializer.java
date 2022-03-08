package fr.univrennes1.istic.wikipediamatrix.Serializer.HTML;

import fr.univrennes1.istic.wikipediamatrix.Serializer.Serializer;

public class WikipediaHTMLSerializer extends Serializer{

    public WikipediaHTMLSerializer() {
        // Relative directory path to save CSV in
        super("wikimatrix/output/html");
    }

    public WikipediaHTMLSerializer(String save_path) {
        // Relative directory path to save CSV in
        super(save_path);
    }
    
}
