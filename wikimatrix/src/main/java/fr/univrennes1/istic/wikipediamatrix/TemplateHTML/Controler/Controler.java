package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Controler;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Balise;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Table;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tbody;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Td;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tfoot;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Th;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Thead;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Tr;
import fr.univrennes1.istic.wikipediamatrix.App;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.A;
import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise.Img;

public class Controler {
    
    // Array of all balise (all class that inherit from Balise)
    private Balise[] balises = {new Table(), new Thead(), new Tbody(), new Tfoot(), 
                                new Td(), new Th(), new Tr(), new A(), new Img()};

    // Mapping from tag name to the corresponding object
    private Map<String, Balise> tag_map = new HashMap<String, Balise>();

    public Controler() {
        // Filling the Hashmap
        for(Balise balise : balises) {
            tag_map.put(balise.getTag(), balise);
        }
    }

    public Balise getBalise(Element child) {
        String tag = child.tag().getName();

        if (this.tag_map.containsKey(tag)) {
            return this.tag_map.get(tag).newInstance();
        }
        App.LOGGER.debug("Tag name not present: " + tag);
        return null;
    }

}