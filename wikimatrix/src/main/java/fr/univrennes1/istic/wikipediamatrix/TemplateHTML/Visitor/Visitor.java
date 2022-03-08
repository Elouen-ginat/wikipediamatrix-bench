package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.App;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;

public class Visitor {
    
    // Array of all balise (all class that inherit from Balise)
    private Balise[] balises = {new Table()};

    // Mapping from tag name to the corresponding object
    Map<String, Balise> tag_map = new HashMap<String, Balise>();

    public Visitor() {
        // Filling the Hashmap
        for(Balise balise : balises) {
            tag_map.put(balise.getTag(), balise);
        }
    }

    public Balise getBalise(Element child) {
        String tag = child.tag().getName();

        if (this.tag_map.containsKey(tag)) {
            return this.tag_map.get(tag);
        }
        App.LOGGER.debug("Tag name not present: " + tag);
        return null;
    }

}
