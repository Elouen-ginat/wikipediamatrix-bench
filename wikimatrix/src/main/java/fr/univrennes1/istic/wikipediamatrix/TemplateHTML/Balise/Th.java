package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Th extends Balise {

    public Th() {
        super("th");
    }

    public Th(Element self, Balise parent) {
        super(self, parent);
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.Th(this);
    }

    @Override
    public String getInfo() {
        if (this.isFinal()) {
            return this.getElement().text();
        }
        return null;
    }

    @Override
    public Balise newInstance(Element self, Balise parent) {
        return new Th(self, parent);
    }
    
}
