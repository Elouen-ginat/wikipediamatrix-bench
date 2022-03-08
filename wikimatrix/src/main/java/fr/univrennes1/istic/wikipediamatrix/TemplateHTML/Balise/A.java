package fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Balise;

import org.jsoup.nodes.Element;

import fr.univrennes1.istic.wikipediamatrix.TemplateHTML.Visitor.Visitor;

public class A extends Balise {

    public A() {
        super("a");
    }

    public A(Element self, Balise parent) {
        super(self, parent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.A(this);
    }

    @Override
    public String getInfo() {
        if (this.isFinal()) {
            return this.getElement().text() + " , URL: " + this.getElement().attr("href");
        }
        return null;
    }

    @Override
    public Balise newInstance(Element self, Balise parent) {
        return new A(self, parent);
    }
    
}
