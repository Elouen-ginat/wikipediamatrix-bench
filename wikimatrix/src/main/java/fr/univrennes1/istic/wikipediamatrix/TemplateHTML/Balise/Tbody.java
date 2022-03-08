package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Tbody extends Balise {

    public Tbody() {
        super("tbody");
    }

    public Tbody(Element self, Balise parent) {
        super(self, parent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Tbody(this);
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Balise newInstance(Element self, Balise parent) {
        return new Tbody(self, parent);
    }
    
}
