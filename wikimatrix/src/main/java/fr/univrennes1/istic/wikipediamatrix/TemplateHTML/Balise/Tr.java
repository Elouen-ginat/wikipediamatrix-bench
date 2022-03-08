package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class Tr extends Balise {

    public Tr() {
        super("tr");
    }

    public Tr(Element self, Balise parent) {
        super(self, parent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.Tr(this);
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
        return new Tr(self, parent);
    }
    
}
